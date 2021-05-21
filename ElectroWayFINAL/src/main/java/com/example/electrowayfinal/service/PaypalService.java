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
    @Autowired
    private APIContext apiContext;
    @Autowired
    private UserService userService;
    @Autowired
    private ChargingPlugService chargingPlugService;

    @Autowired
    private PaypalDetailService paypalDetailService;

    public Payment createPaymentToElectroway(
            Order order,
            String cancelUrl,
            String successUrl) throws PayPalRESTException{


        Amount amount = new Amount();
        amount.setCurrency(order.getCurrency());
        double total = new BigDecimal(order.getTotal()).setScale(2, RoundingMode.HALF_UP).doubleValue();
        amount.setTotal(String.format("%.2f", total));


//        Map<String, String> configMap = new HashMap<>();
//        configMap.put("mode", "sandbox");
//        OAuthTokenCredential oAuthTokenCredential = new OAuthTokenCredential("AeHjx7i-OuKFEAgZb_L_XlVGxZS34jP5TkeMQFFzhY4KGJJf4g3D_U-INqW3U4yGI1bzThpUs_HAFKK2","EGxHuCSAe_6PQ975S2I4CnuWlO06i3t-rAy5gY4WexC_LKNEGqKyJDlsfFKPm7kFdoCXmFFQyDvSEWT6",configMap);
//
//        System.out.printf("%s\n", oAuthTokenCredential.getAccessToken());
//
//        apiContext = new APIContext(oAuthTokenCredential.getAccessToken());

        Transaction transaction = new Transaction();
        transaction.setDescription(order.getDescription());
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod(order.getMethod());

        Payment payment = new Payment();
        payment.setIntent(order.getIntent());
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);
        payment.setRedirectUrls(redirectUrls);

        System.out.println(payment);

        return payment.create(apiContext);
    }

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

        System.out.println(apiContext);
        apiContext = new APIContext(oAuthTokenCredential.getAccessToken());
        apiContext.setConfigurationMap(configMap);
        System.out.println(apiContext);

        int breakpoint=  14;

        Details details = new Details();
        details.setSubtotal("400");
        details.setTax("428");

        Amount amount = new Amount();
        amount.setDetails(details);

        amount.setCurrency(order.getCurrency());
        double total = new BigDecimal(order.getTotal() * chargingPlugService.getOnePlug(plugId).getPriceKw()).setScale(2, RoundingMode.HALF_UP).doubleValue();
        amount.setTotal(String.format("%.2f", total));

        Item item = new Item();
        item.setCurrency("EUR");
        item.setName("1h charge");
        item.setDescription("charging for one hour");
        item.setCurrency("EUR");
        item.setPrice("400");
        item.setQuantity("1");

        ItemList itemList = new ItemList();
        itemList.setItems(List.of(item));

        Transaction transaction = new Transaction();
        transaction.setDescription("ELECTROWAY \n -->"+ plugId +"<-- \n"+"Total: "+order.getTotal());
        transaction.setAmount(amount);
        transaction.setItemList(itemList);

        List<Transaction> transactions = new ArrayList<Transaction>();
        transactions.add(transaction);


//        transaction.setTransactions(transactions);

        Payer payer = new Payer();
        payer.setPaymentMethod(order.getMethod());

        Payment payment = new Payment();
        payment.setIntent(order.getIntent());
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);
        payment.setRedirectUrls(redirectUrls);
        System.out.println(payment);
        System.out.println(transactions);

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