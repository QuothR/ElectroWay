package com.example.electrowayfinal.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Receipt {
    private String date;
    private String destinatar;
    private String expeditor;
    private String status;
    private String total;
    private String id;
}
