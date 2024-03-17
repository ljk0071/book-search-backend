package com.booksearch.dto.naver;

import com.booksearch.model.NaverBook;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class NaverResponseDto {

    private LocalDateTime lastBuilderDate;

    private int total;

    private int start;

    private int display;

    private final List<NaverBook> items = new ArrayList<>();

    public NaverResponseDto() {
    }

    public NaverResponseDto(LocalDateTime lastBuilderDate, int total, int start, int display, List<NaverBook> items) {
        this.lastBuilderDate = lastBuilderDate;
        this.total = total;
        this.start = start;
        this.display = display;
        this.items.addAll(items);
    }

    public List<NaverBook> getItems() {
        return new ArrayList<>(items);
    }
}
