package com.booksearch.controller;

import com.booksearch.dto.book.local.BookResponseDto;
import com.booksearch.dto.book.local.BooksInfoResponseDto;
import com.booksearch.dto.common.KeywordSearchRequestDto;
import com.booksearch.dto.util.ApiResponse;
import com.booksearch.usecase.BookSearchUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookSearchUseCase bookSearchUseCase;

    @GetMapping
    public ApiResponse<BooksInfoResponseDto> findBooks(KeywordSearchRequestDto keywordSearchRequestDto) {

        return ApiResponse.ok(bookSearchUseCase.findBooks(keywordSearchRequestDto));
    }

    @GetMapping("/{isbn}")
    public ApiResponse<BookResponseDto> find(@PathVariable String isbn) {

        return ApiResponse.ok(bookSearchUseCase.find(isbn));
    }
}
