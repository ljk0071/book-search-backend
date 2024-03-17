package com.booksearch.module.search;

import com.booksearch.dto.kakao.KakaoResponseDto;
import com.booksearch.dto.common.KeywordSearchRequestDto;
import com.booksearch.exception.KakaoErrorException;
import com.booksearch.mapper.BookClientMapper;
import com.booksearch.model.Book;
import com.booksearch.model.BooksInfo;
import com.booksearch.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Slf4j
public class KakaoSearchModuleImpl implements SearchModule {

    private static final String KAKAO_API_KEY = FileUtils.getProperty("kakao.book.api");

    private static final WebClient KAKAO_CONNECTION = WebClient.builder()
            .defaultHeaders(headers -> {
                headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
                headers.add("Authorization", "KakaoAK " + KAKAO_API_KEY);
            })
            .build();

    @Override
    public Book findBook(String isbn) {

        WebClient kakaoRequest = KAKAO_CONNECTION.mutate()
                .baseUrl(
                        "https://dapi.kakao.com/v3/search/book?target=title&" +
                                "query=" + isbn +
                                "&sort=latest" +
                                "&target=isbn"
                )
                .build();

        KakaoResponseDto kakaoBooks = Optional.ofNullable(getBody(kakaoRequest))
                .orElseThrow(KakaoErrorException::new);

        List<Book> books = kakaoBooks.getDocuments()
                .stream()
                .map(BookClientMapper::kakaoToDomain)
                .toList();

        if (books.isEmpty()) {
            return new NaverSearchModuleImpl().findBook(isbn);
        }

        return books.getFirst();
    }

    @Override
    public BooksInfo findBooks(KeywordSearchRequestDto keywordSearchRequestDto) {

        WebClient kakaoRequest = KAKAO_CONNECTION.mutate()
                .baseUrl(
                        "https://dapi.kakao.com/v3/search/book?target=title&" +
                                "query=" + keywordSearchRequestDto.getSearchKeyword() +
                                "&page=" + keywordSearchRequestDto.getPage() +
                                "&size=" + keywordSearchRequestDto.getPageSize() +
                                "&sort=latest" +
                                "&target=title"
                )
                .build();

        KakaoResponseDto kakaoBooks = Optional.ofNullable(getBody(kakaoRequest))
                .orElseThrow(KakaoErrorException::new);

        return new BooksInfo(
                kakaoBooks.getMeta().getPageableCount(),
                kakaoBooks.getMeta().getTotalCount(),
                kakaoBooks.getDocuments()
                        .stream()
                        .map(BookClientMapper::kakaoToDomain)
                        .toList()
        );
    }

    private KakaoResponseDto getBody(WebClient kakaoRequest) {

        log.info("findByKakao");

        return kakaoRequest.get()
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
                .block();
    }
}
