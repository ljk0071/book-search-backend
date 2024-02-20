package com.booksearch.internal.repository;

import com.booksearch.model.Book;
import com.booksearch.model.Page;

import java.time.LocalDateTime;
import java.util.List;

public interface BookRepository {

    Book create(String originName);

    Book find(Long id);

    List<Book> findByAuthors(String authors, Page page);

    List<Book> findByContents(String contents, Page page);

    List<Book> findByPublishDateTime(LocalDateTime publishDateTime, Page page);

    List<Book> findByIsbn(String isbn, Page page);

    List<Book> findByPrice(int price, Page page);

    List<Book> findByPublisher(String publisher, Page page);

}
