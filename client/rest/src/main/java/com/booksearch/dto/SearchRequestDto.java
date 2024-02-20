package com.booksearch.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public abstract class SearchRequestDto {

    private String searchKeyword;

    private String searchType;

    private int page;
}
