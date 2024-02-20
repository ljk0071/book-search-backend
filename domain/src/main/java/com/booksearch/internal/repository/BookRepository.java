package com.booksearch.internal.repository;

import com.booksearch.model.Book;
import com.booksearch.model.BooksInfo;
import com.booksearch.model.PageInfo;

import java.time.LocalDateTime;
import java.util.List;

public interface BookRepository {

    Book create(String originName);

    Book find(Long id);

    BooksInfo findByAuthors(String authors, PageInfo pageInfo);

    List<Book> findByContents(String contents, PageInfo pageInfo);

    List<Book> findByPublishDateTime(LocalDateTime publishDateTime, PageInfo pageInfo);

    List<Book> findByIsbn(String isbn, PageInfo pageInfo);

    List<Book> findByPrice(int price, PageInfo pageInfo);

    List<Book> findByPublisher(String publisher, PageInfo pageInfo);

}
