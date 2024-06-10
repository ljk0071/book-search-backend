package com.booksearch.util;

import com.booksearch.exception.AsyncException;

import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AsyncUtils {

    private static final Logger logger = Logger.getLogger(AsyncUtils.class.getName());

    private AsyncUtils() {
    }

    public static void runAsync(Runnable runnable) {
        CompletableFuture.runAsync(runnable)
                .handle((result, exception) -> {
                    if (exception != null) {
                        logger.log(Level.SEVERE, exception, () -> "error when async");
                        throw new AsyncException();
                    }
                    return result;
                });
    }
}
