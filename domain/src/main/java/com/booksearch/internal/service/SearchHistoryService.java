package com.booksearch.internal.service;

import com.booksearch.internal.repository.SearchHistoryRepository;
import com.booksearch.model.SearchHistory;

public class SearchHistoryService {

    private final SearchHistoryRepository repository;

    public SearchHistoryService(SearchHistoryRepository repository) {
        this.repository = repository;
    }


    public void createBackupHistory(SearchHistory searchHistory) {
        repository.createBackupHistory(searchHistory);
    }

    public boolean checkSearched(SearchHistory searchHistory) {
        return repository.checkSearched(searchHistory);
    }
}
