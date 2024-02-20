package com.booksearch.usecase;

import com.booksearch.dto.BookRequestDto;
import com.booksearch.dto.BookResponseDto;
import com.booksearch.dto.BooksInfoResponseDto;
import com.booksearch.internal.service.BookService;
import com.booksearch.mapper.BookMapper;
import com.booksearch.model.Book;
import com.booksearch.model.BooksInfo;
import com.booksearch.model.PageInfo;
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
    public BooksInfoResponseDto findBooks(BookRequestDto bookSearchRequestDto) {
        BooksInfo booksInfo = bookService.findBooks(
                new PageInfo(
                        bookSearchRequestDto.getPage(),
                        bookSearchRequestDto.getPageSize()
                ),
                BookMapper.toDomain(bookSearchRequestDto)
        );
        return new BooksInfoResponseDto(
                booksInfo.getTotalPage(),
                booksInfo.getTotalElements(),
                booksInfo.getBooks().stream()
                        .map(BookMapper::toResponse)
                        .toList()
        );
    }

    @Transactional(readOnly = true)
    public BookResponseDto find(Long id) {
        return BookMapper.toResponse(bookService.find(id));
    }

}
