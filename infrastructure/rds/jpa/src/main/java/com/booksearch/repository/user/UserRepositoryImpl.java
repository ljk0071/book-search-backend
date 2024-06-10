package com.booksearch.repository.user;

import com.booksearch.entity.AuthorityEntity;
import com.booksearch.entity.UserEntity;
import com.booksearch.exception.NotFoundUserException;
import com.booksearch.internal.repository.UserRepository;
import com.booksearch.mapper.UserInfraMapper;
import com.booksearch.model.Authority;
import com.booksearch.model.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository repository;

    @Override
    public void createUser(User user, Authority authority) {
        UserEntity userEntity = UserInfraMapper.toEntity(user);
        userEntity.setAuthorities(new AuthorityEntity(authority.getName(), userEntity));
        repository.save(userEntity);
    }

    @Override
    public User loginUser(User user) {
        return UserInfraMapper.toDomain(repository.findByUserId(user.getUserId()).orElseThrow(() -> new NotFoundUserException("존재하지 않는 유저입니다.")));
    }

    @Override
    public User findByUserId(String nickName) {
        return UserInfraMapper.toDomain(repository.findByUserId(nickName).orElseThrow(() -> new NotFoundUserException("존재하지 않는 유저입니다.")));
    }
}
