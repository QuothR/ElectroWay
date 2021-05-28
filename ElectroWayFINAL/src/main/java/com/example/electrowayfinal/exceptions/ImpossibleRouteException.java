package com.example.electrowayfinal.exceptions;

public class ImpossibleRouteException extends Exception{
    public ImpossibleRouteException() {
        super("There's no way we can get from A to B.");
    }
}
