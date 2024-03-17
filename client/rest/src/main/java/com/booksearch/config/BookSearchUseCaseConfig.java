package com.booksearch.config;

import com.booksearch.internal.repository.BookRepository;
import com.booksearch.internal.repository.SearchHistoryRepository;
import com.booksearch.internal.service.BookService;
import com.booksearch.internal.service.SearchHistoryService;
import com.booksearch.repository.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class BookSearchUseCaseConfig {

    @Bean
    public BookRepository bookRepository(BookJpaRepository bookJpaRepository, JdbcTemplate jdbcTemplate) {
        return new BookRepositoryImpl(bookJpaRepository, jdbcTemplate);
    }

    @Bean
    public BookService bookService(BookRepository bookRepository) {
        return new BookService(bookRepository);
    }

    @Bean
    public SearchHistoryRepository searchHistoryRepository(SearchHistoryJpaRepository searchHistoryJpaRepository) {
        return new SearchHistoryRepositoryImpl(searchHistoryJpaRepository);
    }

    @Bean
    public SearchHistoryService searchHistoryService(SearchHistoryRepository searchHistoryRepository) {
        return new SearchHistoryService(searchHistoryRepository);
    }
}
