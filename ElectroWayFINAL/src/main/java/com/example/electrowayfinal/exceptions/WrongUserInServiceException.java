package com.example.electrowayfinal.exceptions;

public class WrongUserInServiceException extends RuntimeException {
    public WrongUserInServiceException(String exception) {
        super(exception);
    }
}
