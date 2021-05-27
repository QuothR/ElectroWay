package com.example.electrowayfinal.controllers;

import com.example.electrowayfinal.exceptions.CarNotFoundException;
import com.example.electrowayfinal.exceptions.ImpossibleRouteException;
import com.example.electrowayfinal.service.RoutingService;
import com.example.electrowayfinal.utils.Routing.structures.RoutingRequestData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping(path = "/routing")
@CrossOrigin(origins = "https://electrowayweb.herokuapp.com")
public class RoutingController {
    private final RoutingService routingService;

    @Autowired
    public RoutingController(RoutingService routingService) {
        this.routingService = routingService;
    }

    @PostMapping
    public @ResponseBody
    ResponseEntity<Object> getRoute(@Valid @RequestBody RoutingRequestData routingRequestData) throws CarNotFoundException, IOException, ImpossibleRouteException, InterruptedException {

        return routingService.generateRoute(routingRequestData);
    }

    @PostMapping(path = "points")
    public @ResponseBody
    ResponseEntity<Object> getRoutePoints(@Valid @RequestBody RoutingRequestData routingRequestData) throws CarNotFoundException, IOException, ImpossibleRouteException, InterruptedException {
        return routingService.convertToShortAnswer(
                routingService.generateRoute(routingRequestData)
        );
    }
}
