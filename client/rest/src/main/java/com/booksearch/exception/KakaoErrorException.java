package com.booksearch.exception;

public class KakaoErrorException extends RuntimeException {
    public KakaoErrorException() {
    }

    public KakaoErrorException(String message) {
        super(message);
    }
}
