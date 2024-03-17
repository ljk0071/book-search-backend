package com.booksearch.internal.repository;

import com.booksearch.model.SearchHistory;

public interface SearchHistoryRepository {

    void createBackupHistory(SearchHistory searchHistory);

    SearchHistory findByKeywordAndSource(SearchHistory searchHistory);

    boolean checkSearched(SearchHistory searchHistory);
}
