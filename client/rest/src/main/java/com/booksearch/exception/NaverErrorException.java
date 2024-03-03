package com.booksearch.exception;

import com.booksearch.status.NaverStatus;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
public class NaverErrorException extends RuntimeException {

    public NaverErrorException() {
    }

    public NaverErrorException(String message, NaverStatus naverStatus) {
        super(message);
        log.error(message);
        naverStatus.setNaverOk(false);
        naverStatus.setNaverErrorAt(LocalDateTime.now());
    }
}
