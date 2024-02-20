package com.booksearch.dto.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResultType {
    SUCCESS("요청 성공"),
    ERROR("에러");

    private final String message;
}
