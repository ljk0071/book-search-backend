package com.booksearch.internal.repository;

import com.booksearch.model.Book;
import com.booksearch.model.BooksInfo;
import com.booksearch.model.PageInfo;

import java.time.LocalDateTime;
import java.util.List;

public interface BookRepository {

    Book create(String originName);

    void createBooks(List<Book> books);

    Book find(Long id);

    BooksInfo findByAllParams(Book book, PageInfo pageInfo);

    BooksInfo findByTitleContains(String title, PageInfo pageInfo);

    BooksInfo findByAuthors(String authors, PageInfo pageInfo);

    BooksInfo findByContents(String contents, PageInfo pageInfo);

    BooksInfo findByPublishDateTime(LocalDateTime publishDateTime, PageInfo pageInfo);

    BooksInfo findByIsbn(String isbn, PageInfo pageInfo);

    BooksInfo findByPrice(int price, PageInfo pageInfo);

    BooksInfo findByPublisher(String publisher, PageInfo pageInfo);

}
