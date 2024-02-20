package com.booksearch.internal.service;

import com.booksearch.internal.repository.BookRepository;
import com.booksearch.model.Book;
import com.booksearch.util.StringUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class BookService {
    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public Book create(String originName) {
        return repository.create(originName);
    }

    public List<Book> findBooks(Book book) {

        List<Book> books = new ArrayList<>();

        if (StringUtils.hasText(book.getAuthors())) {
            books = repository.findByAuthors(book.getAuthors());
        } else if (StringUtils.hasText(book.getContents())) {
            books = repository.findByContents(book.getContents());
        } else if (book.getPublishDateTime() != null) {
            books = repository.findByPublishDateTime(book.getPublishDateTime());
        } else if (book.getIsbn() != null) {
            books = repository.findByIsbn(book.getIsbn());
        }


        return books;
    }

    public Book find(Book book) {
        return repository.find(book);
    }
}
