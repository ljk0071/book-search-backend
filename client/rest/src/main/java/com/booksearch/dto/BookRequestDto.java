package com.booksearch.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookRequestDto extends SearchRequestDto {

    private String authors;

    private String title;

    private String contents;

    private LocalDateTime publishDateTime;

    private String isbn;

    private int price;

    private String publisher;

    private String thumbnail;

    public BookRequestDto(String searchKeyword, String searchType, int page, int pageSize) {
        super(searchKeyword, searchType, page, pageSize);
    }
}
