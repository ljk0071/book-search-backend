package com.booksearch;

import static org.springframework.restdocs.snippet.Attributes.key;

import org.springframework.restdocs.snippet.Attributes;

public interface RestDocsFormatGenerator {

    static Attributes.Attribute fieldName(String name) {
        return key("fieldName").value(name);
    }

    static Attributes.Attribute getDateFormatYearMonthDay() {
        return key("format").value("yyyy-MM-dd");
    }

    static Attributes.Attribute getDateFormatYearMonthDayHourMinuteSecond() {
        return key("format").value("yyyy-MM-dd HH:mm:ss");
    }
}
