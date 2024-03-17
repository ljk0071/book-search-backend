package com.booksearch.status;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KakaoStatus {
    private static boolean status = false;
    private static LocalDateTime errorAt;

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
