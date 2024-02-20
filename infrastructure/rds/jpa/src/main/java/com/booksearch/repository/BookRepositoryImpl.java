package com.booksearch.repository;

import com.booksearch.entity.BookEntity;
import com.booksearch.internal.repository.BookRepository;
import com.booksearch.mapper.BookMapper;
import com.booksearch.model.Book;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepository {

    private final BookJpaRepository repository;

    @Override
    public Book create(String originName) {
        return null;
    }

    @Override
    public List<Book> findByAuthors(String authors) {
        return null;
    }

    @Override
    public List<Book> findByContents(String contents) {
        return null;
    }

    @Override
    public List<Book> findByPublishDateTime(LocalDateTime publishDateTime) {
        return null;
    }

    @Override
    public List<Book> findByIsbn(String isbn) {
        return null;
    }

    @Override
    public List<Book> findByPrice(int price) {
        return null;
    }

    @Override
    public List<Book> findByPublisher(String publisher) {
        return null;
    }

    @Override
    public Book find(Book book) {
        String authors = book.getAuthors();
        BookEntity bookEntity = repository.findByAuthors(authors).orElseThrow(() -> new IllegalStateException(authors + " doesn't exist"));
        return BookMapper.toDomain(bookEntity);
    }

    //    @Override
//    public Book create(String originName) {
//        BaseEntity sonakiEntity = BaseEntity.builder()
//                .originalName(originName)
//                .build();
//
//        return SonakiMapper.toDomain(repository.save(sonakiEntity));
//    }
//
//    @Override
//    public Book find(String originName) {
//        BaseEntity sonakiEntity = repository.findByOriginalName(originName)
//                .orElseThrow(() -> new IllegalStateException(originName + "doesn't exist"));
//
//        return SonakiMapper.toDomain(sonakiEntity);
//    }
}
