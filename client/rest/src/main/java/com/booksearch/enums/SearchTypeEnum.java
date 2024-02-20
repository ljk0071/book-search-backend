package com.booksearch.enums;

import lombok.Getter;

@Getter
public enum SearchTypeEnum {
    ALL("전체"),
    TITLE("제목"),
    CONTENT("내용"),
    AUTHORS("작가"),
    PUBLISHER("출판사");

    private final String value;

    SearchTypeEnum(String value) {
        this.value = value;
    }
}
