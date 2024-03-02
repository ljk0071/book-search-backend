package com.booksearch.internal.repository;

import com.booksearch.model.Book;
import com.booksearch.model.BooksInfo;
import com.booksearch.model.PageInfo;

import java.util.List;

public interface BookRepository {

    void createBooks(List<Book> books);

    Book find(Long id);

    BooksInfo findByAllParams(Book book, PageInfo pageInfo);

    BooksInfo findByTitle(String title, PageInfo pageInfo);

    BooksInfo findByAuthors(String authors, PageInfo pageInfo);

    BooksInfo findByContents(String contents, PageInfo pageInfo);

    BooksInfo findByIsbn(String isbn, PageInfo pageInfo);

    BooksInfo findByIsbn10(String isbn10, PageInfo pageInfo);

    BooksInfo findByIsbn13(String isbn13, PageInfo pageInfo);

    BooksInfo findByPublisher(String publisher, PageInfo pageInfo);

}
