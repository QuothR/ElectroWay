package com.example.electrowayfinal.controllers;

import com.example.electrowayfinal.models.Order;
import com.example.electrowayfinal.service.PaypalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaypalController {

    @Autowired
    PaypalService service;

    public static final String SUCCESS_URL = "pay/success";
    public static final String CANCEL_URL = "pay/cancel";

    @GetMapping("")
    public String home() {
        return ("<h1>home");
    }

    @GetMapping(value = CANCEL_URL)
    public String cancelPay() {
        return ("<h1>cancel");
    }

    @GetMapping(value = SUCCESS_URL)
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = service.executePayment(paymentId, payerId);
            System.out.println(payment.toJSON());
            if (payment.getState().equals("approved")) {
                return ("<h1>success");
            }
        } catch (PayPalRESTException e) {
            System.out.println(e.getMessage());
        }
        return ("<h1>redirect:/");
    }

}