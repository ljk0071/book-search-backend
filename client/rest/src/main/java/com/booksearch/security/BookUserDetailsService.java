package com.booksearch.security;

import com.booksearch.internal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;

@Configuration
@RequiredArgsConstructor
public class BookUserDetailsService implements UserDetailsManager {


    private final UserRepository repository;

    @Override
    public BookUserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        return new BookUserDetails(repository.findByUserId(userId));
    }

    @Override
    public void createUser(UserDetails user) {
//        UserClientMapper.toDomain(user);
//        repository.createUser(user);
    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        return false;
    }
}
