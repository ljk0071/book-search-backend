package com.booksearch.mapper;

import com.booksearch.dto.BookRequestDto;
import com.booksearch.dto.BookResponseDto;
import com.booksearch.entity.BookEntity;
import com.booksearch.model.Book;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BookMapper {

    public static BookResponseDto toResponse(Book book) {
        return BookResponseDto.builder()
                .authors(book.getAuthors())
                .contents(book.getContents())
                .publishDateTime(book.getPublishDateTime())
                .isbn(book.getIsbn())
                .price(book.getPrice())
                .publisher(book.getPublisher())
                .thumbnail(book.getThumbnail())
                .build();
    }

    public static Book toDomain(BookRequestDto bookSearchRequestDto) {

        String searchKeyword = bookSearchRequestDto.getSearchKeyword();

        String type = bookSearchRequestDto.getSearchType();

        if ("ALL".equals(type)) {
            return new Book(
                    searchKeyword,
                    searchKeyword,
                    searchKeyword,
                    searchKeyword,
                    searchKeyword
            );
        } else {
            return new Book(searchKeyword, type);
        }
    }
}
