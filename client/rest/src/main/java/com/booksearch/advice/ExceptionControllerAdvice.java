package com.booksearch.advice;

import com.booksearch.dto.util.ApiResponse;
import com.booksearch.exception.NoMatchedBookException;
import lombok.Getter;
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

    @ExceptionHandler(NoMatchedBookException.class)
    public ApiResponse<CustomMessage> noMatchedBookException(NoMatchedBookException exception) {
        return ApiResponse.fail(new CustomMessage(exception.getMessage()));
    }

    @Getter
    public static class CustomMessage {

        private String message;

        public CustomMessage() {
        }

        public CustomMessage(String message) {
            this.message = message;
        }
    }
}
