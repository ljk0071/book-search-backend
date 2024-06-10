package com.booksearch.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "user_auth")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthorityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @PrimaryKeyJoinColumn
    private UserEntity user;

    public AuthorityEntity(String name, UserEntity user) {
        this.name = name;
        this.user = user;
    }
}
