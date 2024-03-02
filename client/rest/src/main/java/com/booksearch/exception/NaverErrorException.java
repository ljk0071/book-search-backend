package com.booksearch.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NaverErrorException extends RuntimeException {

    public NaverErrorException() {
    }

    public NaverErrorException(String message) {
        super(message);
    }
}
