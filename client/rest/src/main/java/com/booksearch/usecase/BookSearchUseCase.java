package com.booksearch.usecase;

import com.booksearch.dto.BookRequestDto;
import com.booksearch.dto.BookResponseDto;
import com.booksearch.internal.service.BookService;
import com.booksearch.mapper.BookMapper;
import com.booksearch.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookSearchUseCase {

    private final BookService bookService;

    @Transactional
    public String create(String originName) {
        Book sonaki = bookService.create(originName);
        return null;
    }

    @Transactional(readOnly = true)
    public List<BookResponseDto> findBooks(BookRequestDto bookSearchRequestDto) {
        List<Book> books = bookService.findBooks(BookMapper.toDomain(bookSearchRequestDto));
        return books.stream()
                .map(BookMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public BookResponseDto find(BookRequestDto bookSearchRequestDto) {
        bookService.find(BookMapper.toDomain(bookSearchRequestDto));
        return null;
    }

}
