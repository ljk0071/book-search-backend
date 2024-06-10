package com.booksearch.mapper;

import com.booksearch.entity.SearchHistoryEntity;
import com.booksearch.entity.SearchHistoryKey;
import com.booksearch.model.SearchHistory;

public class SearchHistoryMapper {

    private SearchHistoryMapper() {
    }

    public static SearchHistoryEntity toEntity(SearchHistory searchHistory) {
        return SearchHistoryEntity.builder()
                .searchHistoryKey(new SearchHistoryKey(searchHistory.getKeyword(), searchHistory.getSource()))
                .savedBooks(searchHistory.getSavedBooks())
                .build();
    }

    public static SearchHistory toDomain(SearchHistoryEntity searchHistoryEntity) {
        return new SearchHistory(
                searchHistoryEntity.getSearchHistoryKey().getKeyword(),
                searchHistoryEntity.getSavedBooks(),
                searchHistoryEntity.getSearchHistoryKey().getSource()
        );
    }
}
