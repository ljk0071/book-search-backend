package com.booksearch.advice;

import com.booksearch.dto.util.ApiResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(IllegalStateException.class)
    public ApiResponse<String> illegalStateException(IllegalStateException exception) {
        return ApiResponse.fail(exception.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ApiResponse<String> illegalArgumentException(IllegalArgumentException exception) {
        return ApiResponse.fail(exception.getMessage());
    }
}
