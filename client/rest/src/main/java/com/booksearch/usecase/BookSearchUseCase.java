package com.booksearch.usecase;

import com.booksearch.dto.*;
import com.booksearch.internal.service.BookService;
import com.booksearch.mapper.BookClientMapper;
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
import reactor.core.publisher.Mono;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookSearchUseCase {
    private static final String NAVER_CLIENT_ID = FileUtils.getProperty("naver.book.api.id");
    private static final String NAVER_CLIENT_SECRET = FileUtils.getProperty("naver.book.api.secret");
    private static final String KAKAO_API_KEY = FileUtils.getProperty("kakao.book.api");

    private final BookService bookService;

    public Mono<NaverResponseDto> findByNaver(BookRequestDto bookSearchRequestDto) {
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
                                "&start=" + bookSearchRequestDto.getPage()
                )
                .build();

        return naverRequest.get()
                .retrieve()
                .bodyToMono(NaverResponseDto.class);
    }

    public Mono<KakaoResponseDto> findByKakao(BookRequestDto bookSearchRequestDto) {
        WebClient kakaoRequest = WebClient.builder()
                .defaultHeaders(headers -> {
                    headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
                    headers.add("Authorization", "KakaoAK " + KAKAO_API_KEY);
                })
                .baseUrl(
                        "https://dapi.kakao.com/v3/search/book?target=title&" +
                                "query=" + bookSearchRequestDto.getSearchKeyword() +
                                "&page=" + bookSearchRequestDto.getPage()
                )
                .build();

        return kakaoRequest.get()
                .retrieve()
                .bodyToMono(KakaoResponseDto.class);
    }

    @Transactional
    public BooksInfoResponseDto findBooks(BookRequestDto bookSearchRequestDto) {

        BooksInfo booksInfo = bookService.findBooks(
                BookClientMapper.toDomain(bookSearchRequestDto),
                new PageInfo(
                        bookSearchRequestDto.getPage(),
                        bookSearchRequestDto.getPageSize()
                )
        );

        if (booksInfo.getBooks().isEmpty()) {
            log.debug("db is empty, start search in naver");
            NaverResponseDto naverBooks = Optional.ofNullable(findByNaver(bookSearchRequestDto).block())
                    .orElseThrow(() -> new IllegalStateException("검색기능에 문제가 있습니다.\n다시 시도해주세요."));
            if (naverBooks.getItems().isEmpty()) {
                log.debug("naver is empty, start search in kakao");
                KakaoResponseDto kakaoBooks = Optional.ofNullable(findByKakao(bookSearchRequestDto).block())
                        .orElseThrow(() -> new IllegalStateException("검색기능에 문제가 있습니다.\n다시 시도해주세요."));
                bookService.createBooks(kakaoBooks.getDocuments().stream()
                        .map(BookClientMapper::kakaoToDomain)
                        .toList());
                return new BooksInfoResponseDto(
                        kakaoBooks.getMeta().getPageableCount(),
                        kakaoBooks.getMeta().getTotalCount(),
                        kakaoBooks.getDocuments().stream()
                                .map(BookClientMapper::kakaoToResponse)
                                .toList()
                );
            } else {
                bookService.createBooks(naverBooks.getItems().stream()
                        .map(BookClientMapper::naverToDomain)
                        .toList());
                return new BooksInfoResponseDto(
                        (int) Math.ceil(
                                (double) naverBooks.getTotal() / naverBooks.getDisplay()),
                        naverBooks.getTotal(),
                        naverBooks.getItems().stream()
                                .map(BookClientMapper::naverToResponse)
                                .toList()
                );
            }
        }

        return new BooksInfoResponseDto(
                booksInfo.getTotalPage(),
                booksInfo.getTotalElements(),
                booksInfo.getBooks().stream()
                        .map(BookClientMapper::toResponse)
                        .toList()
        );
    }

    @Transactional(readOnly = true)
    public BookResponseDto find(Long id) {
        return BookClientMapper.toResponse(bookService.find(id));
    }

}
