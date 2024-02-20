package com.booksearch.internal.service;

import com.booksearch.internal.repository.BookRepository;
import com.booksearch.model.Book;
import com.booksearch.model.Page;
import com.booksearch.util.StringUtils;

import java.awt.print.Pageable;
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

    public List<Book> findBooks(Page page, Book book) {

        List<Book> books = new ArrayList<>();

        if (StringUtils.hasText(book.getAuthors())) {
            books = repository.findByAuthors(book.getAuthors(), page);
        } else if (StringUtils.hasText(book.getContents())) {
            books = repository.findByContents(book.getContents(), page);
        } else if (book.getPublishDateTime() != null) {
            books = repository.findByPublishDateTime(book.getPublishDateTime(), page);
        } else if (book.getIsbn() != null) {
            books = repository.findByIsbn(book.getIsbn(), page);
        }


        return books;
    }

    public Book find(Long id) {
        return repository.find(id);
    }
}
