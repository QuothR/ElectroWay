package com.example.electrowayfinal.controllers;

import com.example.electrowayfinal.models.PaypalDetail;
import com.example.electrowayfinal.service.PaypalDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user/wallet")
@CrossOrigin(origins = "http://localhost:3000")
public class PaypalDetailController {

    @Autowired
    PaypalDetailService paypalDetailService;

    @PostMapping
    public PaypalDetail addPaypalDetail(@RequestBody PaypalDetail paypalDetail, HttpServletRequest httpServletRequest) throws Exception {
        return paypalDetailService.addPaypalDetail(paypalDetail,httpServletRequest);
    }

    @PutMapping
    public void updatePPDetail(@RequestBody PaypalDetail paypalDetail,HttpServletRequest httpServletRequest) throws Exception {
        paypalDetailService.updatePaypalDetail(paypalDetail,httpServletRequest);
    }
}
