package com.booksearch.dto;

import com.booksearch.model.NaverBook;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class NaverResponseDto {

    private LocalDateTime lastBuilderDate;

    private int total;

    private int start;

    private int display;

    private List<NaverBook> items;
}
