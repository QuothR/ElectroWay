package com.example.electrowayfinal.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AuthenticationRequest {

    private String email;
    private String password;

    public AuthenticationRequest(String username, String password) {
        this.email = username;
        this.password = password;
    }
}