package com.booksearch.controller;

import com.booksearch.dto.BookRequestDto;
import com.booksearch.dto.BooksInfoResponseDto;
import com.booksearch.dto.util.ApiResponse;
import com.booksearch.usecase.BookSearchUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookSearchUseCase bookSearchUseCase;

    @GetMapping
    public ApiResponse<BooksInfoResponseDto> findBooks(BookRequestDto bookRequestDto) {

        return ApiResponse.ok(bookSearchUseCase.findBooks(bookRequestDto));
    }
}
