package com.booksearch.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    private DateUtils() {
    }

    private static final DateTimeFormatter tillSecond = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

    private static final DateTimeFormatter tillDay = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final DateTimeFormatter yyyyMMdd = DateTimeFormatter.ofPattern("yyyyMMdd");

    public static String convertTillSecond(LocalDateTime localDateTime) {
        return tillSecond.format(localDateTime);
    }

    public static String convertTillDay(LocalDateTime localDateTime) {
        return tillDay.format(localDateTime);
    }

    public static String convertTillSecond(LocalDate localDate) {
        return tillSecond.format(localDate);
    }

    public static String convertTillDay(LocalDate localDate) {
        return tillDay.format(localDate);
    }

    public static DateTimeFormatter getYyyyMMdd() {
        return yyyyMMdd;
    }
}
