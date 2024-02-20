package com.booksearch.repository;

import com.booksearch.entity.BookEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookJpaRepository extends JpaRepository<BookEntity, Long> {
    List<BookEntity> findByAuthors(String originalName, PageRequest pageRequest);
}
