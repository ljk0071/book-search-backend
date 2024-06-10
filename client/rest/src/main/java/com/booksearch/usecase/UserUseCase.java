package com.booksearch.usecase;

import com.booksearch.internal.service.UserService;
import com.booksearch.model.Authority;
import com.booksearch.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserUseCase {

    private final UserService service;

    @Transactional
    public void createUser(User user) {
        service.createUser(user, new Authority("ROLE_USER"));
    }

    @Transactional(readOnly = true)
    public User loginUser(User user) {
        return service.loginUser(user);
    }
}
