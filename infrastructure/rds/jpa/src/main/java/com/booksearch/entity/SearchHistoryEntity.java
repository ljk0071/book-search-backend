package com.booksearch.entity;

import com.booksearch.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
@Table(name = "search_history")
public class SearchHistoryEntity extends BaseEntity {

    @EmbeddedId
    private SearchHistoryKey searchHistoryKey = new SearchHistoryKey();

    @Column(name = "saved_books")
    private long savedBooks;

    public SearchHistoryEntity() {
    }

    @Builder
    public SearchHistoryEntity(SearchHistoryKey searchHistoryKey, long savedBooks) {
        this.searchHistoryKey = searchHistoryKey;
        this.savedBooks = savedBooks;
    }

    public void updateHistory(long savedBooks) {
        this.savedBooks = savedBooks;
    }
}
