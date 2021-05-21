package com.example.electrowayfinal.service;

import com.example.electrowayfinal.exceptions.UserNotFoundException;
import com.example.electrowayfinal.exceptions.WrongAccessException;
import com.example.electrowayfinal.models.Car;
import com.example.electrowayfinal.models.PlugType;
import com.example.electrowayfinal.models.PlugType;
import com.example.electrowayfinal.models.User;
import com.example.electrowayfinal.repositories.PlugTypeRepository;
import com.example.electrowayfinal.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class PlugTypeService {
    private final PlugTypeRepository plugTypeRepository;
    private final CarService carService;
    private final UserService userService;
    private String secret;

    @Value("${jwt.secret}")
    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Autowired
    public PlugTypeService(PlugTypeRepository plugTypeRepository, CarService carService, UserService userService) {
        this.plugTypeRepository = plugTypeRepository;
        this.carService = carService;
        this.userService = userService;
    }

    public void createPlugType(PlugType plugType, Long carId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws UserNotFoundException {
        User user = JwtUtil.checkUserIsDriver(userService, secret, httpServletRequest, httpServletResponse);

        Car car = carService.getCar(carId, httpServletRequest, httpServletResponse);

        if (car.getUser() != user) {
            throw new WrongAccessException("You don't own this car!");
        }

        plugType.setCar(car);

        plugTypeRepository.save(plugType);
    }

    public PlugType getPlugType(Long plugTypeId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws UserNotFoundException {
        User user = JwtUtil.checkUserIsDriver(userService, secret, httpServletRequest, httpServletResponse);

        PlugType plugType = plugTypeRepository.getOne(plugTypeId);

        if (plugType.getCar().getUser() != user) {
            throw new WrongAccessException("Can't access car's plug type if you don't own the car!");
        }

        return plugType;
    }

    public List<PlugType> getCarPlugTypes(Long carId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws UserNotFoundException {
        User user = JwtUtil.checkUserIsDriver(userService, secret, httpServletRequest, httpServletResponse);

        Car car = carService.getCar(carId, httpServletRequest, httpServletResponse);

        if (car.getUser() != user) {
            throw new WrongAccessException("Can't access car's consumptions if you don't own the car!");
        }

        return plugTypeRepository.findAllByCarId(carId);
    }

    public PlugType updatePlugType(PlugType plugType, Long plugTypeId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws UserNotFoundException {
        PlugType plugTypeToUpdate = getPlugType(plugTypeId, httpServletRequest, httpServletResponse);

        plugTypeToUpdate.setPlugType(plugType.getPlugType());

        plugTypeRepository.save(plugTypeToUpdate);
        return plugTypeToUpdate;
    }

    public void deletePlugType(Long plugTypeId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws UserNotFoundException {
        getPlugType(plugTypeId, httpServletRequest, httpServletResponse);
        plugTypeRepository.deleteById(plugTypeId);
    }
}
