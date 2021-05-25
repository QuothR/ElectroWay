package com.example.electrowayfinal.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import com.example.electrowayfinal.models.Order;
import com.example.electrowayfinal.models.PaypalDetail;
import com.paypal.api.payments.*;
import com.paypal.base.rest.OAuthTokenCredential;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Payment createPayment(
            Long plugId,
            Order order,
            String cancelUrl,
            String successUrl) throws Exception {

        //To get station owner details
        PaypalDetail paypalDetail = paypalDetailService.getPaypalDetailByOwnerId(chargingPlugService.getOnePlug(plugId).getChargingPoint().getStation().getUser().getId());
        //To set sandbox mode
        Map<String, String> configMap = new HashMap<>();
        configMap.put("mode", "sandbox");
        //To create a token with secret and userId
        OAuthTokenCredential oAuthTokenCredential = new OAuthTokenCredential(paypalDetail.getClientId(),paypalDetail.getSecret(),configMap);
        //To set the path for paypal
        apiContext = new APIContext(oAuthTokenCredential.getAccessToken());
        apiContext.setConfigurationMap(configMap);

        double subtotal = order.getTotalKW()*chargingPlugService.getOnePlug(plugId).getPriceKw();
        double tax = 19d/100d * subtotal;

        Details details = new Details();
        details.setSubtotal(String.valueOf(subtotal));
        details.setTax(String.valueOf(tax));

        Amount amount = new Amount();
        amount.setDetails(details);

        amount.setCurrency(order.getCurrency());
        double total = BigDecimal.valueOf(subtotal + tax).setScale(2, RoundingMode.HALF_UP).doubleValue();

        amount.setTotal(String.valueOf(total));

        Item item = new Item();
        item.setCurrency("EUR");
        item.setPrice(String.valueOf(chargingPlugService.getOnePlug(plugId).getPriceKw()));
        item.setQuantity(String.valueOf((int) order.getTotalKW()));
        item.setName(" KW consumed for " + "charging at plug " + plugId);
        item.setDescription(order.getDescription() + "\n");

        ItemList itemList = new ItemList();
        itemList.setItems(List.of(item));

        Transaction transaction = new Transaction();
        transaction.setDescription("ELECTROWAY" + "\n" + "-->" + plugId + "<--" + "\n" + "Total: "+amount.getTotal());
        transaction.setAmount(amount);
        transaction.setItemList(itemList);
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

    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException{
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);
        return payment.execute(apiContext, paymentExecute);
    }

}