package com.example.electrowayfinal.exceptions;

public class CarNotFoundException extends Exception {
    public CarNotFoundException(Long id) {
        super("Car with id " + id + " not found.");
    }
}
