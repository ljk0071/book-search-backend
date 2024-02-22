package com.booksearch.usecase;

import com.booksearch.dto.BookRequestDto;
import com.booksearch.dto.BookResponseDto;
import com.booksearch.dto.BooksInfoResponseDto;
import com.booksearch.dto.NaverResponseDto;
import com.booksearch.internal.service.BookService;
import com.booksearch.mapper.BookClientMapper;
import com.booksearch.model.BooksInfo;
import com.booksearch.model.PageInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class BookSearchUseCase {
    private final String NAVER_CLIENT_ID;
    private final String NAVER_CLIENT_SECRET;
    private final BookService bookService;

    public BookSearchUseCase(
            @Value("${naver.book.api.id}") String NAVER_CLIENT_ID,
            @Value("${naver.book.api.secret}") String NAVER_CLIENT_SECRET,
            BookService bookService) {
        this.NAVER_CLIENT_ID = NAVER_CLIENT_ID;
        this.NAVER_CLIENT_SECRET = NAVER_CLIENT_SECRET;
        this.bookService = bookService;
    }

    @Transactional
    public Mono<NaverResponseDto> getSyncedFromNaver(BookRequestDto bookSearchRequestDto) {
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
