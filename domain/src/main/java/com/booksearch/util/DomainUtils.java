package com.booksearch.util;

import com.booksearch.model.Book;
import com.booksearch.model.SearchHistory;

public class DomainUtils {

    private DomainUtils() {
    }

    public static boolean isEmpty(Book book) {
        return !(StringUtils.hasText(book.getAuthors()) ||
                StringUtils.hasText(book.getTitle()) ||
                StringUtils.hasText(book.getPublisher()) ||
                StringUtils.hasText(book.getContents()) ||
                StringUtils.hasText(book.getThumbnail())) &&
                book.getPrice() == 0 &&
                book.getPublishDateTime() == null &&
                book.getIsbns().isEmpty();
    }

    public static boolean isEmpty(SearchHistory searchHistory) {
        return !(StringUtils.hasText(searchHistory.getKeyword()) ||
                StringUtils.hasText(searchHistory.getSource())) &&
                searchHistory.getSavedBooks() == null;
    }
}
