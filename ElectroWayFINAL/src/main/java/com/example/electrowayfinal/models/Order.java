package com.example.electrowayfinal.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Order {

    private double total;
    private final String currency = "EUR";
    private final String method = "paypal";
    private final String intent = "sale";
    private String description;

}