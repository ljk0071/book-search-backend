package com.booksearch.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class SearchHistoryKey implements Serializable {

    @Serial
    private static final long serialVersionUID = -7130898651410765714L;

    @Column(name = "keyword")
    private String keyword;

    @Column(name = "source")
    private String source;
}
