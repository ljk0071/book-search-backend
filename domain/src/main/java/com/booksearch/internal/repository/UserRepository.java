package com.booksearch.internal.repository;

import com.booksearch.model.Authority;
import com.booksearch.model.User;

public interface UserRepository {

    void createUser(User user, Authority authority);

    User loginUser(User user);

    User findByUserId(String nickName);
}
