package com.example.electrowayfinal.exceptions;

public class UserNotFoundException extends Exception{
    public UserNotFoundException(Long id){
        super("User with id " + id + " not found");
    }
}
