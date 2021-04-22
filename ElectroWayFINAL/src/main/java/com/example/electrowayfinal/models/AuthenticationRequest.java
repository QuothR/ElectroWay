package com.example.electrowayfinal.models;

public class AuthenticationRequest {

    private String email;
    private String password;

    public AuthenticationRequest(String username, String password) {
        this.email = username;
        this.password = password;
    }

    public AuthenticationRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}