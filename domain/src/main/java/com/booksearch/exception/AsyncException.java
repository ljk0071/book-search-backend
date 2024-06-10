package com.booksearch.exception;

import java.io.Serial;

public class AsyncException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -7458474885263602945L;

    public AsyncException() {
    }

    public AsyncException(String message) {
        super(message);
    }

    public AsyncException(String message, Throwable cause) {
        super(message, cause);
    }
}
