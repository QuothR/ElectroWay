package com.example.electrowayfinal.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AuthenticationResponse {

    private String token;

    public AuthenticationResponse(String token) {
        this.token = token;
    }

}