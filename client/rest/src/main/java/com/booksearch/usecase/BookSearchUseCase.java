package com.booksearch.usecase;

import com.booksearch.dto.local.BookResponseDto;
import com.booksearch.dto.local.BooksInfoResponseDto;
import com.booksearch.dto.common.KeywordSearchRequestDto;
import com.booksearch.exception.KakaoErrorException;
import com.booksearch.exception.NaverErrorException;
import com.booksearch.internal.service.BookService;
import com.booksearch.internal.service.SearchHistoryService;
import com.booksearch.mapper.BookClientMapper;
import com.booksearch.model.Book;
import com.booksearch.model.BooksInfo;
import com.booksearch.model.SearchHistory;
import com.booksearch.status.KakaoStatus;
import com.booksearch.status.NaverStatus;
import com.booksearch.factory.SearchModuleFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookSearchUseCase {

    private final BookService bookService;

    private final SearchHistoryService searchHistoryService;

    private final SearchModuleFactory searchModuleFactory;

    private static final int MAX_SAVE_VALUE = 1000;


    @Transactional(readOnly = true)
    public BookResponseDto find(String isbn) {
        return BookClientMapper.toResponse(searchModuleFactory.getNormalStatusModule().findBook(isbn));
    }

    @Transactional
    public BooksInfoResponseDto findBooks(KeywordSearchRequestDto keywordSearchRequestDto) {

        BooksInfo booksInfo = findBooksByRecursive(keywordSearchRequestDto);

        long totalElements = booksInfo.getTotalElements();

        boolean isKakaoOk = KakaoStatus.isStatus();
        boolean isNaverOk = NaverStatus.isStatus();

        String source = isKakaoOk ? "kakao" : "naver";

        String keyword = keywordSearchRequestDto.getSearchKeyword();

        if (checkSearched(isKakaoOk, isNaverOk, keyword, source)) {

            CompletableFuture.runAsync(() -> {

                                searchHistoryService.createBackupHistory(
                                        new SearchHistory(
                                                keyword,
                                                totalElements,
                                                source
                                        )
                                );
                                /*
                                  키워드 당 최대 호출 갯수 1000

                                  저장 중에 error 발생 case -> table에 담아둔 뒤 나중에 batch로 재작업
                                 */
                                List<Book> backupBooks = new ArrayList<>();
                                double maxSave = totalElements > MAX_SAVE_VALUE ? MAX_SAVE_VALUE : totalElements;
                                int pageSize;
                                int totalPage;
                                int lastRequestSize;

                                if (isKakaoOk) {
                                    pageSize = 50;
                                    totalPage = (int) Math.ceil(maxSave / pageSize);
                                    for (int i = 1; i <= totalPage; i++) {
                                        backupBooks.addAll(getBooks(keyword, i, pageSize));
                                    }
                                } else {
                                    pageSize = 100;
                                    lastRequestSize = (int) totalElements % pageSize;
                                    totalPage = (int) Math.ceil(maxSave / pageSize);
                                    for (int i = 1; i <= totalPage; i++) {
                                        if (i == totalPage) {
                                            pageSize = lastRequestSize;
                                        }
                                        backupBooks.addAll(getBooks(keyword, (i - 1) * pageSize, pageSize));
                                    }
                                }


                                backup(backupBooks);
                            }
                    )
                    .handle((result, exception) -> {
                        if (exception != null) {
                            log.error("error when backup book", exception);
                            // TODO 백업 배치 등록하는 프로세스 추가
                            return null;
                        }
                        return result;
                    });

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

    private boolean checkSearched(boolean isKakaoOk, boolean isNaverOk, String keyword, String source) {
        return (isKakaoOk || isNaverOk) &&
                !searchHistoryService.checkSearched(new SearchHistory(keyword, source));
    }

    private List<Book> getBooks(String keyword, int page, int pageSize) {

        return findBooksByRecursive(new KeywordSearchRequestDto(keyword, page, pageSize)).getBooks();
    }

    private BooksInfo findBooksByRecursive(KeywordSearchRequestDto keywordSearchRequestDto) {

        BooksInfo booksInfo;

        try {
            booksInfo = searchModuleFactory.getNormalStatusModule().findBooks(keywordSearchRequestDto);
        } catch (KakaoErrorException | NaverErrorException e) {
            log.error("cause: {}, message: {}", e.getStackTrace()[0].toString(), e.getMessage());
            booksInfo = findBooksByRecursive(keywordSearchRequestDto);
        }

        return booksInfo;
    }

}
