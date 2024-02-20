package com.booksearch.controller;

import com.booksearch.dto.BookRequestDto;
import com.booksearch.dto.BookResponseDto;
import com.booksearch.dto.util.ApiResponse;
import com.booksearch.usecase.BookSearchUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookSearchUseCase bookSearchUseCase;

    @GetMapping
    public ApiResponse<List<BookResponseDto>> findBooks(BookRequestDto bookRequestDto) {

        return ApiResponse.ok(bookSearchUseCase.findBooks(bookRequestDto));
    }

//    @PostMapping
//    public ApiResponse<String> create(
//            @RequestBody BookSearchRequestDto sonakiRequestDto
//    ) {
//        if (!StringUtils.hasText(sonakiRequestDto.getOriginName())) {
//            throw new IllegalArgumentException("originName required");
//        }
//        return ApiResponse.ok(sonakiUseCase.create(sonakiRequestDto.getOriginName()));
//    }
//
//    @GetMapping
//    public ApiResponse<String> get(
//            @RequestParam(name = "originName", required = false) String originName
//    ) {
//        if (!StringUtils.hasText(originName)) {
//            throw new IllegalArgumentException("originName required");
//        }
//        return ApiResponse.ok(sonakiUseCase.find(originName));
//    }
}
