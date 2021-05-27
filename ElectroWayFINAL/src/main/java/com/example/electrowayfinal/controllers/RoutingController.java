package com.example.electrowayfinal.controllers;

import com.example.electrowayfinal.exceptions.CarNotFoundException;
import com.example.electrowayfinal.exceptions.CurrentChargeInkWhException;
import com.example.electrowayfinal.exceptions.ImpossibleRouteException;
import com.example.electrowayfinal.exceptions.InvalidData;
import com.example.electrowayfinal.utils.Routing.structures.RoutingRequestData;
import com.example.electrowayfinal.service.RoutingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping(path = "/routing")
public class RoutingController {
    private final RoutingService routingService;

    @Autowired
    public RoutingController(RoutingService routingService) {
        this.routingService = routingService;
    }

    @PostMapping
    public @ResponseBody
    ResponseEntity<Object> getRoute(@Valid @RequestBody RoutingRequestData routingRequestData) throws CarNotFoundException, ImpossibleRouteException, InvalidData, CurrentChargeInkWhException {

        try {
            return routingService.generateRoute(routingRequestData);
        } catch (IOException e) {
            throw new InvalidData();
        } catch (InterruptedException e) {
            throw new InvalidData();
        }
    }

    @PostMapping(path = "points")
    public @ResponseBody
    ResponseEntity<Object> getRoutePoints(@Valid @RequestBody RoutingRequestData routingRequestData) throws CarNotFoundException, ImpossibleRouteException, InvalidData, CurrentChargeInkWhException {
        try {
            return routingService.convertToShortAnswer(
                    routingService.generateRoute(routingRequestData)
            );
        } catch (IOException e) {
            throw new InvalidData();
        } catch (InterruptedException e) {
            throw new InvalidData();
        }
    }
}
