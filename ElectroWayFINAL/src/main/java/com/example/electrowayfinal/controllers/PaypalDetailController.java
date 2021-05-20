package com.example.electrowayfinal.controllers;

import com.example.electrowayfinal.models.PaypalDetail;
import com.example.electrowayfinal.service.PaypalDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/add")
public class PaypalDetailController {

    @Autowired
    PaypalDetailService paypalDetailService;

    /*@GetMapping(path = "{id}")
    public PaypalDetail getPaypalDetail(@PathVariable Long id){
        return paypalDetailService.getPaypalDetail(id);
    }*/

    @PostMapping
    public PaypalDetail addPaypalDetail(@RequestBody PaypalDetail paypalDetail, HttpServletRequest httpServletRequest) throws Exception {
        return paypalDetailService.addPaypalDetail(paypalDetail,httpServletRequest);
    }
    /*@DeleteMapping(path = "{id}")
    public void deletePaypalDetail(@PathVariable Long id){
        paypalDetailService.deletePaypalDetail(id);
    }*/
    @PutMapping(path = "{id}")
    public void updatePPDetail(@RequestBody PaypalDetail paypalDetail,@PathVariable Long id,HttpServletRequest httpServletRequest) throws Exception {
        paypalDetailService.updatePaypalDetail(id,paypalDetail,httpServletRequest);
    }
}
