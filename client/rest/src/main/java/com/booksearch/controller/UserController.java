package com.booksearch.controller;

import com.booksearch.dto.user.UserRequestDto;
import com.booksearch.mapper.UserClientMapper;
import com.booksearch.model.User;
import com.booksearch.usecase.UserUseCase;
import com.booksearch.util.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserUseCase userUseCase;

    @PostMapping
    public void createUser(@RequestBody UserRequestDto userRequestDto) {
        User user = UserClientMapper.toDomain(userRequestDto);
        UserValidator.checkValidation(user);
        userUseCase.createUser(user);
    }
}
