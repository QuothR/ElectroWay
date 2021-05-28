package com.example.electrowayfinal.exceptions;

public class CurrentChargeInkWhException extends Exception{
    public CurrentChargeInkWhException(Double currentCharge, Double batteryCapacity) {
        super(currentCharge + " is not less than " + batteryCapacity + ". It should be.");
    }
}
