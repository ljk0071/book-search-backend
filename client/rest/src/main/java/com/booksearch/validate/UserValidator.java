package com.booksearch.validate;

import com.booksearch.dto.user.UserRequestDto;
import com.booksearch.exception.UserValidationException;
import com.booksearch.model.User;
import com.booksearch.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class UserValidator {

    private static final Pattern idRegex = Pattern.compile("^[a-zA-Z0-9!@#$%^&*()_+=\\[\\]{};':\"|,.<>/?]{5,20}$");

    private static final Pattern nickNameRegex = Pattern.compile("^[가-힣a-zA-Z0-9!@#$%^&*()_+=\\[\\]{};':\"|,.<>/?]{2,50}$");

    private static final Pattern passwordRegex = Pattern.compile("^(?=.*[a-zA-Z])(?=.*[\\d])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"|,.<>?]).{9,20}$");

    private static final Pattern emailRegex = Pattern.compile("^[a-zA-Z0-9]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$");

    private static final Pattern phoneNumberRegex = Pattern.compile("^\\d{3}\\d{3,4}\\d{4}$");

    private UserValidator() {
    }

    public static void checkValidation(User user) {
        if (!(StringUtils.hasText(user.getUserId()) ||
                StringUtils.hasText(user.getNickName()) ||
                StringUtils.hasText(user.getEmail()) ||
                StringUtils.hasText(user.getPhoneNumber()))) {
            throw new UserValidationException("빈 칸을 채워주세요!");
        }

        List<String> failColumn = new ArrayList<>();

        if (!idRegex.matcher(user.getUserId()).matches()) {
            failColumn.add("아이디");
        }

        if (!nickNameRegex.matcher(user.getNickName()).matches()) {
            failColumn.add("별명");
        }

        if (!emailRegex.matcher(user.getEmail()).matches()) {
            failColumn.add("이메일");
        }

        if (!phoneNumberRegex.matcher(user.getPhoneNumber()).matches()) {
            failColumn.add("휴대폰 번호");
        }

        if (!failColumn.isEmpty()) {
            throw new UserValidationException(queryFailMessage(StringUtils.joinWithCommas(failColumn)));
        }
    }

    public static void checkValidation(UserRequestDto userRequestDto) {
        if (!(StringUtils.hasText(userRequestDto.getUserId()) ||
                StringUtils.hasText(userRequestDto.getPassword()) ||
                StringUtils.hasText(userRequestDto.getNickName()) ||
                StringUtils.hasText(userRequestDto.getEmail()) ||
                StringUtils.hasText(userRequestDto.getPhoneNumber()))) {
            throw new UserValidationException("빈 칸을 채워주세요!");
        }

        List<String> failColumn = new ArrayList<>();

        if (!idRegex.matcher(userRequestDto.getUserId()).matches()) {
            failColumn.add("아이디");
        }

        if (!passwordRegex.matcher(userRequestDto.getPassword()).matches()) {
            failColumn.add("비밀번호");
        }

        if (!nickNameRegex.matcher(userRequestDto.getNickName()).matches()) {
            failColumn.add("별명");
        }

        if (!emailRegex.matcher(userRequestDto.getEmail()).matches()) {
            failColumn.add("이메일");
        }

        if (!phoneNumberRegex.matcher(userRequestDto.getPhoneNumber()).matches()) {
            failColumn.add("휴대폰 번호");
        }

        if (!failColumn.isEmpty()) {
            throw new UserValidationException(queryFailMessage(StringUtils.joinWithCommas(failColumn)));
        }
    }

    private static String queryFailMessage(String text) {

        if (StringUtils.hasFinalConsonant(text)) {
            text = text + "을";
        } else {
            text = text + "를";
        }

        return text + " 조건에 맞게 다시 입력해 주세요 🥲";
    }
}
