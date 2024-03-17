package com.booksearch.exception;

import com.booksearch.status.KakaoStatus;

import java.time.LocalDateTime;

public class KakaoErrorException extends RuntimeException {

    public KakaoErrorException() {
        setErrorStatus();
    }

    public KakaoErrorException(String message) {
        super(message);
        setErrorStatus();
    }

    private void setErrorStatus() {
        KakaoStatus.setStatus(false);
        KakaoStatus.setErrorAt(LocalDateTime.now());
    }

}
