package com.booksearch.internal.repository;

import com.booksearch.model.SearchHistory;

public interface SearchHistoryRepository {

    void createBackupHistory(SearchHistory searchHistory);

    void updateBackupHistory(SearchHistory searchHistory);

    SearchHistory findByKeywordAndSource(SearchHistory searchHistory);
}
