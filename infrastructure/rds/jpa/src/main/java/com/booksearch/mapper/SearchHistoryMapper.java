package com.booksearch.mapper;

import com.booksearch.entity.SearchHistoryEntity;
import com.booksearch.model.SearchHistory;

public class SearchHistoryMapper {

    private SearchHistoryMapper() {
    }

    public static SearchHistoryEntity toEntity(SearchHistory searchHistory) {
        return SearchHistoryEntity.builder()
                .keyword(searchHistory.getKeyword())
                .savedBooks(searchHistory.getSavedBooks())
                .source(searchHistory.getSource())
                .build();
    }

    public static SearchHistory toDomain(SearchHistoryEntity searchHistoryEntity) {
        return new SearchHistory(
                searchHistoryEntity.getKeyword(),
                searchHistoryEntity.getSavedBooks(),
                searchHistoryEntity.getSource()
        );
    }
}
