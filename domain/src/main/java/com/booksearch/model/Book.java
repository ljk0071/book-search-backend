package com.booksearch.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Book {

    private String title;

    private String authors;

    private String contents;

    private LocalDateTime publishDateTime;

    private final List<String> isbns = new ArrayList<>();

    private int price;

    private String publisher;

    private String thumbnail;

    public Book() {
    }

    public Book(String keyword) {
        title = keyword;
        contents = keyword;
        authors = keyword;
        isbns.add(keyword);
        publisher = keyword;
    }

    public Book(String title, String authors, String contents, LocalDateTime publishDateTime, int price, String publisher, String thumbnail) {
        this.title = title;
        this.authors = authors;
        this.contents = contents;
        this.publishDateTime = publishDateTime;
        this.price = price;
        this.publisher = publisher;
        this.thumbnail = thumbnail;
    }

    public Book(String title, String authors, String contents, LocalDateTime publishDateTime, String isbn, int price, String publisher, String thumbnail) {
        this.title = title;
        this.authors = authors;
        this.contents = contents;
        this.publishDateTime = publishDateTime;
        parsingIsbn(isbn.trim());
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

    public List<String> getIsbns() {
        return new ArrayList<>(isbns);
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

    private void parsingIsbn(String isbn) {
        if (isbn.length() == 10 || isbn.length() == 13) {
            this.isbns.add(isbn);
        } else if (isbn.length() == 24 || isbn.length() == 27) {
            this.isbns.addAll(Arrays.asList(isbn.split(" ")));
        }
    }
}
