package com.booksearch.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "user_simple_auth")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSimpleAuthEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "di")
    private String di;

    @Column(name = "kakao_info")
    private String kakaoInfo;

    @Column(name = "google_info")
    private String googleInfo;

    @Column(name = "naver_info")
    private String naverInfo;

    @Column(name = "apple_info")
    private String appleInfo;
}
