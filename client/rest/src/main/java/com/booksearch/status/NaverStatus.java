package com.booksearch.status;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NaverStatus {
    private static boolean status = true;
    private static LocalDateTime errorAt;

    public static boolean isStatus() {
        return status;
    }

    public static void setStatus(boolean status) {
        NaverStatus.status = status;
    }

    public static LocalDateTime getErrorAt() {
        return errorAt;
    }

    public static void setErrorAt(LocalDateTime errorAt) {
        NaverStatus.errorAt = errorAt;
    }
}
