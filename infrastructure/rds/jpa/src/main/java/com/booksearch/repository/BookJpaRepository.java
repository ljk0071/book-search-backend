package com.booksearch.repository;

import com.booksearch.entity.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    Page<BookEntity> findByAuthors(String originalName, Pageable pageable);

    Page<BookEntity> findByTitleContains(String title, Pageable pageable);
}
