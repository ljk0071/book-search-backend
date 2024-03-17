package com.booksearch.dto.local;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BooksInfoResponseDto {

    private int totalPage;

    private long totalElements;

    private List<BookResponseDto> bookResponseDtos;
}
