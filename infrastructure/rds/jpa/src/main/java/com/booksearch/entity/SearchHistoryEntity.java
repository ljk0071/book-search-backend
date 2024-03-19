package com.booksearch.entity;

import com.booksearch.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "search_history")
public class SearchHistoryEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "keyword")
    private String keyword;

    @Column(name = "saved_books")
    private long savedBooks;

    @Column(name = "source")
    private String source;

    @Builder
    public SearchHistoryEntity(LocalDateTime updateAt, Long id, String keyword, long savedBooks, String source) {
        super(updateAt);
        this.id = id;
        this.keyword = keyword;
        this.savedBooks = savedBooks;
        this.source = source;
    }

    public SearchHistoryEntity() {
        super(null);
    }
}
