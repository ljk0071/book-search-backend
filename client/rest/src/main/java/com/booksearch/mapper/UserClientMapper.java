package com.booksearch.mapper;

import com.booksearch.dto.user.UserRequestDto;
import com.booksearch.model.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserClientMapper {

    public static User toDomain(UserRequestDto requestDto) {
        return new User(requestDto.getUserId(),
                requestDto.getPassword(),
                requestDto.getNickName(),
                requestDto.getEmail(),
                requestDto.getPhoneNumber(),
                null,
                false,
                false);
    }
}
