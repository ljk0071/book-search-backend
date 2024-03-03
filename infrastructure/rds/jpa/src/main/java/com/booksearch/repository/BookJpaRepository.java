package com.booksearch.repository;

import com.booksearch.entity.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookJpaRepository extends JpaRepository<BookEntity, Long> {

    @Query("SELECT NEW com.booksearch.entity.BookEntity (" +
            "id," +
            "title," +
            "authors," +
            "contents," +
            "publishDateTime," +
            "isbn10," +
            "isbn13," +
            "price," +
            "publisher," +
            "thumbnail) " +
            "FROM BookEntity " +
            "WHERE title like %:title% " +
            "OR authors like %:authors% " +
            "or contents like %:contents% " +
            "or isbn10 like %:isbn10% " +
            "or isbn13 like %:isbn13% " +
            "or publisher like %:publisher% ")
    Page<BookEntity> findByAllParams(
            @Param("title")
            String title,
            @Param("authors")
            String authors,
            @Param("contents")
            String contents,
            @Param("isbn10")
            String isbn10,
            @Param("isbn13")
            String isbn13,
            @Param("publisher")
            String publisher,
            Pageable pageable);

    Page<BookEntity> findByTitleContains(String title, Pageable pageable);

    Page<BookEntity> findByAuthorsContains(String originalName, Pageable pageable);

    Page<BookEntity> findByContentsContains(String contents, Pageable pageable);

    Page<BookEntity> findByIsbn10ContainsOrIsbn13(String isbn10, String isbn13, Pageable pageable);

    Optional<BookEntity> findByIsbn10(String isbn10);

    Optional<BookEntity> findByIsbn13(String isbn13);

    Page<BookEntity> findByPublisherContains(String publisher, Pageable pageable);
}
