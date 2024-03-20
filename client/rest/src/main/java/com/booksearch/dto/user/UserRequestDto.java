package com.booksearch.dto.user;

import lombok.Getter;

@Getter
public class UserRequestDto {

    private String userId;

    private String password;

    private String nickName;

    private String email;

    private String phoneNumber;

    private String type;
}
