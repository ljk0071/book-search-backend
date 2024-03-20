package com.booksearch.advice;

import com.booksearch.dto.util.ApiResponse;
import com.booksearch.exception.NoMatchedBookException;
import com.booksearch.exception.UserValidationException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(IllegalStateException.class)
    public ApiResponse<CustomMessage> illegalStateException(IllegalStateException exception) {
        return ApiResponse.fail(new CustomMessage(exception.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ApiResponse<CustomMessage> illegalArgumentException(IllegalArgumentException exception) {
        return ApiResponse.fail(new CustomMessage(exception.getMessage()));
    }

    @ExceptionHandler(NoMatchedBookException.class)
    public ApiResponse<CustomMessage> noMatchedBookException(NoMatchedBookException exception) {
        return ApiResponse.fail(new CustomMessage(exception.getMessage()));
    }

    @ExceptionHandler(UserValidationException.class)
    public ApiResponse<CustomMessage> userValidationException(UserValidationException exception) {
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
