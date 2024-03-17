package com.booksearch.deserializer.xml;

import com.booksearch.util.DateUtils;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.time.LocalDate;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {

    public LocalDate unmarshal(String v) throws Exception {
        return LocalDate.parse(v, DateUtils.getYyyyMMdd());
    }

    public String marshal(LocalDate v) throws Exception {
        return v.toString();
    }
}
