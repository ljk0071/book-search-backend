package com.booksearch.internal.repository;

import com.booksearch.model.Book;

import java.time.LocalDateTime;
import java.util.List;

public interface BookRepository {

    Book create(String originName);

    Book find(Book book);

    List<Book> findByAuthors(String authors);

    List<Book> findByContents(String contents);

    List<Book> findByPublishDateTime(LocalDateTime publishDateTime);

    List<Book> findByIsbn(String isbn);

    List<Book> findByPrice(int price);

    List<Book> findByPublisher(String publisher);

}
