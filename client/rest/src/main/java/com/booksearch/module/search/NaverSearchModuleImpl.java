package com.booksearch.module.search;

import com.booksearch.dto.common.KeywordSearchRequestDto;
import com.booksearch.dto.naver.NaverResponseDto;
import com.booksearch.dto.naver.NaverXmlBook;
import com.booksearch.dto.naver.NaverXmlBook.Channel.Item;
import com.booksearch.exception.NaverErrorException;
import com.booksearch.exception.NoMatchedBookException;
import com.booksearch.mapper.BookClientMapper;
import com.booksearch.model.Book;
import com.booksearch.model.BooksInfo;
import com.booksearch.status.KakaoStatus;
import com.booksearch.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.codec.xml.Jaxb2XmlDecoder;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
public class NaverSearchModuleImpl implements SearchModule {

    private static final String NAVER_CLIENT_ID = FileUtils.getProperty("naver.book.api.id");
    private static final String NAVER_CLIENT_SECRET = FileUtils.getProperty("naver.book.api.secret");
    private static final WebClient NAVER_CONNECTION = WebClient.builder()
            .defaultHeaders(headers -> {
                headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE);
                headers.add("X-Naver-Client-Id", NAVER_CLIENT_ID);
                headers.add("X-Naver-Client-Secret", NAVER_CLIENT_SECRET);
            })
            .exchangeStrategies(
                    ExchangeStrategies.builder()
                            .codecs(configurer -> configurer.defaultCodecs().jaxb2Decoder(new Jaxb2XmlDecoder()))
                            .build()
            )
            .build();

    public NaverSearchModuleImpl() {
    }

    @Override
    public Book findBook(String isbn) {

        WebClient naverRequest = NAVER_CONNECTION.mutate()
                .baseUrl(
                        "https://openapi.naver.com/v1/search/book_adv.xml?d_isbn=" + isbn
                )
                .build();

        NaverXmlBook naverXmlBook = getBody(naverRequest, NaverXmlBook.class);

        Item book = naverXmlBook.getChannel().getItem();

        if (book == null) {
            throw new NoMatchedBookException("저희가 알고 있는 책이 아닙니다 😢");
        }

        return BookClientMapper.naverXmlToDomain(book);
    }

    @Override
    public BooksInfo findBooks(KeywordSearchRequestDto keywordSearchRequestDto) {

        WebClient naverRequest = NAVER_CONNECTION.mutate()
                .baseUrl(
                        "https://openapi.naver.com/v1/search/book.json?" +
                                "query=" + keywordSearchRequestDto.getSearchKeyword() +
                                "&display=" + keywordSearchRequestDto.getPageSize() +
                                "&start=" + keywordSearchRequestDto.getPage() +
                                "&sort=date"
                )
                .build();

        NaverResponseDto naverBooks = Optional.ofNullable(getBody(naverRequest, NaverResponseDto.class))
                .orElseThrow(NaverErrorException::new);

        return new BooksInfo(
                (int) Math.ceil(
                        (double) naverBooks.getTotal() / naverBooks.getDisplay()),
                naverBooks.getTotal(),
                naverBooks.getItems()
                        .stream()
                        .map(BookClientMapper::naverToDomain)
                        .toList()
        );
    }

    private <T> T getBody(WebClient naverRequest, Class<T> clazz) {

        log.info("findByNaver");

        checkKakaoErrorTime();

        return naverRequest.get()
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        Mono.error(new NaverErrorException(
                                String.format(
                                        "4xx 요청 오류. statusCode: %s, response: %s, header: %s",
                                        response.statusCode(),
                                        response.bodyToMono(String.class),
                                        response.headers().asHttpHeaders()
                                )
                        )))
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        Mono.error(new NaverErrorException(
                                String.format(
                                        "5xx 시스템 오류. statusCode: %s, response: %s, header: %s",
                                        response.statusCode(),
                                        response.bodyToMono(String.class),
                                        response.headers().asHttpHeaders()
                                )
                        ))
                )
                .bodyToMono(clazz)
                .block();
    }

    private void checkKakaoErrorTime() {
        if (KakaoStatus.getErrorAt() != null && KakaoStatus.getErrorAt().isBefore(LocalDateTime.now().minusDays(1))) {
            KakaoStatus.setStatus(true);
        }
    }

}
