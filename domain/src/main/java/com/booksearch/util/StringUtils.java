package com.booksearch.util;

public class StringUtils {

    public static boolean hasText(String text) {
        return text != null && !text.replace(" ", "").isEmpty();
    }
}
