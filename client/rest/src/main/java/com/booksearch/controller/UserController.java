package com.booksearch.controller;

import com.booksearch.JwtTest;
import com.booksearch.dto.user.UserRequestDto;
import com.booksearch.mapper.UserClientMapper;
import com.booksearch.model.User;
import com.booksearch.usecase.UserUseCase;
import com.booksearch.validate.UserValidator;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserUseCase userUseCase;

    private final JwtTest jwtTest;

    private final PasswordEncoder passwordEncoder;

    @PostMapping
    public void createUser(@RequestBody(required = false) UserRequestDto userRequestDto) {

        userRequestDto.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));

        userUseCase.createUser(UserClientMapper.toDomain(userRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity loginUser(@RequestBody(required = false) User user,
                                    HttpServletResponse response) {

        response.setHeader("Set-Cookie", "accessToken=" + jwtTest.createAccessToken(user) + "; HttpOnly;");


        return ResponseEntity.ok().build();


//        User user = UserClientMapper.toDomain(userRequestDto, passwordEncoder);
//        User authenticationUser = userUseCase.loginUser(user);
    }
}
