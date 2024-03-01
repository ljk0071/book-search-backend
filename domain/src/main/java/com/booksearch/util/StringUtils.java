package com.booksearch.util;

import java.util.List;
import java.util.StringJoiner;

public class StringUtils {

    public static boolean hasText(String text) {
        return text != null && !text.replace(" ", "").isEmpty();
    }

    public static String joinWithCommas(List<String> texts) {
        StringJoiner sj = new StringJoiner(", ");
        texts.forEach(sj::add);
        return sj.toString();
    }
}
