package com.booksearch.config;

import com.booksearch.internal.repository.BookRepository;
import com.booksearch.internal.service.BookService;
import com.booksearch.repository.BookJpaRepository;
import com.booksearch.repository.BookRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BookSearchUseCaseConfig {

    @Bean
    public BookRepository bookRepository(BookJpaRepository bookJpaRepository) {
        return new BookRepositoryImpl(bookJpaRepository);
    }

    @Bean
    public BookService bookService(BookRepository bookRepository) {
        return new BookService(bookRepository);
    }
}
