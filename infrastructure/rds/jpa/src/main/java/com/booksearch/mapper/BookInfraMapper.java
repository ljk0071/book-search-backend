package com.booksearch.mapper;

import com.booksearch.entity.BookEntity;
import com.booksearch.model.Book;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BookInfraMapper {

    public static BookEntity toEntity(Book book) {
        return BookEntity.builder()
                .authors(book.getAuthors())
                .contents(book.getContents())
                .publishDateTime(book.getPublishDateTime())
                .isbn(book.getIsbn())
                .price(book.getPrice())
                .publisher(book.getPublisher())
                .thumbnail(book.getThumbnail())
                .build();
    }

    public static Book toDomain(BookEntity bookEntity) {

        /*
          authors
          contents
          publishDateTime
          isbn
          price
          publisher
          thumbnail
         */
        return new Book(
                bookEntity.getTitle(),
                bookEntity.getAuthors(),
                bookEntity.getContents(),
                bookEntity.getPublishDateTime(),
                bookEntity.getIsbn(),
                bookEntity.getPrice(),
                bookEntity.getPublisher(),
                bookEntity.getThumbnail()
        );
    }
}
