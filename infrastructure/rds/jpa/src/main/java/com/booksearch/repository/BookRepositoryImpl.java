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

import java.util.List;

@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepository {

    private static final String PUBLISH_DATE_TIME = "publishDateTime";

    private final BookJpaRepository repository;

    @Override
    public void createBooks(List<Book> books) {
        repository.saveAll(
                books.stream()
                        .map(BookInfraMapper::toEntity)
                        .toList()
        );
    }

    @Override
    public BooksInfo findByAllParams(Book book, PageInfo pageInfo) {
        int pageSize = pageInfo.getPageSize();
        Page<BookEntity> bookEntities = repository.findByAllParams(
                book.getTitle(),
                book.getAuthors(),
                book.getContents(),
                book.getIsbn10(),
                book.getIsbn13(),
                book.getPublisher(),
                PageRequest.of(
                        (pageInfo.getPage() - 1) * pageSize,
                        pageSize,
                        Sort.Direction.DESC,
                        PUBLISH_DATE_TIME
                ));
        return new BooksInfo(
                bookEntities.getTotalPages(),
                bookEntities.getTotalElements(),
                bookEntities.getContent()
                        .stream()
                        .map(BookInfraMapper::toDomain)
                        .toList()
        );
    }

    @Override
    public BooksInfo findByTitle(String title, PageInfo pageInfo) {
        int pageSize = pageInfo.getPageSize();
        Page<BookEntity> bookEntities = repository.findByTitleContains(
                title,
                PageRequest.of(
                        (pageInfo.getPage() - 1) * pageSize,
                        pageSize,
                        Sort.Direction.DESC,
                        PUBLISH_DATE_TIME
                )
        );
        return new BooksInfo(
                bookEntities.getTotalPages(),
                bookEntities.getTotalElements(),
                bookEntities.getContent()
                        .stream()
                        .map(BookInfraMapper::toDomain)
                        .toList()
        );
    }

    @Override
    public BooksInfo findByAuthors(String authors, PageInfo pageInfo) {
        int pageSize = pageInfo.getPageSize();
        Page<BookEntity> bookEntities = repository.findByAuthorsContains(
                authors,
                PageRequest.of(
                        (pageInfo.getPage() - 1) * pageSize,
                        pageSize,
                        Sort.Direction.DESC,
                        PUBLISH_DATE_TIME
                )
        );
        return new BooksInfo(
                bookEntities.getTotalPages(),
                bookEntities.getTotalElements(),
                bookEntities.getContent()
                        .stream()
                        .map(BookInfraMapper::toDomain)
                        .toList()
        );
    }

    @Override
    public BooksInfo findByContents(String contents, PageInfo pageInfo) {
        int pageSize = pageInfo.getPageSize();
        Page<BookEntity> bookEntities = repository.findByContentsContains(
                contents,
                PageRequest.of(
                        (pageInfo.getPage() - 1) * pageSize,
                        pageSize,
                        Sort.Direction.DESC,
                        PUBLISH_DATE_TIME
                )
        );
        return new BooksInfo(
                bookEntities.getTotalPages(),
                bookEntities.getTotalElements(),
                bookEntities.getContent()
                        .stream()
                        .map(BookInfraMapper::toDomain)
                        .toList()
        );
    }

    @Override
    public BooksInfo findByIsbn(String isbn, PageInfo pageInfo) {
        int pageSize = pageInfo.getPageSize();

        Page<BookEntity> bookEntities = repository.findByIsbn10ContainsOrIsbn13(
                isbn,
                isbn,
                PageRequest.of(
                        (pageInfo.getPage() - 1) * pageSize,
                        pageSize,
                        Sort.Direction.DESC,
                        PUBLISH_DATE_TIME
                )
        );

        return new BooksInfo(
                bookEntities.getTotalPages(),
                bookEntities.getTotalElements(),
                bookEntities.getContent()
                        .stream()
                        .map(BookInfraMapper::toDomain)
                        .toList()
        );
    }

    @Override
    public BooksInfo findByIsbn10(String isbn10, PageInfo pageInfo) {
        int pageSize = pageInfo.getPageSize();

        Page<BookEntity> bookEntities = repository.findByIsbn10Contains(
                isbn10,
                PageRequest.of(
                        (pageInfo.getPage() - 1) * pageSize,
                        pageSize,
                        Sort.Direction.DESC,
                        PUBLISH_DATE_TIME
                )
        );

        return new BooksInfo(
                bookEntities.getTotalPages(),
                bookEntities.getTotalElements(),
                bookEntities.getContent()
                        .stream()
                        .map(BookInfraMapper::toDomain)
                        .toList()
        );
    }

    @Override
    public BooksInfo findByIsbn13(String isbn13, PageInfo pageInfo) {

        int pageSize = pageInfo.getPageSize();

        Page<BookEntity> bookEntities = repository.findByIsbn13Contains(
                isbn13,
                PageRequest.of(
                        (pageInfo.getPage() - 1) * pageSize,
                        pageSize,
                        Sort.Direction.DESC,
                        PUBLISH_DATE_TIME
                )
        );

        return new BooksInfo(
                bookEntities.getTotalPages(),
                bookEntities.getTotalElements(),
                bookEntities.getContent()
                        .stream()
                        .map(BookInfraMapper::toDomain)
                        .toList()
        );
    }

    @Override
    public BooksInfo findByPublisher(String publisher, PageInfo pageInfo) {
        int pageSize = pageInfo.getPageSize();

        Page<BookEntity> bookEntities = repository.findByPublisherContains(
                publisher,
                PageRequest.of(
                        (pageInfo.getPage() - 1) * pageSize,
                        pageSize,
                        Sort.Direction.DESC,
                        PUBLISH_DATE_TIME
                )
        );

        return new BooksInfo(
                bookEntities.getTotalPages(),
                bookEntities.getTotalElements(),
                bookEntities.getContent()
                        .stream()
                        .map(BookInfraMapper::toDomain)
                        .toList()
        );
    }

    @Override
    public Book find(Long id) {
        BookEntity bookEntity = repository.findById(id)
                .orElseThrow(() -> new IllegalStateException("book doesn't exist"));
        return BookInfraMapper.toDomain(bookEntity);
    }
}
