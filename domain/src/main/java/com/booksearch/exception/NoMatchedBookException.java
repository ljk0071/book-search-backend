package com.booksearch.exception;

public class NoMatchedBookException extends RuntimeException {

    public NoMatchedBookException() {
    }

    public NoMatchedBookException(String message) {
        super(message);
    }
}
