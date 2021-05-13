package com.example.electrowayfinal.service;

import com.example.electrowayfinal.exceptions.UserNotFoundException;
import com.example.electrowayfinal.exceptions.WrongAccessException;
import com.example.electrowayfinal.exceptions.WrongUserInServiceException;
import com.example.electrowayfinal.models.Car;
import com.example.electrowayfinal.models.Role;
import com.example.electrowayfinal.models.User;
import com.example.electrowayfinal.repositories.CarRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
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

    public void createCar(Car car, HttpServletRequest httpServletRequest) throws RoleNotFoundException, UserNotFoundException {

        String bearerToken = httpServletRequest.getHeader("Authorization");
        bearerToken = bearerToken.substring(6);

        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(bearerToken).getBody();
        String username = claims.getSubject();

        Optional<User> optionalUser = userService.getOptionalUserByUsername(username);
        if (optionalUser.isEmpty())
            throw new UserNotFoundException(username);


        if (optionalUser.get().getRoles().stream().map(Role::getName).noneMatch(s -> s.equals("ROLE_DRIVER"))){
            userService.addRole(optionalUser.get(),"ROLE_DRIVER");
        }

        car.setUser(optionalUser.get());
        carRepository.save(car);

    }

    public Car getCar(Long id) {
        return carRepository.getOne(id);
    }

    public List<Car> getCars(HttpServletRequest httpServletRequest) throws UserNotFoundException {
        String bearerToken = httpServletRequest.getHeader("Authorization");
        bearerToken = bearerToken.substring(6);

        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(bearerToken).getBody();
        String username = claims.getSubject();

        Optional<User> user = userService.getOptionalUserByUsername(username);

        if (user.isEmpty())
            throw new UserNotFoundException(username);

        return carRepository.findAllByUserId(user.get().getId());
    }
}
