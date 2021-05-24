package com.example.electrowayfinal.controllers;

import com.example.electrowayfinal.models.ChargingPlug;
import com.example.electrowayfinal.models.ChargingPoint;
import com.example.electrowayfinal.models.Order;
import com.example.electrowayfinal.service.ChargingPlugService;
import com.example.electrowayfinal.service.ChargingPointService;
import com.example.electrowayfinal.service.PaypalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/station")
public class ChargingPlugController {
    private final ChargingPlugService chargingPlugService;
    private final ChargingPointService chargingPointService;

    @Autowired
    PaypalService paypalService;

    public static final String SUCCESS_URL = "payment/success";
    public static final String CANCEL_URL = "payment/cancel";

    @Autowired
    public ChargingPlugController(ChargingPlugService chargingPlugService, ChargingPointService chargingPointService) {
        this.chargingPlugService = chargingPlugService;
        this.chargingPointService = chargingPointService;
    }

    @PostMapping(value = "{id}/points/{cId}")
    @ResponseStatus(HttpStatus.OK)
    public ChargingPlug createChargingPlug(@RequestBody ChargingPlug chargingPlug, @PathVariable("cId") Long cId, @PathVariable("id") Long id, HttpServletRequest httpServletRequest) {
        chargingPlugService.createChargingPlug(chargingPlug, cId, id, httpServletRequest);
        return chargingPlug;
    }

    @GetMapping(value = "{id}/points/{cId}/plugs")
    public List<ChargingPlug> getAllPlugsFromStation(@PathVariable("id") Long id, @PathVariable("cId") Long cId, HttpServletRequest httpServletRequest) {
        Optional<ChargingPoint> chargingPoint = chargingPointService.findChargingPointById(id, cId, httpServletRequest);
        if (chargingPoint.isEmpty()) {
            throw new NoSuchElementException("Charging plug does not exist!");
        }
        return chargingPlugService.getChargingPlugsByChargingPoint(chargingPoint.get(), id);
    }

    @GetMapping(path = "/{id}/points/{cId}/plugs/{pId}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<ChargingPlug> getChargingPlug(@PathVariable("pId") Long pId, @PathVariable("id") Long id, @PathVariable("cId") Long cId, HttpServletRequest httpServletRequest) {
        return chargingPlugService.getChargingPlugById(pId, id, cId, httpServletRequest);
    }

    @PutMapping(path = "/{id}/points/{cId}/plugs/{pId}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<ChargingPlug> updateChargingPlug(@RequestBody ChargingPlug chargingPlug, @PathVariable("pId") Long pId, @PathVariable("id") Long id, @PathVariable("cId") Long cId, HttpServletRequest httpServletRequest) throws Exception {
        return chargingPlugService.updateChargingPlug(pId,id,cId,chargingPlug,httpServletRequest);
    }

    @DeleteMapping(path = "/{id}/points/{cId}/plugs/{pId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("pId") Long pId, @PathVariable("id") Long id, @PathVariable("cId") Long cId, HttpServletRequest httpServletRequest) {
        chargingPlugService.deleteChargingPlugById(pId, id, cId, httpServletRequest);
    }

    @PostMapping("/{id}/points/{cId}/plugs/{pId}/pay")
    public String payment(@RequestBody Order order, @PathVariable("pId") Long plugId) {
        try {
            Payment payment = paypalService.createPayment(plugId,order, "https://localhost:443/" + CANCEL_URL,
                    "https://localhost:443/" + SUCCESS_URL);
            for (Links link : payment.getLinks()) {
                if (link.getRel().equals("approval_url")) {
                    return ("<h1>redirect:" + link.getHref());
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
        return ("<h1>redirect:/");
    }

}
