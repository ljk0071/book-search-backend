package com.booksearch.status;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NaverStatus {
    private boolean isNaverOk = true;
    private LocalDateTime naverErrorAt = LocalDateTime.now();
}
