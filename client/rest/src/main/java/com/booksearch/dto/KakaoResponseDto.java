package com.booksearch.dto;

import com.booksearch.model.KakaoBook;
import lombok.Getter;

import java.util.List;

@Getter
public class KakaoResponseDto {

    KakaoInfo meta;

    List<KakaoBook> documents;
}
