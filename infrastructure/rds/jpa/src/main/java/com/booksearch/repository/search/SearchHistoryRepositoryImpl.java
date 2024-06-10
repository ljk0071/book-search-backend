package com.booksearch.repository.search;

import com.booksearch.entity.SearchHistoryEntity;
import com.booksearch.entity.SearchHistoryKey;
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
    public void updateBackupHistory(SearchHistory searchHistory) {
        SearchHistoryEntity searchHistoryEntity = repository.findById(new SearchHistoryKey(searchHistory.getKeyword(), searchHistory.getSource()))
                .orElseGet(SearchHistoryEntity::new);

        searchHistoryEntity.updateHistory(searchHistory.getSavedBooks());

        repository.save(searchHistoryEntity);
    }

    @Override
    public SearchHistory findByKeywordAndSource(SearchHistory searchHistory) {
        return SearchHistoryMapper.toDomain(
                repository.findById(new SearchHistoryKey(searchHistory.getKeyword(), searchHistory.getSource()))
                        .orElseGet(SearchHistoryEntity::new)
        );
    }
}
