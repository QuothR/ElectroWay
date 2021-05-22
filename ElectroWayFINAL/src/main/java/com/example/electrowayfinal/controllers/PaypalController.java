package com.example.electrowayfinal.controllers;

import com.example.electrowayfinal.exceptions.UserNotFoundException;
import com.example.electrowayfinal.models.Order;
import com.example.electrowayfinal.models.Receipt;
import com.example.electrowayfinal.service.ChargingPlugService;
import com.example.electrowayfinal.service.PaypalService;
import com.example.electrowayfinal.service.UserService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/payment")
public class PaypalController {

    @Autowired
    PaypalService service;

    @Autowired
    UserService userService;

    @Autowired
    ChargingPlugService chargingPlugService;

    public static final String SUCCESS_URL = "success";
    public static final String CANCEL_URL = "cancel";

    @GetMapping("")
    public String home() {
        return ("<h1>home");
    }

    @GetMapping(value = CANCEL_URL)
    public String cancelPay() {
        return ("<h1>cancel");
    }

    @GetMapping(value = SUCCESS_URL)
    public Receipt successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        try {
            Payment payment = service.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                Receipt receipt = new Receipt();
                receipt.setDate(payment.getCreateTime());
                String[] split = payment.getTransactions().get(0).getItemList().getItems().get(0).getName().split(" ");
                Long plugId = Long.valueOf(split[7]);
                System.out.println("asas" + plugId);
                receipt.setDestinatar(chargingPlugService.getUserFromPlug(plugId).getFirstName() + " " + chargingPlugService.getUserFromPlug(plugId).getLastName());
                receipt.setExpeditor(userService.getCurrentUser(httpServletRequest).getFirstName() + " " + userService.getCurrentUser(httpServletRequest).getLastName());
                receipt.setStatus(payment.getPayer().getStatus());
                receipt.setId(payment.getId());
                receipt.setTotal(payment.getTransactions().get(0).getAmount().getTotal());
                return receipt;
            }
        } catch (PayPalRESTException e) {
            System.out.println(e.getMessage());
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}