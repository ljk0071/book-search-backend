package com.booksearch.repository;

import com.booksearch.entity.SearchHistoryEntity;
import com.booksearch.internal.repository.SearchHistoryRepository;
import com.booksearch.mapper.SearchHistoryMapper;
import com.booksearch.model.SearchHistory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SearchHistoryRepositoryImpl implements SearchHistoryRepository {

    private final SearchHistoryJpaRepository repository;

    @Override
    public void createBackupHistory(SearchHistory searchHistory) {
        repository.save(SearchHistoryMapper.toEntity(searchHistory));
    }

    @Override
    public SearchHistory findByKeywordAndSource(SearchHistory searchHistory) {
        return SearchHistoryMapper.toDomain(
                repository.findByKeywordAndSource(searchHistory.getKeyword(), searchHistory.getSource())
                        .orElseGet(SearchHistoryEntity::new)
        );
    }
}
