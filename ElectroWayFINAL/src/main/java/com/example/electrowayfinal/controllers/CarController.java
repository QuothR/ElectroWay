package com.example.electrowayfinal.controllers;

import com.example.electrowayfinal.models.Car;
import com.example.electrowayfinal.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Qualifier("car")
public class CarController {
    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping(path = "user/{id}")
    public Car createCar(@RequestBody Car car, @PathVariable("id") Long userId,  HttpServletRequest httpServletRequest) {
        carService.createCar(car, userId, httpServletRequest);
        return carService.getCar(car.getId());
    }

    @GetMapping(path = "user/{id}/cars")
    public List<Car> getUserCars(@PathVariable("id") Long userId) {
        return carService.getCars(userId);
    }
}
