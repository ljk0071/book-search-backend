package com.booksearch.util;

import com.booksearch.exception.UserValidationException;
import com.booksearch.model.User;

import java.util.regex.Pattern;

public class UserValidator {

    private static final Pattern idRegex = Pattern.compile("^[a-zA-Z0-9!@#$%^&*()_+=\\[\\]{};':\"|,.<>/?]{5,20}$");

    private static final Pattern nickNameRegex = Pattern.compile("^[가-힣a-zA-Z0-9!@#$%^&*()_+=\\[\\]{};':\"|,.<>/?]{2,50}$");

    private static final Pattern passwordRegex = Pattern.compile("^(?=.*[a-zA-Z])(?=.*[\\d])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"|,.<>?]).{9,20}$");

    private static final Pattern emailRegex = Pattern.compile("^[a-zA-Z0-9]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$");

    private static final Pattern phoneNumberRegex = Pattern.compile("^\\d{3}-\\d{3,4}-\\d{4}$");

    private UserValidator() {
    }

    public static void checkValidation(User user) {
        if (!(StringUtils.hasText(user.getUserId()) ||
                StringUtils.hasText(user.getPassword()) ||
                StringUtils.hasText(user.getNickName()) ||
                StringUtils.hasText(user.getEmail()) ||
                StringUtils.hasText(user.getPhoneNumber()) ||
                StringUtils.hasText(user.getType()))) {
            throw new UserValidationException("빈 칸을 채워주세요!");
        }

        if (!idRegex.matcher(user.getUserId()).matches()) {
            throw new UserValidationException("아이디를 조건에 맞게 다시 입력해주세요 🥲");
        }

        if (!passwordRegex.matcher(user.getPassword()).matches()) {
            throw new UserValidationException("비밀번호를 조건에 맞게 다시 입력해주세요 🥲");
        }

        if (!nickNameRegex.matcher(user.getNickName()).matches()) {
            throw new UserValidationException("별명을 조건에 맞게 다시 입력해주세요 🥲");
        }

        if (!emailRegex.matcher(user.getEmail()).matches()) {
            throw new UserValidationException("이메일을 조건에 맞게 다시 입력해주세요 🥲");
        }

        if (!phoneNumberRegex.matcher(user.getPhoneNumber()).matches()) {
            throw new UserValidationException("휴대폰 번호를 조건에 맞게 다시 입력해주세요 🥲");
        }
    }
}
