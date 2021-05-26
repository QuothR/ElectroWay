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
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "https://electrowayweb.herokuapp.com")
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
    public ChargingPlug createChargingPlug(@RequestBody ChargingPlug chargingPlug, @PathVariable("id") Long id, @PathVariable("cId") Long cId, HttpServletRequest httpServletRequest) {
        chargingPlugService.createChargingPlug(chargingPlug, id, cId, httpServletRequest);
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
    public Optional<ChargingPlug> getChargingPlug(@PathVariable("id") Long id, @PathVariable("cId") Long cId, @PathVariable("pId") Long pId, HttpServletRequest httpServletRequest) {
        return chargingPlugService.getChargingPlugById(id, cId, pId, httpServletRequest);
    }

    @PutMapping(path = "/{id}/points/{cId}/plugs/{pId}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<ChargingPlug> updateChargingPlug(@RequestBody ChargingPlug chargingPlug, @PathVariable("id") Long id, @PathVariable("cId") Long cId, @PathVariable("pId") Long pId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        return chargingPlugService.updateChargingPlug(id,cId,pId,chargingPlug,httpServletRequest, httpServletResponse);
    }

    @DeleteMapping(path = "/{id}/points/{cId}/plugs/{pId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("pId") Long pId, @PathVariable("id") Long id, @PathVariable("cId") Long cId, HttpServletRequest httpServletRequest) {
        chargingPlugService.deleteChargingPlugById(pId, id, cId, httpServletRequest);
    }

    @PostMapping("/{id}/points/{cId}/plugs/{pId}/pay")
    public String payment(@RequestBody Order order, @PathVariable("pId") Long plugId) {
        try {
            Payment payment = paypalService.createPayment(plugId,order, "https://electroway.herokuapp.com/" + CANCEL_URL,
                    "https://electroway.herokuapp.com/" + SUCCESS_URL);
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
