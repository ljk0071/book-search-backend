package com.booksearch.util;

import java.util.List;
import java.util.StringJoiner;

public class StringUtils {

    private StringUtils() {
    }

    public static boolean hasText(String text) {
        return text != null && !text.replace(" ", "").isEmpty();
    }

    public static String joinWithCommas(List<String> texts) {
        StringJoiner sj = new StringJoiner(", ");
        texts.forEach(sj::add);
        return sj.toString();
    }

    public static boolean hasFinalConsonant(String text) {
        char lastWord = text.charAt(text.length() - 1);
        return (lastWord - 0xac00) % 28 > 0;
    }

}
