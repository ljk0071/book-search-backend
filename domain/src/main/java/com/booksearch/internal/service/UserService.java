package com.booksearch.internal.service;

import com.booksearch.internal.repository.UserRepository;
import com.booksearch.model.Authority;
import com.booksearch.model.User;

public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public void createUser(User user, Authority authority) {
        repository.createUser(user, authority);
    }

    public User loginUser(User user) {
        return repository.loginUser(user);
    }
}
