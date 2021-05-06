package com.example.electrowayfinal.exceptions;

public class PasswordsDoNotMatch extends Exception {
    public PasswordsDoNotMatch(String exception) {
        super(exception);
        System.out.println("Passwords do not match!");
    }
}
