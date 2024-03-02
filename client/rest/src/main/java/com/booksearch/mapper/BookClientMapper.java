package com.booksearch.mapper;

import com.booksearch.dto.BookRequestDto;
import com.booksearch.dto.BookResponseDto;
import com.booksearch.model.Book;
import com.booksearch.model.KakaoBook;
import com.booksearch.model.NaverBook;
import com.booksearch.util.DateUtils;
import com.booksearch.util.StringUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BookClientMapper {

    public static BookResponseDto naverToResponse(NaverBook naverBook) {
        return BookResponseDto.builder()
                .authors(naverBook.getAuthor())
                .contents(naverBook.getDescription())
                .publishDateTime(DateUtils.convertTillDay(naverBook.getPubdate()))
                .isbn(naverBook.getIsbn())
                .price(naverBook.getDiscount())
                .publisher(naverBook.getPublisher())
                .thumbnail(naverBook.getImage())
                .build();
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
                kakaoBook.getDateTime().toLocalDateTime(),
                kakaoBook.getIsbn(),
                kakaoBook.getPrice(),
                kakaoBook.getPublisher(),
                kakaoBook.getThumbnail()
        );
    }

    public static BookResponseDto toResponse(Book book) {
        String isbn;
        if (StringUtils.hasText(book.getIsbn10()) && StringUtils.hasText(book.getIsbn13())) {
            isbn = StringUtils.joinWithCommas(Arrays.asList(book.getIsbn10(), book.getIsbn13()));
        } else if (StringUtils.hasText(book.getIsbn10())) {
            isbn = book.getIsbn10();
        } else {
            isbn = book.getIsbn13();
        }
        return BookResponseDto.builder()
                .authors(book.getAuthors())
                .contents(book.getContents())
                .publishDateTime(DateUtils.convertTillDay(book.getPublishDateTime()))
                .isbn(isbn)
                .price(book.getPrice())
                .publisher(book.getPublisher())
                .thumbnail(book.getThumbnail())
                .build();
    }

    public static Book toDomain(BookRequestDto bookSearchRequestDto) {

        String searchKeyword = bookSearchRequestDto.getSearchKeyword();

        String type = bookSearchRequestDto.getSearchType();

        if ("ALL".equals(type)) {
            return new Book(
                    searchKeyword,
                    searchKeyword,
                    searchKeyword,
                    searchKeyword,
                    searchKeyword
            );
        } else {
            return new Book(searchKeyword, type);
        }
    }
}
