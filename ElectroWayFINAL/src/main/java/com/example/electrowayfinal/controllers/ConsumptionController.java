package com.example.electrowayfinal.controllers;

import com.example.electrowayfinal.exceptions.UserNotFoundException;
import com.example.electrowayfinal.models.Consumption;
import com.example.electrowayfinal.service.ConsumptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/consumption")
@CrossOrigin(origins = "https://electrowayweb.herokuapp.com")
@Qualifier("consumption")
public class ConsumptionController {
    private final ConsumptionService consumptionService;

    @Autowired
    public ConsumptionController(ConsumptionService consumptionService) {
        this.consumptionService = consumptionService;
    }

    @PostMapping(path = "create/{carId}")
    @ResponseStatus(HttpStatus.OK)
    public Consumption createConsumption(@RequestBody Consumption consumption, @PathVariable("carId") Long carId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws UserNotFoundException {
        consumptionService.createConsumption(consumption, carId, httpServletRequest, httpServletResponse);
        return consumptionService.getConsumption(consumption.getId(), httpServletRequest, httpServletResponse);
    }

    @GetMapping(path = "get/{consumptionId}")
    @ResponseStatus(HttpStatus.OK)
    public Consumption getConsumption(@PathVariable("consumptionId") Long consumptionId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws UserNotFoundException {
        return consumptionService.getConsumption(consumptionId, httpServletRequest, httpServletResponse);
    }

    @GetMapping(path = "all/{carId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Consumption> getCarConsumptions(@PathVariable("carId") Long carId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws UserNotFoundException {
        return consumptionService.getCarConsumptions(carId, httpServletRequest, httpServletResponse);
    }

    @RequestMapping(value = "update/{consumptionId}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public Consumption updateConsumption(@RequestBody Consumption newConsumption, @PathVariable("consumptionId") Long consumptionId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws UserNotFoundException {
        return consumptionService.updateConsumption(newConsumption, consumptionId, httpServletRequest, httpServletResponse);
    }

    @DeleteMapping(path = "delete/{consumptionId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteConsumption(@PathVariable("consumptionId") Long consumptionId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws UserNotFoundException {
        consumptionService.deleteConsumption(consumptionId, httpServletRequest, httpServletResponse);
    }
}
