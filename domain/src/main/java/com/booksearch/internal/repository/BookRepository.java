package com.booksearch.internal.repository;

import com.booksearch.model.Book;
import com.booksearch.model.BooksInfo;
import com.booksearch.model.PageInfo;

import java.util.List;

public interface BookRepository {

    void createBooks(List<Book> books);

    void createBook(Book books);

    Book find(Long id);

    BooksInfo findByAllParams(Book book, PageInfo pageInfo);

    BooksInfo findByTitle(String title, PageInfo pageInfo);

    BooksInfo findByAuthors(String authors, PageInfo pageInfo);

    BooksInfo findByContents(String contents, PageInfo pageInfo);

    BooksInfo findByIsbn(String isbn, PageInfo pageInfo);

    Book findByIsbn10(String isbn10);

    Book findByIsbn13(String isbn13);

    BooksInfo findByPublisher(String publisher, PageInfo pageInfo);

}
