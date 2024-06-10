package com.booksearch.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "password")
    private String password;

    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "is_expired", length = 1)
    private String isExpired;

    @Column(name = "is_locked", length = 1)
    private String isLocked;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "user")
    private List<AuthorityEntity> authorities = new ArrayList<>();

    @Builder
    public UserEntity(String userId,
                      String password,
                      String nickName,
                      String email,
                      String phoneNumber,
                      String accessToken,
                      String isExpired,
                      String isLocked,
                      AuthorityEntity... authorityEntities) {
        this.userId = userId;
        this.password = password;
        this.nickName = nickName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.accessToken = accessToken;
        this.isExpired = isExpired;
        this.isLocked = isLocked;
        if (authorityEntities != null) {
            this.authorities.addAll(List.of(authorityEntities));
        }
    }

    public void setAuthorities(AuthorityEntity... authorities) {
        this.authorities = List.of(authorities);
    }
}
