package com.booksearch.usecase;

import com.booksearch.dto.*;
import com.booksearch.exception.KakaoErrorException;
import com.booksearch.exception.NaverErrorException;
import com.booksearch.internal.service.BookService;
import com.booksearch.mapper.BookClientMapper;
import com.booksearch.model.Book;
import com.booksearch.model.BooksInfo;
import com.booksearch.status.NaverStatus;
import com.booksearch.util.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookSearchUseCase {
    private static final String NAVER_CLIENT_ID = FileUtils.getProperty("naver.book.api.id");
    private static final String NAVER_CLIENT_SECRET = FileUtils.getProperty("naver.book.api.secret");
    private static final String KAKAO_API_KEY = FileUtils.getProperty("kakao.book.api");
    private static final NaverStatus NAVER_STATUS = new NaverStatus();
    private static final WebClient NAVER_CONNECTION = WebClient.builder()
            .defaultHeaders(headers -> {
                headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                headers.add("X-Naver-Client-Id", NAVER_CLIENT_ID);
                headers.add("X-Naver-Client-Secret", NAVER_CLIENT_SECRET);
            })
            .build();

    private static final WebClient KAKAO_CONNECTION = WebClient.builder()
            .defaultHeaders(headers -> {
                headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
                headers.add("Authorization", "KakaoAK " + KAKAO_API_KEY);
            })
            .build();

    private final BookService bookService;

    public BooksInfo findByNaver(BookRequestDto bookSearchRequestDto) {

        WebClient naverRequest = NAVER_CONNECTION.mutate()
                .baseUrl(
                        "https://openapi.naver.com/v1/search/book.json?" +
                                "query=" + bookSearchRequestDto.getSearchKeyword() +
                                "&display=" + bookSearchRequestDto.getPageSize() +
                                "&start=" + bookSearchRequestDto.getPage() +
                                "&sort=date"
                )
                .build();

        NaverResponseDto naverBooks =
                Optional.ofNullable(
                                naverRequest.get()
                                        .retrieve()
                                        .onStatus(HttpStatusCode::is4xxClientError, response ->
                                                Mono.error(new NaverErrorException(
                                                        String.format(
                                                                "4xx 요청 오류. statusCode: %s, response: %s, header: %s",
                                                                response.statusCode(),
                                                                response.bodyToMono(String.class),
                                                                response.headers().asHttpHeaders()
                                                        ),
                                                        NAVER_STATUS
                                                )))
                                        .onStatus(HttpStatusCode::is5xxServerError, response ->
                                                Mono.error(new NaverErrorException(
                                                        String.format(
                                                                "5xx 시스템 오류. statusCode: %s, response: %s, header: %s",
                                                                response.statusCode(),
                                                                response.bodyToMono(String.class),
                                                                response.headers().asHttpHeaders()
                                                        ),
                                                        NAVER_STATUS
                                                ))
                                        )
                                        .bodyToMono(NaverResponseDto.class)
                                        .block()
                        )
                        .orElseThrow(NaverErrorException::new);

        List<Book> books = naverBooks.getItems()
                .stream()
                .map(BookClientMapper::naverToDomain)
                .toList();

        return new BooksInfo(
                (int) Math.ceil(
                        (double) naverBooks.getTotal() / naverBooks.getDisplay()),
                naverBooks.getTotal(),
                books
        );
    }

    public BooksInfo findByKakao(BookRequestDto bookSearchRequestDto) {

        if (NAVER_STATUS.getNaverErrorAt().isBefore(LocalDateTime.now().minusDays(1))) {
            NAVER_STATUS.setNaverOk(true);
        }

        WebClient kakaoRequest = KAKAO_CONNECTION.mutate()
                .baseUrl(
                        "https://dapi.kakao.com/v3/search/book?target=title&" +
                                "query=" + bookSearchRequestDto.getSearchKeyword() +
                                "&page=" + bookSearchRequestDto.getPage() +
                                "&size=" + bookSearchRequestDto.getPageSize() +
                                "&sort=latest"
                )
                .build();

        KakaoResponseDto kakaoBooks =
                Optional.ofNullable(
                                kakaoRequest.get()
                                        .retrieve()
                                        .onStatus(HttpStatusCode::is4xxClientError, response ->
                                                Mono.error(new KakaoErrorException(
                                                        String.format(
                                                                "4xx 요청 오류. statusCode: %s, response: %s, header: %s",
                                                                response.statusCode(),
                                                                response.bodyToMono(String.class),
                                                                response.headers().asHttpHeaders()
                                                        )
                                                )))
                                        .onStatus(HttpStatusCode::is5xxServerError, response ->
                                                Mono.error(new KakaoErrorException(
                                                        String.format(
                                                                "5xx 시스템 오류. statusCode: %s, response: %s, header: %s",
                                                                response.statusCode(),
                                                                response.bodyToMono(String.class),
                                                                response.headers().asHttpHeaders()
                                                        )
                                                ))
                                        )
                                        .bodyToMono(KakaoResponseDto.class)
                                        .block()
                        )
                        .orElseThrow(() -> new KakaoErrorException("현재 검색 기능에 문제가 있습니다.\n나중에 다시 찾아주세요."));

        List<Book> books = kakaoBooks.getDocuments()
                .stream()
                .map(BookClientMapper::kakaoToDomain)
                .toList();

        return new BooksInfo(
                kakaoBooks.getMeta().getPageableCount(),
                kakaoBooks.getMeta().getTotalCount(),
                books
        );
    }

    @Transactional
    public BooksInfoResponseDto findBooks(BookRequestDto bookSearchRequestDto) {

        BooksInfo booksInfo;

        try {
            booksInfo = NAVER_STATUS.isNaverOk() ? findByNaver(bookSearchRequestDto) : findByKakao(bookSearchRequestDto);
            if (NAVER_STATUS.isNaverOk() && booksInfo.getTotalPage() == 0) {
                booksInfo = findByKakao(bookSearchRequestDto);
            }
        } catch (NaverErrorException e) {
            log.error(e.getStackTrace()[0].toString());
            booksInfo = findByKakao(bookSearchRequestDto);
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
