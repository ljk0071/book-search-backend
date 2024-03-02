package com.booksearch.entity;

import com.booksearch.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "books")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TITLE", length = 100)
    private String title;

    @Column(name = "AUTHORS", length = 100)
    private String authors;

    @Column(name = "CONTENTS", length = 3000)
    private String contents;

    @Column(name = "PUBLISH_DATE_TIME")
    private LocalDateTime publishDateTime;

    @Column(name = "ISBN10", length = 10, unique = true)
    private String isbn10;

    @Column(name = "ISBN13", length = 13, unique = true)
    private String isbn13;

    @Column(name = "PRICE")
    private int price;

    @Column(name = "PUBLISHER", length = 200)
    private String publisher;

    @Column(name = "THUMBNAIL", length = 200)
    private String thumbnail;

    @Builder
    public BookEntity(Long id, String title, String authors, String contents, LocalDateTime publishDateTime, String isbn10, String isbn13, int price, String publisher, String thumbnail) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.contents = contents;
        this.publishDateTime = publishDateTime;
        this.isbn10 = isbn10;
        this.isbn13 = isbn13;
        this.price = price;
        this.publisher = publisher;
        this.thumbnail = thumbnail;
    }
}
