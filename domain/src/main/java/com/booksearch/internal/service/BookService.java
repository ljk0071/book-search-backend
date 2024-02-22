package com.booksearch.internal.service;

import com.booksearch.internal.repository.BookRepository;
import com.booksearch.model.Book;
import com.booksearch.model.BooksInfo;
import com.booksearch.model.PageInfo;
import com.booksearch.util.StringUtils;

public class BookService {
    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public BooksInfo findBooks(PageInfo pageInfo, Book book) {

        BooksInfo booksInfo = null;

        if (StringUtils.hasText(book.getAuthors())) {
            booksInfo = repository.findByAuthors(book.getAuthors(), pageInfo);
        }

//        else if (StringUtils.hasText(book.getContents())) {
//            books = repository.findByContents(book.getContents(), pageInfo);
//        } else if (book.getPublishDateTime() != null) {
//            books = repository.findByPublishDateTime(book.getPublishDateTime(), pageInfo);
//        } else if (book.getIsbn() != null) {
//            books = repository.findByIsbn(book.getIsbn(), pageInfo);
//        }


        return booksInfo;
    }

    public Book find(Long id) {
        return repository.find(id);
    }
}
