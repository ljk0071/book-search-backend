package com.booksearch.status;

import java.time.LocalDateTime;

public class KakaoStatus {
    private static boolean status = true;
    private static LocalDateTime errorAt;

    private KakaoStatus() {
    }

    public static boolean isStatus() {
        return status;
    }

    public static void setStatus(boolean status) {
        KakaoStatus.status = status;
    }

    public static LocalDateTime getErrorAt() {
        return errorAt;
    }

    public static void setErrorAt(LocalDateTime errorAt) {
        KakaoStatus.errorAt = errorAt;
    }
}
