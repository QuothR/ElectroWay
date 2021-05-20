package com.example.electrowayfinal.models;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Payee;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseUnit {
    private MyAmount amount;
    private MyPayee payee;
}
