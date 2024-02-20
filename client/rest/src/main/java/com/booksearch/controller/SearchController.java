//package com.booksearch.controller;
//
//import com.booksearch.dto.BookSearchRequestDto;
//import com.booksearch.dto.SearchRequestDto;
//import com.booksearch.usecase.BookSearchUseCase;
//import com.example.dh.repository.Users;
//import com.example.dh.service.SearchService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.IOException;
//import java.util.Map;
//
//@RestController
//@RequiredArgsConstructor
//public class SearchController {
//
//    private final BookSearchUseCase bookSearchUseCase;
//
//    @RequestMapping("/search")
//    public String search(SearchRequestDto searchRequestDto) {
//        return bookSearchUseCase.find(searchRequestDto);
//    }
//
//    @RequestMapping("/title")
//    public String searchDeep(String title, Model model) {
//        model.addAttribute("info", sService.searchDeep(title));
//        return "searchDeep";
//    }
//}
