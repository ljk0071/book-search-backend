package com.booksearch.repository;

import com.booksearch.entity.BookEntity;
import com.booksearch.internal.repository.BookRepository;
import com.booksearch.mapper.BookInfraMapper;
import com.booksearch.model.Book;
import com.booksearch.model.BooksInfo;
import com.booksearch.model.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    public BooksInfo findByAuthors(String authors, PageInfo pageInfo) {
        int pageSize = pageInfo.getPageSize();
        Page<BookEntity> bookEntities = repository.findByAuthors(
                authors,
                PageRequest.of(
                        (pageInfo.getPage() - 1) * pageSize,
                        pageSize,
                        Sort.Direction.DESC,
                        "publishDateTime"
                )
        );
        return new BooksInfo(
                bookEntities.getTotalPages(),
                bookEntities.getTotalElements(),
                bookEntities.getContent().stream()
                        .map(BookInfraMapper::toDomain)
                        .toList()
        );
    }

    @Override
    public List<Book> findByContents(String contents, PageInfo pageInfo) {
        return null;
    }

    @Override
    public List<Book> findByPublishDateTime(LocalDateTime publishDateTime, PageInfo pageInfo) {
        return null;
    }

    @Override
    public List<Book> findByIsbn(String isbn, PageInfo pageInfo) {
        return null;
    }

    @Override
    public List<Book> findByPrice(int price, PageInfo pageInfo) {
        return null;
    }

    @Override
    public List<Book> findByPublisher(String publisher, PageInfo pageInfo) {
        return null;
    }

    @Override
    public Book find(Long id) {
        BookEntity bookEntity = repository.findById(id).orElseThrow(() -> new IllegalStateException("book doesn't exist"));
        return BookInfraMapper.toDomain(bookEntity);
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
