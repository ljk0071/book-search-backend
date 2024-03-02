package com.booksearch.usecase;

import com.booksearch.exception.KakaoErrorException;
import com.booksearch.exception.NaverErrorException;
import com.booksearch.dto.*;
import com.booksearch.internal.service.BookService;
import com.booksearch.mapper.BookClientMapper;
import com.booksearch.model.Book;
import com.booksearch.model.BooksInfo;
import com.booksearch.model.PageInfo;
import com.booksearch.util.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookSearchUseCase {
    private static final String NAVER_CLIENT_ID = FileUtils.getProperty("naver.book.api.id");
    private static final String NAVER_CLIENT_SECRET = FileUtils.getProperty("naver.book.api.secret");
    private static final String KAKAO_API_KEY = FileUtils.getProperty("kakao.book.api");

    private final BookService bookService;

    public BooksInfo findByNaver(BookRequestDto bookSearchRequestDto) {
        WebClient naverRequest = WebClient.builder()
                .defaultHeaders(headers -> {
                    headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                    headers.add("X-Naver-Client-Id", NAVER_CLIENT_ID);
                    headers.add("X-Naver-Client-Secret", NAVER_CLIENT_SECRET);
                })
                .baseUrl(
                        "https://openapi.naver.com/v1/search/book.json?" +
                                "query=" + bookSearchRequestDto.getSearchKeyword() +
                                "&display=" + bookSearchRequestDto.getPageSize() +
                                "&start=" + bookSearchRequestDto.getPage() +
                                "&sort=date"
                )
                .build();

        NaverResponseDto naverBooks = Optional.ofNullable(
                        naverRequest.get()
                                .retrieve()
                                .bodyToMono(NaverResponseDto.class)
                                .block()
                )
                .orElseThrow(NaverErrorException::new);

        List<Book> books = naverBooks.getItems()
                .stream()
                .map(BookClientMapper::naverToDomain)
                .toList();

        CompletableFuture.runAsync(() -> bookService.createBooks(books));

        return new BooksInfo(
                (int) Math.ceil(
                        (double) naverBooks.getTotal() / naverBooks.getDisplay()),
                naverBooks.getTotal(),
                books
        );
    }

    public BooksInfo findByKakao(BookRequestDto bookSearchRequestDto) {
        WebClient kakaoRequest = WebClient.builder()
                .defaultHeaders(headers -> {
                    headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
                    headers.add("Authorization", "KakaoAK " + KAKAO_API_KEY);
                })
                .baseUrl(
                        "https://dapi.kakao.com/v3/search/book?target=title&" +
                                "query=" + bookSearchRequestDto.getSearchKeyword() +
                                "&page=" + bookSearchRequestDto.getPage() +
                                "&size=" + bookSearchRequestDto.getPageSize() +
                                "&sort=latest"
                )
                .build();

        KakaoResponseDto kakaoBooks = Optional.ofNullable(
                        kakaoRequest.get()
                                .retrieve()
                                .bodyToMono(KakaoResponseDto.class)
                                .block()
                )
                .orElseThrow(() -> new KakaoErrorException("현재 검색 기능에 문제가 있습니다.\n나중에 다시 찾아주세요."));

        List<Book> books = kakaoBooks.getDocuments()
                .stream()
                .map(BookClientMapper::kakaoToDomain)
                .toList();

        CompletableFuture.runAsync(() -> bookService.createBooks(books));

        return new BooksInfo(
                kakaoBooks.getMeta().getPageableCount(),
                kakaoBooks.getMeta().getTotalCount(),
                books
        );
    }

    @Transactional
    public BooksInfoResponseDto findBooks(BookRequestDto bookSearchRequestDto) {

        // 1 db 검색 -> 없으면 네이버 -> 없으면 카카오
        BooksInfo booksInfo = bookService.findBooks(
                BookClientMapper.toDomain(bookSearchRequestDto),
                new PageInfo(
                        bookSearchRequestDto.getPage(),
                        bookSearchRequestDto.getPageSize()
                )
        );

        if (booksInfo.getTotalPage() == 0) {
            try {
                booksInfo = findByNaver(bookSearchRequestDto);
                if (booksInfo.getTotalPage() == 0) {
                    booksInfo = findByKakao(bookSearchRequestDto);
                }
            } catch (NaverErrorException e) {
                booksInfo = findByKakao(bookSearchRequestDto);
            }
        }

        return new BooksInfoResponseDto(
                booksInfo.getTotalPage(),
                booksInfo.getTotalElements(),
                booksInfo.getBooks()
                        .stream()
                        .map(BookClientMapper::toResponse)
                        .toList()
        );
    }

    @Transactional(readOnly = true)
    public BookResponseDto find(Long id) {
        return BookClientMapper.toResponse(bookService.find(id));
    }

}
