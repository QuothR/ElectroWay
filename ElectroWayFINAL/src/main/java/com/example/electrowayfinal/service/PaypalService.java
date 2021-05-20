package com.example.electrowayfinal.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import com.example.electrowayfinal.exceptions.UserNotFoundException;
import com.example.electrowayfinal.models.Order;
import com.example.electrowayfinal.models.PaypalDetail;
import com.example.electrowayfinal.models.User;
import com.paypal.api.payments.*;
import com.paypal.base.rest.OAuthTokenCredential;
import jdk.jfr.Description;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

@Service
public class PaypalService {
    //@Autowired
    private APIContext apiContext;
    @Autowired
    private UserService userService;
    @Autowired
    private ChargingPlugService chargingPlugService;

    @Autowired
    private PaypalDetailService paypalDetailService;


    public Payment createPayment(
            Long plugId,
            Order order,
            String cancelUrl,
            String successUrl) throws Exception {


        PaypalDetail paypalDetail = paypalDetailService.getPaypalDetailByOwnerId(chargingPlugService.getOnePlug(plugId).getChargingPoint().getStation().getUser().getId());
        Map<String, String> configMap = new HashMap<>();
        configMap.put("mode", "sandbox");
        OAuthTokenCredential oAuthTokenCredential = new OAuthTokenCredential(paypalDetail.getClientId(),paypalDetail.getSecret(),configMap);

        System.out.printf("%s\n", oAuthTokenCredential.getAccessToken());

        apiContext = new APIContext(oAuthTokenCredential.getAccessToken());

        Details details = new Details();
        details.setSubtotal("10");
        details.setTax("69");

        Amount amount = new Amount();
        amount.setDetails(details);

        amount.setCurrency(order.getCurrency());
        double total = new BigDecimal(order.getTotal() * chargingPlugService.getOnePlug(plugId).getPriceKw()).setScale(2, RoundingMode.HALF_UP).doubleValue();
        amount.setTotal(String.format("%.2f", total));

        Transaction transaction = new Transaction();
        transaction.setDescription("ELECTROWAY \n -->"+ plugId +"<-- \n"+"Total: "+order.getTotal());
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<Transaction>();
        transactions.add(transaction);


        transaction.setTransactions(transactions);

        Payer payer = new Payer();
        payer.setPaymentMethod(order.getMethod());

        Payment payment = new Payment();
        payment.setIntent(order.getIntent());
        payment.setPayer(payer);
        payment.setTransactions(transaction.getTransactions());

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);
        payment.setRedirectUrls(redirectUrls);
        System.out.println(transaction.getTransactions());

        return payment.create(apiContext);
    }

    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException{
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);
        return payment.execute(apiContext, paymentExecute);
    }

}