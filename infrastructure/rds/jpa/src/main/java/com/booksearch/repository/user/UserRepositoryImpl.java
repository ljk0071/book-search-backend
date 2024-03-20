package com.booksearch.repository.user;

import com.booksearch.internal.repository.UserRepository;
import com.booksearch.mapper.UserInfraMapper;
import com.booksearch.model.User;
import com.booksearch.util.UserValidator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository repository;

    @Override
    public void createUser(User user) {
        UserValidator.checkValidation(user);
        repository.save(UserInfraMapper.toEntity(user));
    }
}
