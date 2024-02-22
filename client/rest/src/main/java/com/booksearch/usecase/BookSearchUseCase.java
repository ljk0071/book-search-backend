package com.booksearch.usecase;

import com.booksearch.dto.*;
import com.booksearch.internal.service.BookService;
import com.booksearch.mapper.BookClientMapper;
import com.booksearch.model.BooksInfo;
import com.booksearch.model.PageInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class BookSearchUseCase {
    private final String NAVER_CLIENT_ID;
    private final String NAVER_CLIENT_SECRET;
    private final String KAKAO_API_KEY;
    private final BookService bookService;

    public BookSearchUseCase(
            @Value("${naver.book.api.id}") String NAVER_CLIENT_ID,
            @Value("${naver.book.api.secret}") String NAVER_CLIENT_SECRET,
            @Value("${kakao.book.api}") String KAKAO_API_KEY,
            BookService bookService) {
        this.NAVER_CLIENT_ID = NAVER_CLIENT_ID;
        this.NAVER_CLIENT_SECRET = NAVER_CLIENT_SECRET;
        this.KAKAO_API_KEY = KAKAO_API_KEY;
        this.bookService = bookService;
    }

    public Mono<NaverResponseDto> findByNaver(BookRequestDto bookSearchRequestDto) {
        WebClient naverRequest = WebClient.builder()
                .defaultHeaders(headers -> {
                    headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                    headers.add("X-Naver-Client-Id", NAVER_CLIENT_ID);
                    headers.add("X-Naver-Client-Secret", NAVER_CLIENT_SECRET);
                })
                .baseUrl(
                        "https://openapi.naver.com/v1/search/book.json?query=" +
                                bookSearchRequestDto.getSearchKeyword() + "&display=" +
                                bookSearchRequestDto.getPageSize() + "&start=" +
                                bookSearchRequestDto.getPage()
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
                        "https://dapi.kakao.com/v3/search/book?target=title&query=" +
                                bookSearchRequestDto.getSearchKeyword() + "&page=" +
                                bookSearchRequestDto.getPage()
                )
                .build();

        return kakaoRequest.get()
                .retrieve()
                .bodyToMono(KakaoResponseDto.class);
    }

    @Transactional(readOnly = true)
    public BooksInfoResponseDto findBooks(BookRequestDto bookSearchRequestDto) {
        BooksInfo booksInfo = bookService.findBooks(
                new PageInfo(
                        bookSearchRequestDto.getPage(),
                        bookSearchRequestDto.getPageSize()
                ),
                BookClientMapper.toDomain(bookSearchRequestDto)
        );
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
