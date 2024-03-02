package com.booksearch.internal.service;

import com.booksearch.internal.repository.BookRepository;
import com.booksearch.model.Book;
import com.booksearch.model.BooksInfo;
import com.booksearch.model.PageInfo;
import com.booksearch.util.StringUtils;

import java.util.List;

public class BookService {
    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public BooksInfo findBooks(Book book, PageInfo pageInfo) {

        BooksInfo booksInfo = new BooksInfo();

        if (
                StringUtils.hasText(book.getTitle()) &&
                        StringUtils.hasText(book.getContents()) &&
                        StringUtils.hasText(book.getAuthors()) &&
                        (StringUtils.hasText(book.getIsbn10()) || StringUtils.hasText(book.getIsbn13())) &&
                        StringUtils.hasText(book.getPublisher())
        ) {
            booksInfo = repository.findByAllParams(book, pageInfo);
        } else if (StringUtils.hasText(book.getAuthors())) {
            booksInfo = repository.findByAuthors(book.getAuthors(), pageInfo);
        } else if (StringUtils.hasText(book.getContents())) {
            booksInfo = repository.findByContents(book.getContents(), pageInfo);
        } else if (StringUtils.hasText(book.getIsbn10())) {
            booksInfo = repository.findByIsbn(book.getIsbn10(), pageInfo);
        } else if (StringUtils.hasText(book.getIsbn13())) {
            booksInfo = repository.findByIsbn(book.getIsbn13(), pageInfo);
        } else if (StringUtils.hasText(book.getTitle())) {
            booksInfo = repository.findByTitleContains(book.getTitle(), pageInfo);
        }

        return booksInfo;
    }

    public Book find(Long id) {
        return repository.find(id);
    }

    public void createBooks(List<Book> books) {
        repository.createBooks(books);
    }
}
