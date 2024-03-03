package com.booksearch.entity;

import com.booksearch.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "books")
@NoArgsConstructor
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

    @Column(name = "ISBN10", length = 10)
    private String isbn10;

    @Column(name = "ISBN13", length = 13)
    private String isbn13;

    @Column(name = "PRICE")
    private int price;

    @Column(name = "PUBLISHER", length = 500)
    private String publisher;

    @Column(name = "THUMBNAIL", length = 300)
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
