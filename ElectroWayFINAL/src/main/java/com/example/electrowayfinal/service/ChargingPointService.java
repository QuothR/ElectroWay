package com.example.electrowayfinal.service;

import com.example.electrowayfinal.exceptions.WrongAccessException;
import com.example.electrowayfinal.exceptions.WrongUserInServiceException;
import com.example.electrowayfinal.models.ChargingPoint;
import com.example.electrowayfinal.models.Station;
import com.example.electrowayfinal.models.User;
import com.example.electrowayfinal.repositories.ChargingPlugRepository;
import com.example.electrowayfinal.repositories.ChargingPointRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
public class ChargingPointService {
    private final ChargingPointRepository chargingPointRepository;
    private final ChargingPlugRepository chargingPlugRepository;
    private final StationService stationService;
    private final UserService userService;
    private String secret;

    @Value("${jwt.secret}")
    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Autowired
    public ChargingPointService(ChargingPointRepository chargingPointRepository, ChargingPlugRepository chargingPlugRepository, StationService stationService, UserService userService) {
        this.chargingPointRepository = chargingPointRepository;
        this.chargingPlugRepository = chargingPlugRepository;
        this.stationService = stationService;
        this.userService = userService;
    }

    public void createChargingPoint(ChargingPoint chargingPoint, Long id, HttpServletRequest httpServletRequest) {
        Station station = stationService.getStation(id);
        String bearerToken = httpServletRequest.getHeader("Authorization");
        bearerToken = bearerToken.substring(6);

        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(bearerToken).getBody();
        String username = claims.getSubject();

        Optional<User> optionalUser = userService.getOptionalUserByUsername(username);

        if (optionalUser.isEmpty()) {
            WrongUserInServiceException exception = new WrongUserInServiceException("Wrong user in station service!");
            log.error(exception.getMessage());
            throw exception;
        }
        if (!station.getUser().equals(optionalUser.get())) {
            WrongAccessException exception = new WrongAccessException("You don't own this station!");
            log.error(exception.getMessage());
            throw exception;
        }

        chargingPoint.setStation(station);
        chargingPointRepository.save(chargingPoint);
    }

    public void deleteChargingPoint(ChargingPoint chargingPoint) {
        chargingPointRepository.delete(chargingPoint);
    }

    public void deleteChargingPointById(Long cId, Long id) {
        Optional<ChargingPoint> chargingPoint = chargingPointRepository.getChargingPointById(cId);

        if (chargingPoint.isEmpty()) {
            NoSuchElementException exception = new NoSuchElementException("No charging point found!");
            log.error(exception.getMessage());
            throw exception;
        }
        if (chargingPoint.get().getStation().getId() != id) {
            WrongAccessException exception = new WrongAccessException("You don't own this station!");
            log.error(exception.getMessage());
            throw exception;
        }
        chargingPointRepository.deleteById(cId);
    }

    public Optional<ChargingPoint> findChargingPointById(Long id, Long cId, HttpServletRequest httpServletRequest) {
        Optional<ChargingPoint> chargingPoint = chargingPointRepository.getChargingPointById(id);

        if (chargingPoint.isEmpty()) {
            NoSuchElementException exception = new NoSuchElementException("No charging point found!");
            log.error(exception.getMessage());
            throw exception;
        }
        if (chargingPoint.get().getStation().getId() != cId) {
            WrongAccessException exception = new WrongAccessException("You don't own this station!");
            log.error(exception.getMessage());
            throw exception;
        }
        Station station = stationService.getStation(chargingPoint.get().getStation().getId());
        String bearerToken = httpServletRequest.getHeader("Authorization");
        bearerToken = bearerToken.substring(6);

        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(bearerToken).getBody();
        String username = claims.getSubject();

        Optional<User> optionalUser = userService.getOptionalUserByUsername(username);

        if (optionalUser.isEmpty()) {
            NoSuchElementException exception = new NoSuchElementException("Empty user in charging point search!");
            log.error(exception.getMessage());
            throw exception;
        }
        if (!station.getUser().equals(optionalUser.get())) {
            WrongAccessException exception = new WrongAccessException("You don't own this station!");
            log.error(exception.getMessage());
            throw exception;
        }

        return chargingPoint;
    }

    public List<ChargingPoint> getAllChargingPointsByStationId(Long stationId, HttpServletRequest httpServletRequest) {

        Station station = stationService.getStation(stationId);
        String bearerToken = httpServletRequest.getHeader("Authorization");
        bearerToken = bearerToken.substring(6);

        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(bearerToken).getBody();
        String username = claims.getSubject();

        Optional<User> optionalUser = userService.getOptionalUserByUsername(username);

        if (optionalUser.isEmpty()) {
            WrongUserInServiceException exception = new WrongUserInServiceException("Wrong user in station service!");
            log.error(exception.getMessage());
            throw exception;
        }
        if (!station.getUser().equals(optionalUser.get())) {
            WrongAccessException exception = new WrongAccessException("You don't own this station!");
            log.error(exception.getMessage());
            throw exception;
        }

        return chargingPointRepository.findChargingPointsByStation_Id(stationId);
    }

}
