package com.example.electrowayfinal.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequest {
    private String intent;
    private List<PurchaseUnit> purchase_units;
}
