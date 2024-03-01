package com.booksearch.mapper;

import com.booksearch.entity.BookEntity;
import com.booksearch.model.Book;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BookInfraMapper {

    public static BookEntity toEntity(Book book) {
        return BookEntity.builder()
                .title(book.getTitle())
                .authors(book.getAuthors())
                .contents(book.getContents())
                .publishDateTime(book.getPublishDateTime())
                .isbn10(book.getIsbn10())
                .isbn13(book.getIsbn13())
                .price(book.getPrice())
                .publisher(book.getPublisher())
                .thumbnail(book.getThumbnail())
                .build();
    }

    public static Book toDomain(BookEntity bookEntity) {

        /*
          title
          authors
          contents
          publishDateTime
          isbn10
          isbn13
          price
          publisher
          thumbnail
         */
        return new Book(
                bookEntity.getTitle(),
                bookEntity.getAuthors(),
                bookEntity.getContents(),
                bookEntity.getPublishDateTime(),
                bookEntity.getIsbn10(),
                bookEntity.getIsbn13(),
                bookEntity.getPrice(),
                bookEntity.getPublisher(),
                bookEntity.getThumbnail()
        );
    }
}
