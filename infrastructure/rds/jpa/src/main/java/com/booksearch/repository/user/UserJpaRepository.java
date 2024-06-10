package com.booksearch.repository.user;

import com.booksearch.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByNickName(String nickName);

    Optional<UserEntity> findByUserId(String userId);
}
