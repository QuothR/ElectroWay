package com.example.electrowayfinal.service;

import com.example.electrowayfinal.exceptions.UserNotFoundException;
import com.example.electrowayfinal.exceptions.WrongAccessException;
import com.example.electrowayfinal.models.Car;
import com.example.electrowayfinal.models.Consumption;
import com.example.electrowayfinal.models.User;
import com.example.electrowayfinal.repositories.ConsumptionRepository;
import com.example.electrowayfinal.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class ConsumptionService {
    private final ConsumptionRepository consumptionRepository;
    private final UserService userService;
    private final CarService carService;
    private String secret;

    @Value("${jwt.secret}")
    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Autowired
    public ConsumptionService(ConsumptionRepository consumptionRepository, UserService userService, CarService carService) {
        this.consumptionRepository = consumptionRepository;
        this.userService = userService;
        this.carService = carService;
    }

    public void createConsumption(Consumption consumption, Long carId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws UserNotFoundException {
        User user = JwtUtil.checkUserIsDriver(userService, secret, httpServletRequest, httpServletResponse);

        Car car = carService.getCar(carId, httpServletRequest, httpServletResponse);

        if (!car.getUser().getEmailAddress().equals(user.getEmailAddress())) {
            throw new WrongAccessException("You don't own this car!");
        }

        consumption.setCar(car);

        consumptionRepository.save(consumption);
    }

    public Consumption getConsumption(Long consumptionId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws UserNotFoundException {
        User user = JwtUtil.checkUserIsDriver(userService, secret, httpServletRequest, httpServletResponse);

        Consumption consumption = consumptionRepository.getOne(consumptionId);

        if (!consumption.getCar().getUser().getEmailAddress().equals(user.getEmailAddress())) {
            throw new WrongAccessException("Can't access car's consumption if you don't own the car!");
        }

        return consumption;
    }

    public List<Consumption> getCarConsumptions(Long carId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws UserNotFoundException {
        User user = JwtUtil.checkUserIsDriver(userService, secret, httpServletRequest, httpServletResponse);

        Car car = carService.getCar(carId, httpServletRequest, httpServletResponse);

        if (!car.getUser().getEmailAddress().equals(user.getEmailAddress())) {
            throw new WrongAccessException("Can't access car's consumptions if you don't own the car!");
        }

        return consumptionRepository.findAllByCarId(carId);
    }

    public Consumption updateConsumption(Consumption consumption, Long consumptionId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws UserNotFoundException {
        Consumption consumptionToUpdate = getConsumption(consumptionId, httpServletRequest, httpServletResponse);

        consumptionToUpdate.setSpeed(consumption.getSpeed());
        consumptionToUpdate.setConsumptionKwh(consumption.getConsumptionKwh());

        consumptionRepository.save(consumptionToUpdate);
        return consumptionToUpdate;
    }

    public void deleteConsumption(Long consumptionId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws UserNotFoundException {
        getConsumption(consumptionId, httpServletRequest, httpServletResponse);
        consumptionRepository.deleteById(consumptionId);
    }
}
