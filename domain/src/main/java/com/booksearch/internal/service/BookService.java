package com.booksearch.internal.service;

import com.booksearch.internal.repository.BookRepository;
import com.booksearch.model.Book;
import com.booksearch.model.BooksInfo;
import com.booksearch.model.PageInfo;
import com.booksearch.util.StringUtils;

import java.util.ArrayList;
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
                        !book.getIsbns().isEmpty() &&
                        StringUtils.hasText(book.getPublisher())
        ) {
            booksInfo = repository.findByAllParams(book, pageInfo);
        } else if (StringUtils.hasText(book.getAuthors())) {
            booksInfo = repository.findByAuthors(book.getAuthors(), pageInfo);
        } else if (StringUtils.hasText(book.getContents())) {
            booksInfo = repository.findByContents(book.getContents(), pageInfo);
        } else if (!book.getIsbns().isEmpty()) {
            List<Book> books = new ArrayList<>();
            book.getIsbns().forEach(isbn -> books.add(repository.findByIsbn(isbn)));
            booksInfo = new BooksInfo(1, books.size(), books);
        } else if (StringUtils.hasText(book.getTitle())) {
            booksInfo = repository.findByTitle(book.getTitle(), pageInfo);
        } else if (StringUtils.hasText(book.getPublisher())) {
            booksInfo = repository.findByPublisher(book.getTitle(), pageInfo);
        }

        return booksInfo;
    }

    public Book find(String isbn) {
        return repository.findByIsbn(isbn);
    }

    public boolean checkDuplication(String isbn) {

        return repository.checkDuplication(isbn);
    }

    public void backup(List<Book> books) {
        repository.bulkInsert(books);
    }
}
