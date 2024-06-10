package com.booksearch.aspect.validate;

import com.booksearch.dto.user.UserRequestDto;
import com.booksearch.model.User;
import com.booksearch.validate.UserValidator;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class UserValidation {

    @Before("execution(* com.booksearch.controller.UserController.createUser(..))")
    public void validateUserRequestDto(JoinPoint joinPoint) {
        UserValidator.checkValidation((UserRequestDto) joinPoint.getArgs()[0]);
    }

    @Before("execution(* com.booksearch.repository.user.UserRepositoryImpl.createUser(..))")
    public void validateUser(JoinPoint joinPoint) {
        UserValidator.checkValidation((User) joinPoint.getArgs()[0]);
    }
}
