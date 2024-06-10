package com.booksearch.usecase;

import com.booksearch.dto.book.local.BookResponseDto;
import com.booksearch.dto.book.local.BooksInfoResponseDto;
import com.booksearch.dto.common.KeywordSearchRequestDto;
import com.booksearch.exception.AsyncException;
import com.booksearch.exception.KakaoErrorException;
import com.booksearch.exception.NaverErrorException;
import com.booksearch.factory.SearchModuleFactory;
import com.booksearch.internal.service.BookService;
import com.booksearch.internal.service.SearchHistoryService;
import com.booksearch.mapper.BookClientMapper;
import com.booksearch.model.Book;
import com.booksearch.model.BooksInfo;
import com.booksearch.model.SearchHistory;
import com.booksearch.module.search.KakaoSearchModuleImpl;
import com.booksearch.module.search.RepositorySearchModuleImpl;
import com.booksearch.module.search.SearchModule;
import com.booksearch.util.AsyncUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookSearchUseCase {

    private final BookService bookService;

    private final SearchHistoryService searchHistoryService;

    private final SearchModuleFactory searchModuleFactory;

    private final JdbcTemplate jdbcTemplate;

    private static final int MAX_SAVE_VALUE = 1000;

    private static final int KAKAO_PAGE_SIZE = 50;

    private static final int NAVER_PAGE_SIZE = 100;

    private static final String SEARCH_HISTORY_INSERT_SQL = "INSERT INTO search_history (keyword, source, saved_books, register_date_time) values (?,?,?,?)";

    private SearchModule searchModule;

    @Transactional(readOnly = true)
    public BookResponseDto find(String isbn) {
        return BookClientMapper.toResponse(searchModuleFactory.getNormalStatusModule().findBook(isbn));
    }

    @Transactional(readOnly = true)
    public BooksInfoResponseDto findBooks(KeywordSearchRequestDto keywordSearchRequestDto) {

        searchModule = searchModuleFactory.getNormalStatusModule();
        BooksInfo booksInfo = findBooksByRecursive(keywordSearchRequestDto);

        long totalElements = booksInfo.getTotalElements();
        String keyword = keywordSearchRequestDto.getSearchKeyword();
        String source = searchModule instanceof KakaoSearchModuleImpl ? "kakao" : "naver";

        searchHistoryService.findSearchedHistory(new SearchHistory(
                keyword,
                source
        ));


        if (!(searchModule instanceof RepositorySearchModuleImpl)) {

            SearchHistory searchHistory = searchHistoryService.findSearchedHistory(new SearchHistory(keyword, source));

            if (!isSearched(searchHistory, totalElements)) {

                AsyncUtils.runAsync(() -> {
                            if (searchHistory.getSavedBooks() == 0) {
                                try {
                                    jdbcTemplate.update(SEARCH_HISTORY_INSERT_SQL, ps -> {
                                                ps.setString(1, keyword);
                                                ps.setString(2, source);
                                                ps.setLong(3, totalElements);
                                                ps.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
                                            }
                                    );
                                } catch (Exception e) {
                                    throw new AsyncException("다른 사람의 요청이 먼저 잡힘");
                                }
                            } else {
                                searchHistoryService.updateBackupHistory(new SearchHistory(keyword, totalElements, source));
                            }
                        /*
                          키워드 당 최대 호출 갯수 1000

                          저장 중에 error 발생 case -> table에 담아둔 뒤 나중에 batch로 재작업
                         */
                            List<Book> backupBooks = new CopyOnWriteArrayList<>();

                            double maxSave = totalElements > MAX_SAVE_VALUE ? MAX_SAVE_VALUE : totalElements;


                            int totalPage;
                            if (searchModule instanceof KakaoSearchModuleImpl) {
                                try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
                                    totalPage = (int) Math.ceil(maxSave / KAKAO_PAGE_SIZE);

                                    IntStream.range(1, totalPage + 1)
                                            .forEach(i -> executor.execute(() -> backupBooks.addAll(getBooks(keyword, i, KAKAO_PAGE_SIZE))));
                                }

                            } else {
                                totalPage = (int) Math.ceil(maxSave / NAVER_PAGE_SIZE);

                                IntStream.range(1, totalPage + 1)
                                        .forEach(i -> backupBooks.addAll(getBooks(keyword, ((i - 1) * NAVER_PAGE_SIZE) + 1, NAVER_PAGE_SIZE)));
                            }

                            List<String> backupBooksIsbns = new CopyOnWriteArrayList<>();

                            List<Book> filteredBooks = backupBooks.stream()
                                    .filter(book -> {
                                        boolean isUniqueInThisList = true;
                                        Set<String> isbns = new HashSet<>(book.getIsbns());
                                        for (String isbn : isbns) {
                                            if (backupBooksIsbns.contains(isbn)) {
                                                isUniqueInThisList = false;
                                            } else {
                                                backupBooksIsbns.add(isbn);
                                            }
                                        }
                                        if (isbns.size() != book.getIsbns().size()) {
                                            book.updateIsbns(isbns);
                                        }
                                        return isUniqueInThisList;
                                    })
                                    .toList();

                            try {
                                backup(filteredBooks.stream()
                                        .filter(book -> book.getIsbns()
                                                .stream()
                                                .noneMatch(isbn -> filteredBooks.stream()
                                                        .anyMatch(backupBook -> {
                                                                    boolean publishDateTimeDiff = false;
                                                                    if (backupBook.getPublishDateTime() != null) {
                                                                        publishDateTimeDiff = !backupBook.getPublishDateTime().equals(book.getPublishDateTime());
                                                                    }
                                                                    boolean publisherDiff = false;
                                                                    if (backupBook.getPublisher() != null) {
                                                                        publisherDiff = !backupBook.getPublisher().equals(book.getPublisher());
                                                                    }
                                                                    boolean authorDiff = false;
                                                                    if (backupBook.getAuthors() != null) {
                                                                        authorDiff = !backupBook.getAuthors().equals(book.getAuthors());
                                                                    }
                                                                    return (publishDateTimeDiff || publisherDiff || authorDiff) && backupBook.getIsbns().contains(isbn);
                                                                }
                                                        )
                                                )
                                        )
                                        .toList()
                                );
                            } catch (Exception e) {
                                if (searchHistory.getSavedBooks() == 0) {
                                    jdbcTemplate.update("delete from search_history where keyword = ? and source = ?", keyword, source);
                                } else {
                                    searchHistoryService.updateBackupHistory(searchHistory);
                                }
                            }
                        }
                );
            }
        }

        return new BooksInfoResponseDto(
                booksInfo.getTotalPage(),
                totalElements,
                booksInfo.getBooks()
                        .stream()
                        .map(BookClientMapper::toResponse)
                        .toList()
        );
    }

    private void backup(List<Book> books) {

        books = books.stream()
                .filter(book -> book.getIsbns().stream().noneMatch(bookService::checkDuplication))
                .toList();

        if (!books.isEmpty()) {
            bookService.backup(books);
        }
    }

    private boolean isSearched(SearchHistory searchHistory, long totalElements) {

        return searchHistory.getSavedBooks() >= totalElements;
    }

    private List<Book> getBooks(String keyword, int page, int pageSize) {

        return findBooksByRecursive(new KeywordSearchRequestDto(keyword, page, pageSize)).getBooks();
    }

    private BooksInfo findBooksByRecursive(KeywordSearchRequestDto keywordSearchRequestDto) {

        BooksInfo booksInfo;

        try {
            booksInfo = searchModule.findBooks(keywordSearchRequestDto);
        } catch (KakaoErrorException | NaverErrorException e) {
            log.error("cause: {}, message: {}", e.getStackTrace()[0].toString(), e.getMessage());
            booksInfo = findBooksByRecursive(keywordSearchRequestDto);
        }

        return booksInfo;
    }

}
