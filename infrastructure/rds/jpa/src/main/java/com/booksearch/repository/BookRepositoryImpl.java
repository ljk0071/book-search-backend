package com.booksearch.repository;

import com.booksearch.entity.BookEntity;
import com.booksearch.internal.repository.BookRepository;
import com.booksearch.mapper.BookMapper;
import com.booksearch.model.Book;
import com.booksearch.model.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

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
    public List<Book> findByAuthors(String authors, Page page) {
        List<BookEntity> bookEntities = repository.findByAuthors(
                authors,
                PageRequest.of(
                        page.getPage(),
                        page.getPageSize(),
                        Sort.Direction.DESC,
                        "publishDateTime"
                )
        );
        return bookEntities.stream()
                .map(BookMapper::toDomain)
                .toList();
    }

    @Override
    public List<Book> findByContents(String contents, Page page) {
        return null;
    }

    @Override
    public List<Book> findByPublishDateTime(LocalDateTime publishDateTime, Page page) {
        return null;
    }

    @Override
    public List<Book> findByIsbn(String isbn, Page page) {
        return null;
    }

    @Override
    public List<Book> findByPrice(int price, Page page) {
        return null;
    }

    @Override
    public List<Book> findByPublisher(String publisher, Page page) {
        return null;
    }

    @Override
    public Book find(Long id) {
        BookEntity bookEntity = repository.findById(id).orElseThrow(() -> new IllegalStateException("book doesn't exist"));
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
