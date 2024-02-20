package com.booksearch.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BookResponseDto {

    private String authors;

    private String contents;

    private LocalDateTime publishDateTime;

    private String isbn;

    private int price;

    private String publisher;

    private String thumbnail;

    @Builder
    public BookResponseDto(String authors, String contents, LocalDateTime publishDateTime, String isbn, int price, String publisher, String thumbnail) {
        this.authors = authors;
        this.contents = contents;
        this.publishDateTime = publishDateTime;
        this.isbn = isbn;
        this.price = price;
        this.publisher = publisher;
        this.thumbnail = thumbnail;
    }
}
