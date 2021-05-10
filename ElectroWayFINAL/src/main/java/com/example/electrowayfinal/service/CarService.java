package com.example.electrowayfinal.service;

import com.example.electrowayfinal.exceptions.WrongAccessException;
import com.example.electrowayfinal.exceptions.WrongUserInServiceException;
import com.example.electrowayfinal.models.Car;
import com.example.electrowayfinal.models.User;
import com.example.electrowayfinal.repositories.CarRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

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

    public void createCar(Car car, Long userId, HttpServletRequest httpServletRequest) {

        String bearerToken = httpServletRequest.getHeader("Authorization");
        bearerToken = bearerToken.substring(6);

        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(bearerToken).getBody();
        String username = claims.getSubject();

        Optional<User> optionalUser = userService.getOptionalUserByUsername(username);
        Optional<User> optionalUser1 = userService.getUserById(userId);

        if (optionalUser.isEmpty() || optionalUser1.isEmpty()) {
            throw new WrongUserInServiceException("Wrong user in car service!");
        }

        if (!optionalUser.get().equals(optionalUser1.get())) {
            throw new WrongAccessException("You don't own this car!");
        }

        car.setUser(optionalUser.get());

        carRepository.save(car);

    }

    public Car getCar(Long id) {
        return carRepository.getOne(id);
    }

    public List<Car> getCars(Long userId) {
        return carRepository.findAllByUserId(userId);
    }
}
