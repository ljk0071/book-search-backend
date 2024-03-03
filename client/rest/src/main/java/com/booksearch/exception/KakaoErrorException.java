package com.booksearch.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KakaoErrorException extends RuntimeException {
    public KakaoErrorException() {
    }

    public KakaoErrorException(String message) {
        super(message);
        log.error(message);
    }
}
