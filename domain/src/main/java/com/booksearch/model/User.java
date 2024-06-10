package com.booksearch.model;

import java.util.List;

public class User {

    private String userId;

    private String password;

    private String nickName;

    private String email;

    private String phoneNumber;

    private List<Authority> authorities;


    private boolean isExpired;

    private boolean isLocked;

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getNickName() {
        return nickName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public Boolean isExpired() {
        return isExpired;
    }

    public Boolean isLocked() {
        return isLocked;
    }

    public User() {
    }

    public User(String userId, String password, String nickName, String email, String phoneNumber, List<Authority> authorities, Boolean isExpired, Boolean isLocked) {
        this.userId = userId;
        this.password = password;
        this.nickName = nickName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.authorities = authorities;
        this.isExpired = isExpired;
        this.isLocked = isLocked;
    }
}
