package com.booksearch.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class NaverBook {

    private String title;

    private String link;

    private String image;

    private String author;

    private Integer discount;

    private String publisher;

    @JsonFormat(pattern = "yyyyMMdd")
    private LocalDate pubdate;

    private String isbn;

    private String description;

    public NaverBook() {
    }

    public NaverBook(String title, String link, String image, String author, Integer discount, String publisher, LocalDate pubdate, String isbn, String description) {
        this.title = title;
        this.link = link;
        this.image = image;
        this.author = author;
        this.discount = discount;
        this.publisher = publisher;
        this.pubdate = pubdate;
        this.isbn = isbn;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getImage() {
        return image;
    }

    public String getAuthor() {
        return author;
    }

    public Integer getDiscount() {
        return discount;
    }

    public String getPublisher() {
        return publisher;
    }

    public LocalDate getPubdate() {
        return pubdate;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getDescription() {
        return description;
    }
}
