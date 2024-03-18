package com.booksearch.exception;

import com.booksearch.status.NaverStatus;

import java.time.LocalDateTime;

public class NaverErrorException extends RuntimeException {

    public NaverErrorException() {
        setErrorStatus();
    }

    public NaverErrorException(String message) {
        super(message);
        setErrorStatus();
    }

    private void setErrorStatus() {
        NaverStatus.setStatus(false);
        NaverStatus.setErrorAt(LocalDateTime.now());
    }

}
