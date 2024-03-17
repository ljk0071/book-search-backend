package com.booksearch.model;

public class SearchHistory {

    String keyword;

    Long savedBooks;

    String source;

    public SearchHistory() {
    }

    public SearchHistory(String keyword, String source) {
        this.keyword = keyword;
        this.source = source;
    }

    public SearchHistory(String keyword, Long savedBooks, String source) {
        this.keyword = keyword;
        this.savedBooks = savedBooks;
        this.source = source;
    }

    public String getKeyword() {
        return keyword;
    }

    public Long getSavedBooks() {
        return savedBooks;
    }

    public String getSource() {
        return source;
    }
}
