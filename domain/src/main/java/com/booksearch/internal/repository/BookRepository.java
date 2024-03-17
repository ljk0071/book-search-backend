package com.booksearch.internal.repository;

import com.booksearch.model.Book;
import com.booksearch.model.BooksInfo;
import com.booksearch.model.PageInfo;

import java.util.List;

public interface BookRepository {

    void createBooks(List<Book> books);

    void createBook(Book books);

    void bulkInsert(List<Book> books);

    Book find(Long id);

    BooksInfo findByAllParams(Book book, PageInfo pageInfo);

    BooksInfo findByTitle(String title, PageInfo pageInfo);

    BooksInfo findByAuthors(String authors, PageInfo pageInfo);

    BooksInfo findByContents(String contents, PageInfo pageInfo);

    BooksInfo findByPublisher(String publisher, PageInfo pageInfo);

    Book findByIsbn(String isbn);

    boolean checkDuplication(String isbn);
}
