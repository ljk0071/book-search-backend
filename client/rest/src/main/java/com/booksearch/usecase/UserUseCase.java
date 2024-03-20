package com.booksearch.usecase;

import com.booksearch.dto.user.UserRequestDto;
import com.booksearch.internal.service.UserService;
import com.booksearch.mapper.UserClientMapper;
import com.booksearch.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserUseCase {

    private final UserService service;

    public void createUser(User user) {
        service.createUser(user);
    }
}
