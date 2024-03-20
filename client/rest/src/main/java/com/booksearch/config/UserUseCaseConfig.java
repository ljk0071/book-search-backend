package com.booksearch.config;

import com.booksearch.internal.repository.UserRepository;
import com.booksearch.internal.service.UserService;
import com.booksearch.repository.user.UserJpaRepository;
import com.booksearch.repository.user.UserRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserUseCaseConfig {

    @Bean
    public UserRepository userRepository(UserJpaRepository jpaRepository) {
        return new UserRepositoryImpl(jpaRepository);
    }

    @Bean
    public UserService userService(UserRepository userRepository) {
        return new UserService(userRepository);
    }
}
