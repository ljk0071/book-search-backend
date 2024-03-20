package com.booksearch.mapper;

import com.booksearch.entity.UserEntity;
import com.booksearch.model.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserInfraMapper {

    public static UserEntity toEntity(User user) {
        return new UserEntity(user.getUserId(),
                user.getPassword(),
                user.getNickName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getType());
    }
}
