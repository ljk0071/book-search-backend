package com.booksearch.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BookResponseDto {

    private String authors;

    private String title;

    private String contents;

    private String publishDateTime;

    private String isbn;

    private int price;

    private String publisher;

    private String thumbnail;

    @Builder
    public BookResponseDto(String authors, String title, String contents, String publishDateTime, String isbn, int price, String publisher, String thumbnail) {
        this.authors = authors;
        this.title = title;
        this.contents = contents;
        this.publishDateTime = publishDateTime;
        this.isbn = isbn;
        this.price = price;
        this.publisher = publisher;
        this.thumbnail = thumbnail;
    }
}
