package com.booksearch.repository.book;

import com.booksearch.entity.BookEntity;
import com.booksearch.exception.NoMatchedBookException;
import com.booksearch.internal.repository.BookRepository;
import com.booksearch.mapper.BookInfraMapper;
import com.booksearch.model.Book;
import com.booksearch.model.BooksInfo;
import com.booksearch.model.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepository {

    private static final String PUBLISH_DATE_TIME = "publishDateTime";

    private final BookJpaRepository repository;

    private final JdbcTemplate jdbcTemplate;

    public void bulkInsert(List<Book> books) {

        List<BookEntity> bookEntities = books.stream()
                .map(BookInfraMapper::toEntity)
                .toList();

        jdbcTemplate.batchUpdate(
                "INSERT INTO BOOKS ( " +
                        "TITLE, " +
                        "CONTENTS, " +
                        "AUTHORS, " +
                        "PUBLISHER, " +
                        "THUMBNAIL, " +
                        "PRICE, " +
                        "PUBLISH_DATE_TIME, " +
                        "REGISTER_DATE_TIME) VALUES ( " +
                        "?, ?, ?, ?, ?, ?, ?, ?)",
                bookEntities,
                bookEntities.size(),
                (PreparedStatement ps, BookEntity bookEntity) -> {

                    Timestamp publishDateTime;

                    if (bookEntity.getPublishDateTime() == null) {
                        publishDateTime = null;
                    } else {
                        publishDateTime = Timestamp.valueOf(bookEntity.getPublishDateTime());
                    }

                    ps.setString(1, bookEntity.getTitle());
                    ps.setString(2, bookEntity.getContents());
                    ps.setString(3, bookEntity.getAuthors());
                    ps.setString(4, bookEntity.getPublisher());
                    ps.setString(5, bookEntity.getThumbnail());
                    ps.setInt(6, bookEntity.getPrice());
                    ps.setTimestamp(7, publishDateTime);
                    ps.setTimestamp(8, Timestamp.valueOf(bookEntity.getCreatedAt()));

                });

        long lastInsertId = jdbcTemplate.queryForObject("SELECT currval('books_id_seq')", Long.class);

        int bookEntitiesSize = bookEntities.size();

        List<Map<String, Object>> isbnMaps = new ArrayList<>();

        for (int i = 0; i < bookEntitiesSize; i++) {
            long id = lastInsertId - i;
            bookEntities.get(bookEntitiesSize - i - 1).getIsbns().forEach(isbn -> {
                Map<String, Object> temp = new HashMap<>();
                temp.put("id", id);
                temp.put("isbn", isbn);
                isbnMaps.add(temp);
            });
        }

        jdbcTemplate.batchUpdate(
                "INSERT INTO ISBNS ( " +
                        "id, " +
                        "isbn) VALUES ( " +
                        "?, ?)",
                isbnMaps,
                isbnMaps.size(),
                (PreparedStatement ps, Map<String, Object> isbnMap) -> {
                    ps.setLong(1, (Long) isbnMap.get("id"));
                    ps.setString(2, (String) isbnMap.get("isbn"));
                });
    }

    @Override
    public void createBooks(List<Book> books) {

        List<BookEntity> bookEntities = books.stream()
                .map(BookInfraMapper::toEntity)
                .toList();

        repository.saveAll(bookEntities);
    }

    @Override
    public void createBook(Book book) {
        repository.save(BookInfraMapper.toEntity(book));
    }

    @Override
    public BooksInfo findByAllParams(Book book, PageInfo pageInfo) {
        int pageSize = pageInfo.getPageSize();
        Page<BookEntity> bookEntities = repository.findByAllParams(
                book.getTitle(),
                book.getAuthors(),
                book.getContents(),
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
    public Book findByIsbn(String isbn) {

        BookEntity bookEntity = repository.findByIsbns(isbn)
                .orElseThrow(() -> new NoMatchedBookException("저희가 알고 있는 책이 아닙니다 😢"));

        return BookInfraMapper.toDomain(bookEntity);
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

    @Override
    public boolean checkDuplication(String isbn) {
        return repository.findByIsbns(isbn).isPresent();
    }
}
