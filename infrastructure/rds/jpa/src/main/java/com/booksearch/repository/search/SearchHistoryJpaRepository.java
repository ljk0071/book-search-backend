package com.booksearch.repository.search;

import com.booksearch.entity.SearchHistoryEntity;
import com.booksearch.entity.SearchHistoryKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchHistoryJpaRepository extends JpaRepository<SearchHistoryEntity, SearchHistoryKey> {
}
