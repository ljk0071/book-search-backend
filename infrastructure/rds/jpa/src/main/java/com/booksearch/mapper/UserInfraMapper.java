package com.booksearch.mapper;

import com.booksearch.entity.AuthorityEntity;
import com.booksearch.entity.UserEntity;
import com.booksearch.model.Authority;
import com.booksearch.model.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserInfraMapper {

    public static UserEntity toEntity(User user) {
        return UserEntity.builder()
                .userId(user.getUserId())
                .password(user.getPassword())
                .nickName(user.getNickName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .isExpired("N")
                .isLocked("N")
                .build();
    }

    public static User toDomain(UserEntity userEntity) {
        return new User(userEntity.getUserId(),
                userEntity.getPassword(),
                userEntity.getNickName(),
                userEntity.getEmail(),
                userEntity.getPhoneNumber(),
                userEntity.getAuthorities().stream()
                        .map(UserInfraMapper::toDomain)
                        .toList(),
                "Y".equals(userEntity.getIsExpired()),
                "Y".equals(userEntity.getIsLocked()));
    }

    private static Authority toDomain(AuthorityEntity authorityEntity) {
        return new Authority(authorityEntity.getName());
    }
}
