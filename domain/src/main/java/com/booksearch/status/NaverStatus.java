package com.booksearch.status;

import java.time.LocalDateTime;

public class NaverStatus {
    private static boolean status = true;
    private static LocalDateTime errorAt;

    private NaverStatus() {
    }

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
