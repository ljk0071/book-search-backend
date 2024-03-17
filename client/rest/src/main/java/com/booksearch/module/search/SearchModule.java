package com.booksearch.module.search;

import com.booksearch.dto.common.KeywordSearchRequestDto;
import com.booksearch.model.Book;
import com.booksearch.model.BooksInfo;

public interface SearchModule {

    BooksInfo findBooks(KeywordSearchRequestDto keywordSearchRequestDto);

    Book findBook(String isbn);
}
