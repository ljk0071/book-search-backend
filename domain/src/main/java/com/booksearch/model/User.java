package com.booksearch.model;

public class User {

    private String userId;

    private String password;

    private String nickName;

    private String email;

    private String phoneNumber;

    private String type;

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

    public String getType() {
        return type;
    }

    public User() {
    }

    public User(String userId, String password, String nickName, String email, String phoneNumber, String type) {
        this.userId = userId;
        this.password = password;
        this.nickName = nickName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.type = type;
    }
}
