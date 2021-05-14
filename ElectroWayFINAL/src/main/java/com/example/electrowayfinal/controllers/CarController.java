package com.example.electrowayfinal.controllers;

import com.example.electrowayfinal.exceptions.ForbiddenRoleAssignmentAttemptException;
import com.example.electrowayfinal.exceptions.UserNotFoundException;
import com.example.electrowayfinal.models.Car;
import com.example.electrowayfinal.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@Qualifier("car")

public class CarController {
    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping(path = "car")
    public Car createCar(@RequestBody Car car, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws RoleNotFoundException, UserNotFoundException, ForbiddenRoleAssignmentAttemptException {
        carService.createCar(car, httpServletRequest,httpServletResponse);
        return httpServletResponse.getStatus() == HttpServletResponse.SC_FORBIDDEN ? null : carService.getCar(car.getId());
    }

    @GetMapping(path = "cars")
    public List<Car> getUserCars(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws UserNotFoundException {
        return carService.getCars(httpServletRequest,httpServletResponse);
    }
}
