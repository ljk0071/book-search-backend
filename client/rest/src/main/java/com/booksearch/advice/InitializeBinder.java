package com.booksearch.advice;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class InitializeBinder {

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.initDirectFieldAccess();
    }
}
