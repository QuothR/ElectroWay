package com.example.electrowayfinal.controllers;

import com.example.electrowayfinal.exceptions.ForbiddenRoleAssignmentAttemptException;
import com.example.electrowayfinal.exceptions.UserNotFoundException;
import com.example.electrowayfinal.models.Car;
import com.example.electrowayfinal.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/car")
@CrossOrigin(origins = "http://localhost:3000")
@Qualifier("car")
public class CarController {
    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping(path = "create")
    @ResponseStatus(HttpStatus.OK)
    public Car createCar(@RequestBody Car car, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws RoleNotFoundException, UserNotFoundException, ForbiddenRoleAssignmentAttemptException {
        carService.createCar(car, httpServletRequest, httpServletResponse);
        return carService.getCar(car.getId(), httpServletRequest, httpServletResponse);
    }

    @GetMapping(path = "all")
    @ResponseStatus(HttpStatus.OK)
    public List<Car> getUserCars(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws UserNotFoundException {
        return carService.getCars(httpServletRequest, httpServletResponse);
    }

    @RequestMapping(value = "update/{carId}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public Car updateCar(@RequestBody Car newCar, @PathVariable("carId") Long carId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws UserNotFoundException {
        return carService.updateCar(newCar, carId, httpServletRequest, httpServletResponse);
    }

    @DeleteMapping(path = "delete/{carId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCar(@PathVariable("carId") Long carId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws UserNotFoundException {
        carService.deleteCar(carId, httpServletRequest, httpServletResponse);
    }
}
