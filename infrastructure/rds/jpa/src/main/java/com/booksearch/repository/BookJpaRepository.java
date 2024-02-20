package com.booksearch.repository;

import com.booksearch.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookJpaRepository extends JpaRepository<BookEntity, Long> {
    Optional<BookEntity> findByAuthors(String originalName);
}
