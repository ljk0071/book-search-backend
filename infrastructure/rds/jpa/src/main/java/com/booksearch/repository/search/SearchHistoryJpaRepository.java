package com.booksearch.repository.search;

import com.booksearch.entity.SearchHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SearchHistoryJpaRepository extends JpaRepository<SearchHistoryEntity, Long> {

    Optional<SearchHistoryEntity> findByKeywordAndSource(String keyword, String source);
}
