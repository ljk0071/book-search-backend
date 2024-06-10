package com.booksearch.factory;

import com.booksearch.internal.service.BookService;
import com.booksearch.status.KakaoStatus;
import com.booksearch.status.NaverStatus;
import com.booksearch.module.search.KakaoSearchModuleImpl;
import com.booksearch.module.search.NaverSearchModuleImpl;
import com.booksearch.module.search.RepositorySearchModuleImpl;
import com.booksearch.module.search.SearchModule;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SearchModuleFactory {

    private final BookService bookService;

    @Bean
    public SearchModule getNormalStatusModule() {
        if (!KakaoStatus.isStatus()) {
            return new KakaoSearchModuleImpl();
        } else if (NaverStatus.isStatus()) {
            return new NaverSearchModuleImpl();
        } else {
            return new RepositorySearchModuleImpl(bookService);
        }
    }
}
