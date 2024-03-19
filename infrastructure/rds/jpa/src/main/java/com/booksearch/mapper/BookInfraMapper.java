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
                .isbns(book.getIsbns())
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
        StringBuilder sb = new StringBuilder();

        bookEntity.getIsbns().forEach(sb::append);

        return new Book(
                bookEntity.getTitle(),
                bookEntity.getAuthors(),
                bookEntity.getContents(),
                bookEntity.getPublishDateTime(),
                sb.toString(),
                bookEntity.getPrice(),
                bookEntity.getPublisher(),
                bookEntity.getThumbnail()
        );
    }
}
