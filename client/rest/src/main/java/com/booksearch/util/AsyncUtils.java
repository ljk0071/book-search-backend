package com.booksearch.util;

import com.booksearch.exception.AsyncException;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

@Slf4j
public class AsyncUtils {

    private AsyncUtils() {
    }

    public static void runAsync(Runnable runnable) {
        CompletableFuture.runAsync(runnable)
                .handle((result, exception) -> {
                    if (exception != null) {
                        log.error("error when async", exception);
                        throw new AsyncException();
                    }
                    return result;
                });
    }
}
