package com.example.quanthinh2205.foodyapp;

import java.io.Serializable;

public class User implements Serializable{
    private String username;
    private String password;
    private String email;
    private String birth;
    private String phone;

    public User(String username, String password, String email, String birth, String phone) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.birth = birth;
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
