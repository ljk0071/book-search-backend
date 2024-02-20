package com.booksearch.model;

public class PageInfo {

    private int page;

    private int pageSize;

    public PageInfo() {
    }

    public PageInfo(int page, int pageSize) {
        this.page = page;
        this.pageSize = pageSize;
    }

    public int getPage() {
        return page;
    }

    public int getPageSize() {
        return pageSize;
    }
}
