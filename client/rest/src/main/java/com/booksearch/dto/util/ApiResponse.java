package com.booksearch.dto.util;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {

    private ResultType result;

    private T data;

    private ApiResponse(ResultType result) {
        this.result = result;
    }

    private ApiResponse(ResultType result, T data) {
        this.result = result;
        this.data = data;
    }

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(ResultType.SUCCESS, data);
    }

    public static <T> ApiResponse<T> fail(T data) {
        return new ApiResponse<>(ResultType.ERROR, data);
    }
}
