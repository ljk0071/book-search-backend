package com.booksearch.dto.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserRequestDto {

    private String userId;

    private String password;

    private String nickName;

    private String email;

    private String phoneNumber;

    private String type;

    public void setPassword(String password) {
        this.password = password;
    }
}
