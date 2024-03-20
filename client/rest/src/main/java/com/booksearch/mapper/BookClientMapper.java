package com.booksearch.mapper;

import com.booksearch.dto.book.local.BookResponseDto;
import com.booksearch.dto.common.KeywordSearchRequestDto;
import com.booksearch.dto.book.naver.NaverXmlBook.Channel.Item;
import com.booksearch.model.Book;
import com.booksearch.model.KakaoBook;
import com.booksearch.model.NaverBook;
import com.booksearch.util.DateUtils;
import com.booksearch.util.StringUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BookClientMapper {

    public static Book naverXmlToDomain(Item item) {
        return new Book(
                item.getTitle(),
                item.getAuthor(),
                item.getDescription(),
                LocalDateTime.of(item.getPubdate(), LocalTime.MIN),
                item.getIsbn(),
                item.getDiscount(),
                item.getPublisher(),
                item.getImage()
        );
    }

    public static Book naverToDomain(NaverBook naverBook) {
        return new Book(
                naverBook.getTitle(),
                naverBook.getAuthor(),
                naverBook.getDescription(),
                LocalDateTime.of(naverBook.getPubdate(), LocalTime.MIN),
                naverBook.getIsbn(),
                naverBook.getDiscount(),
                naverBook.getPublisher(),
                naverBook.getImage()
        );
    }

    public static BookResponseDto kakaoToResponse(KakaoBook kakaoBook) {
        return BookResponseDto.builder()
                .authors(StringUtils.joinWithCommas(kakaoBook.getAuthors()))
                .title(kakaoBook.getTitle())
                .contents(kakaoBook.getContents())
                .publishDateTime(DateUtils.convertTillDay(kakaoBook.getDateTime().toLocalDateTime()))
                .isbn(kakaoBook.getIsbn())
                .price(kakaoBook.getPrice())
                .publisher(kakaoBook.getPublisher())
                .thumbnail(kakaoBook.getThumbnail())
                .build();
    }

    public static Book kakaoToDomain(KakaoBook kakaoBook) {
        return new Book(
                kakaoBook.getTitle(),
                StringUtils.joinWithCommas(kakaoBook.getAuthors()),
                kakaoBook.getContents(),
                kakaoBook.getDateTime() == null ? null : kakaoBook.getDateTime().toLocalDateTime(),
                kakaoBook.getIsbn(),
                kakaoBook.getPrice(),
                kakaoBook.getPublisher(),
                kakaoBook.getThumbnail()
        );
    }

    public static BookResponseDto toResponse(Book book) {

        if (book == null) {
            return null;
        }

        String publishDateTime = book.getPublishDateTime() != null ? DateUtils.convertTillDay(book.getPublishDateTime()) : "정보 없음";

        return BookResponseDto.builder()
                .authors(book.getAuthors())
                .title(book.getTitle())
                .contents(book.getContents())
                .publishDateTime(publishDateTime)
                .isbn(StringUtils.joinWithCommas(book.getIsbns()))
                .price(book.getPrice())
                .publisher(book.getPublisher())
                .thumbnail(book.getThumbnail())
                .build();
    }

    public static Book toDomain(KeywordSearchRequestDto keywordSearchRequestDto) {

        return new Book(keywordSearchRequestDto.getSearchKeyword());
    }
}
