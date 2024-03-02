package com.booksearch.model;

import java.time.LocalDateTime;

public class Book {

    private String title;

    private String authors;

    private String contents;

    private LocalDateTime publishDateTime;

    private String isbn10;

    private String isbn13;

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
            parsingIsbn(keyword);
        } else if ("publisher".equals(type)) {
            publisher = keyword;
        }
    }

    // for findByAllParams
    public Book(String title, String authors, String contents, String isbn, String publisher) {
        this.title = title;
        this.authors = authors;
        this.contents = contents;
        this.isbn10 = isbn;
        this.isbn13 = isbn;
        this.publisher = publisher;
    }

    public Book(String title, String authors, String contents, LocalDateTime publishDateTime, String isbn, int price, String publisher, String thumbnail) {
        this.title = title;
        this.authors = authors;
        this.contents = contents;
        this.publishDateTime = publishDateTime;
        parsingIsbn(isbn);
        this.price = price;
        this.publisher = publisher;
        this.thumbnail = thumbnail;
    }

    public Book(String title, String authors, String contents, LocalDateTime publishDateTime, String isbn10, String isbn13, int price, String publisher, String thumbnail) {
        this.title = title;
        this.authors = authors;
        this.contents = contents;
        this.publishDateTime = publishDateTime;
        this.isbn10 = isbn10;
        this.isbn13 = isbn13;
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

    public String getIsbn10() {
        return isbn10;
    }

    public String getIsbn13() {
        return isbn13;
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
        if (isbn.length() == 10) {
            this.isbn10 = isbn;
        } else if (isbn.length() == 13) {
            this.isbn13 = isbn;
        } else if (isbn.length() == 24) {
            String[] isbns = isbn.split(" ");
            for (String isbnElements : isbns) {
                setIsbnByParsing(isbnElements);
            }
        } else {
            this.isbn10 = isbn;
            this.isbn13 = isbn;
        }
    }

    private void setIsbnByParsing(String isbn) {
        if (isbn.length() == 10) {
            this.isbn10 = isbn;
        } else {
            this.isbn13 = isbn;
        }
    }
}
