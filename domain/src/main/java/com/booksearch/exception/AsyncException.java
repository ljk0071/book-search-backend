package com.booksearch.exception;

public class AsyncException extends RuntimeException {

    public AsyncException() {
    }

    public AsyncException(String message, Throwable cause) {
        super(message, cause);
    }
}
