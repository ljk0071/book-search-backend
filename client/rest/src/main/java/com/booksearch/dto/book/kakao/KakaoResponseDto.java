package com.booksearch.dto.book.kakao;

import com.booksearch.model.KakaoBook;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class KakaoResponseDto {

    private KakaoInfo meta = new KakaoInfo();

    private final List<KakaoBook> documents = new ArrayList<>();

    public KakaoResponseDto() {
    }

    public KakaoResponseDto(boolean isEnd, int pageableCount, int totalCount, List<KakaoBook> documents) {
        this.meta = new KakaoInfo(
                isEnd,
                pageableCount,
                totalCount
        );
        this.documents.addAll(documents);
    }

    public List<KakaoBook> getDocuments() {
        return new ArrayList<>(documents);
    }
}
