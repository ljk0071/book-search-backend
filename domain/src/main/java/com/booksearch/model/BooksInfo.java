package com.booksearch.model;

import java.util.ArrayList;
import java.util.List;

public class BooksInfo {

    private int totalPage;

    private long totalElements;

    private List<Book> books;

    public BooksInfo() {
    }

    public BooksInfo(int totalPage, long totalElements, List<Book> books) {
        this.totalPage = totalPage;
        this.totalElements = totalElements;
        this.books = books;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public List<Book> getBooks() {
        return new ArrayList<>(books);
    }
}