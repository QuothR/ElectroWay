package com.example.electrowayfinal.service;

import com.example.electrowayfinal.exceptions.UserNotFoundException;
import com.example.electrowayfinal.exceptions.WrongAccessException;
import com.example.electrowayfinal.exceptions.WrongPrivilegesException;
import com.example.electrowayfinal.models.Car;
import com.example.electrowayfinal.models.Role;
import com.example.electrowayfinal.models.User;
import com.example.electrowayfinal.repositories.CarRepository;
import com.example.electrowayfinal.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarService {
    private final CarRepository carRepository;
    private final UserService userService;
    private String secret;

    @Value("${jwt.secret}")
    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Autowired
    public CarService(CarRepository carRepository, UserService userService) {
        this.carRepository = carRepository;
        this.userService = userService;
    }

    public void createCar(Car car, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws UserNotFoundException {
        User user = JwtUtil.getUserFromToken(userService, secret, httpServletRequest);

        if (!user.getRoles().stream().map(Role::getName).collect(Collectors.toList()).contains("ROLE_DRIVER")) {
            httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            throw new WrongPrivilegesException("Can't access car without being a car owner!");
        }

        car.setUser(user);
        carRepository.save(car);
    }

    public Car getCar(Long carId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws UserNotFoundException {
        User user = JwtUtil.getUserFromToken(userService, secret, httpServletRequest);

        if (!user.getRoles().stream().map(Role::getName).collect(Collectors.toList()).contains("ROLE_DRIVER")) {
            httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            throw new WrongPrivilegesException("Can't access car without being a car owner!");
        }

        Car car = carRepository.getOne(carId);

        if (car.getUser() != user) {
            throw new WrongAccessException("You don't own this car!");
        }
        return car;
    }

    public List<Car> getCars(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws UserNotFoundException {
        User user = JwtUtil.getUserFromToken(userService, secret, httpServletRequest);

        if (!user.getRoles().stream().map(Role::getName).collect(Collectors.toList()).contains("ROLE_DRIVER")) {
            httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            throw new WrongPrivilegesException("Can't access car without being a car owner!");
        }

        return carRepository.findAllByUserId(user.getId());
    }

    public Car updateCar(Car car, Long carId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws UserNotFoundException {
        Car carToUpdate = getCar(carId, httpServletRequest, httpServletResponse);

        carToUpdate.setModel(car.getModel());
        carToUpdate.setYear(car.getYear());
        carToUpdate.setBatteryCapacity(car.getBatteryCapacity());
        carToUpdate.setChargingCapacity(car.getChargingCapacity());
        carToUpdate.setPlugType(car.getPlugType());
        carToUpdate.setVehicleMaxSpeed(car.getVehicleMaxSpeed());
        carToUpdate.setAuxiliaryKwh(car.getAuxiliaryKwh());

        carRepository.save(carToUpdate);
        return carToUpdate;
    }

    public void deleteCar(Long carId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws UserNotFoundException {
        getCar(carId, httpServletRequest, httpServletResponse);
        carRepository.deleteById(carId);
    }
}
