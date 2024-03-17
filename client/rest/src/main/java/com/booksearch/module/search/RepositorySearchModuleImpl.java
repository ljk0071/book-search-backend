package com.booksearch.module.search;

import com.booksearch.dto.common.KeywordSearchRequestDto;
import com.booksearch.internal.service.BookService;
import com.booksearch.mapper.BookClientMapper;
import com.booksearch.model.Book;
import com.booksearch.model.BooksInfo;
import com.booksearch.model.PageInfo;
import com.booksearch.status.KakaoStatus;
import com.booksearch.status.NaverStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
public class RepositorySearchModuleImpl implements SearchModule {

    private final BookService service;

    @Override
    public Book findBook(String isbn) {
        return service.find(isbn);
    }

    @Override
    public BooksInfo findBooks(KeywordSearchRequestDto keywordSearchRequestDto) {

        log.info("findByRepository");

        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);

        if (KakaoStatus.getErrorAt().isBefore(yesterday)) {
            KakaoStatus.setStatus(true);
        }
        if (NaverStatus.getErrorAt().isBefore(yesterday)) {
            NaverStatus.setStatus(true);
        }

        return service.findBooks(
                BookClientMapper.toDomain(keywordSearchRequestDto),
                new PageInfo(
                        keywordSearchRequestDto.getPage(),
                        keywordSearchRequestDto.getPageSize()
                )
        );
    }
}
