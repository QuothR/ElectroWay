package com.example.electrowayfinal.service;

import com.example.electrowayfinal.exceptions.ForbiddenRoleAssignmentAttemptException;
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
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
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

    public void createCar(Car car, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws RoleNotFoundException, UserNotFoundException, ForbiddenRoleAssignmentAttemptException {

        String bearerToken = httpServletRequest.getHeader("Authorization");
        bearerToken = bearerToken.substring(6);

        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(bearerToken).getBody();
        String username = claims.getSubject();


        Optional<User> optionalUser = userService.getOptionalUserByUsername(username);

        if (optionalUser.isEmpty())
            throw new UserNotFoundException(username);

        if (!optionalUser.get().getRoles().stream().map(Role::getName).collect(Collectors.toList()).contains("ROLE_DRIVER")){
            httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }


        if (optionalUser.get().getRoles().stream().map(Role::getName).noneMatch(s -> s.equals("ROLE_DRIVER"))){
            userService.addRole(optionalUser.get(),"ROLE_DRIVER");
        }

        car.setUser(optionalUser.get());
        carRepository.save(car);

    }

    public Car getCar(Long id) {
        return carRepository.getOne(id);
    }

    public List<Car> getCars(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws UserNotFoundException {
        String bearerToken = httpServletRequest.getHeader("Authorization");
        bearerToken = bearerToken.substring(6);

        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(bearerToken).getBody();
        String username = claims.getSubject();

        Optional<User> optionalUser = userService.getOptionalUserByUsername(username);

        if (optionalUser.isEmpty())
            throw new UserNotFoundException(username);

        if (!optionalUser.get().getRoles().stream().map(Role::getName).collect(Collectors.toList()).contains("ROLE_DRIVER")){
            httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return null;
        }

        return carRepository.findAllByUserId(optionalUser.get().getId());
    }
}
