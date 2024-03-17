package com.booksearch.dto.common;

import lombok.Getter;

@Getter
public class KeywordSearchRequestDto {

    private String searchKeyword;

    private int page;

    private int pageSize;

    public KeywordSearchRequestDto(String searchKeyword, int page, int pageSize) {
        this.searchKeyword = searchKeyword;
        this.page = page;
        this.pageSize = pageSize;
    }
}
