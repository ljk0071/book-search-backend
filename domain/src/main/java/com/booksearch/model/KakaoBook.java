package com.booksearch.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;


public class KakaoBook {

    private List<String> authors;

    private String contents;

    @JsonProperty("datetime")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private ZonedDateTime dateTime;

    private String isbn;

    private int price;

    private String publisher;

    private int salePrice;

    private String status;

    private String thumbnail;

    private String title;

    private List<String> translators;

    private String url;

    public List<String> getAuthors() {
        return new ArrayList<>(authors);
    }

    public String getContents() {
        return contents;
    }

    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getPrice() {
        return price;
    }

    public String getPublisher() {
        return publisher;
    }

    public int getSalePrice() {
        return salePrice;
    }

    public String getStatus() {
        return status;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getTranslators() {
        return new ArrayList<>(translators);
    }

    public String getUrl() {
        return url;
    }
}
