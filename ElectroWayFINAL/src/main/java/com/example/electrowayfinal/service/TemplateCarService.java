package com.example.electrowayfinal.service;

import com.example.electrowayfinal.exceptions.UserNotFoundException;
import com.example.electrowayfinal.models.TemplateCar;
import com.example.electrowayfinal.repositories.TemplateCarRepository;
import com.example.electrowayfinal.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Repository
public class TemplateCarService {
    private final TemplateCarRepository templateCarRepository;
    private final UserService userService;
    private String secret;

    @Value("${jwt.secret}")
    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Autowired
    public TemplateCarService(TemplateCarRepository templateCarRepository, UserService userService) {
        this.templateCarRepository = templateCarRepository;
        this.userService = userService;
    }

    public void createTemplateCar(TemplateCar templateCar, HttpServletRequest httpServletRequest) throws UserNotFoundException {
        JwtUtil.getUserFromToken(userService, secret, httpServletRequest);

        templateCarRepository.save(templateCar);
    }

    public TemplateCar getTemplateCar(Long id, HttpServletRequest httpServletRequest) throws UserNotFoundException {
        JwtUtil.getUserFromToken(userService, secret, httpServletRequest);

        return templateCarRepository.getOne(id);
    }

    public List<TemplateCar> getTemplateCars(HttpServletRequest httpServletRequest) throws UserNotFoundException {
        JwtUtil.getUserFromToken(userService, secret, httpServletRequest);

        return templateCarRepository.findAll();
    }

    public TemplateCar updateTemplateCar(TemplateCar templateCar, Long templateCarId, HttpServletRequest httpServletRequest) throws UserNotFoundException {
        TemplateCar templateCarToUpdate = getTemplateCar(templateCarId, httpServletRequest);

        templateCarToUpdate.setModel(templateCar.getModel());
        templateCarToUpdate.setYear(templateCar.getYear());
        templateCarToUpdate.setBatteryCapacity(templateCar.getBatteryCapacity());
        templateCarToUpdate.setChargingCapacity(templateCar.getChargingCapacity());
        templateCarToUpdate.setPlugType(templateCar.getPlugType());
        templateCarToUpdate.setVehicleMaxSpeed(templateCar.getVehicleMaxSpeed());
        templateCarToUpdate.setAuxiliaryKwh(templateCar.getAuxiliaryKwh());

        templateCarRepository.save(templateCarToUpdate);
        return templateCarToUpdate;
    }

    public void deleteTemplateCar(Long carId, HttpServletRequest httpServletRequest) throws UserNotFoundException {
        JwtUtil.getUserFromToken(userService, secret, httpServletRequest);

        templateCarRepository.deleteById(carId);
    }
}
