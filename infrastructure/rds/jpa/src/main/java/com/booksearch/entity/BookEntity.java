package com.booksearch.entity;

import com.booksearch.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "books")
public class BookEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TITLE", length = 300)
    private String title;

    @Column(name = "AUTHORS", length = 100)
    private String authors;

    @Column(name = "CONTENTS", length = 9000)
    private String contents;

    @Column(name = "PUBLISH_DATE_TIME")
    private LocalDateTime publishDateTime;

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
//    @PrimaryKeyJoinColumn
//    private List<IsbnEntity> isbns = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "isbns", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "isbn")
    private final List<String> isbns = new ArrayList<>();

    @Column(name = "PRICE")
    private int price;

    @Column(name = "PUBLISHER", length = 500)
    private String publisher;

    @Column(name = "THUMBNAIL", length = 300)
    private String thumbnail;

    public BookEntity(Long id, String title, String authors, String contents, LocalDateTime publishDateTime, int price, String publisher, String thumbnail) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.contents = contents;
        this.publishDateTime = publishDateTime;
        this.price = price;
        this.publisher = publisher;
        this.thumbnail = thumbnail;
    }

    public BookEntity(LocalDateTime updateAt, Long id, String title, String authors, String contents, LocalDateTime publishDateTime, int price, String publisher, String thumbnail) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.contents = contents;
        this.publishDateTime = publishDateTime;
        this.price = price;
        this.publisher = publisher;
        this.thumbnail = thumbnail;
    }

    @Builder
    public BookEntity(LocalDateTime updateAt, Long id, String title, String authors, String contents, LocalDateTime publishDateTime, List<String> isbns, int price, String publisher, String thumbnail) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.contents = contents;
        this.publishDateTime = publishDateTime;
        this.isbns.addAll(isbns);
        this.price = price;
        this.publisher = publisher;
        this.thumbnail = thumbnail;
    }

    public BookEntity() {
    }
}
