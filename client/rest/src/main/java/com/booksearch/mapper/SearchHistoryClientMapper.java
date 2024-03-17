package com.booksearch.mapper;

import com.booksearch.model.SearchHistory;

public class SearchHistoryClientMapper {

    private SearchHistoryClientMapper() {
    }

    public static SearchHistory toDomain(String keyword, long savedBooks, String source) {

        return new SearchHistory(keyword, savedBooks, source);
    }
}
