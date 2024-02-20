package com.booksearch.model;

import java.time.LocalDateTime;

public class Book {

    private String title;

    private String authors;

    private String contents;

    private LocalDateTime publishDateTime;

    private String isbn;

    private int price;

    private String publisher;

    private String thumbnail;

    public Book() {
    }

    public Book(String keyword, String type) {
        if ("title".equals(type)) {
            title = keyword;
        } else if ("contents".equals(type)) {
            contents = keyword;
        } else if ("authors".equals(type)) {
            authors = keyword;
        } else if ("isbn".equals(type)) {
            isbn = keyword;
        } else if ("publisher".equals(type)) {
            publisher = keyword;
        }
    }

    public Book(String title, String authors, String contents, String isbn, String publisher) {
        this.title = title;
        this.authors = authors;
        this.contents = contents;
        this.isbn = isbn;
        this.publisher = publisher;
    }

    public Book(String title, String authors, String contents, LocalDateTime publishDateTime, String isbn, int price, String publisher, String thumbnail) {
        this.title = title;
        this.authors = authors;
        this.contents = contents;
        this.publishDateTime = publishDateTime;
        this.isbn = isbn;
        this.price = price;
        this.publisher = publisher;
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthors() {
        return authors;
    }

    public String getContents() {
        return contents;
    }

    public LocalDateTime getPublishDateTime() {
        return publishDateTime;
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

    public String getThumbnail() {
        return thumbnail;
    }
}
