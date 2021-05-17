package com.example.electrowayfinal.service;

import com.example.electrowayfinal.exceptions.WrongAccessException;
import com.example.electrowayfinal.exceptions.WrongUserInServiceException;
import com.example.electrowayfinal.models.ChargingPlug;
import com.example.electrowayfinal.models.ChargingPoint;
import com.example.electrowayfinal.models.Station;
import com.example.electrowayfinal.models.User;
import com.example.electrowayfinal.repositories.ChargingPlugRepository;
import com.example.electrowayfinal.repositories.ChargingPointRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
    @Lazy
    @Autowired
    public ChargingPointService(ChargingPointRepository chargingPointRepository, ChargingPlugRepository chargingPlugRepository, StationService stationService, UserService userService) {
        this.chargingPointRepository = chargingPointRepository;
        this.chargingPlugRepository = chargingPlugRepository;
        this.stationService = stationService;
        this.userService = userService;
    }

    public ChargingPoint createChargingPoint(Long id, HttpServletRequest httpServletRequest) {
        Station station = stationService.getStation(id);
        String bearerToken = httpServletRequest.getHeader("Authorization");
        bearerToken = bearerToken.substring(6);

        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(bearerToken).getBody();
        String username = claims.getSubject();

        Optional<User> optionalUser = userService.getOptionalUserByUsername(username);

        if (optionalUser.isEmpty()) {
            throw new WrongUserInServiceException("Wrong user in station service!");
        }
        if (!station.getUser().equals(optionalUser.get())) {
            throw new WrongAccessException("You don't own this station!");
        }
        ChargingPoint chargingPoint = new ChargingPoint();
        chargingPoint.setStation(station);
        chargingPointRepository.save(chargingPoint);
        return chargingPoint;
    }

    public Optional<ChargingPoint> findChargingPointById(Long id, Long cId, HttpServletRequest httpServletRequest) {
        Optional<ChargingPoint> chargingPoint = chargingPointRepository.getChargingPointById(id);

        if (chargingPoint.isEmpty()) {
            throw new NoSuchElementException("No charging point found!");
        }
        if (chargingPoint.get().getStation().getId() != cId) {
            throw new WrongAccessException("You don't own this station!");
        }
        Station station = stationService.getStation(chargingPoint.get().getStation().getId());
        String bearerToken = httpServletRequest.getHeader("Authorization");
        bearerToken = bearerToken.substring(6);

        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(bearerToken).getBody();
        String username = claims.getSubject();

        Optional<User> optionalUser = userService.getOptionalUserByUsername(username);

        if (optionalUser.isEmpty()) {
            throw new NoSuchElementException("Empty user in charging point search!");
        }
        if (!station.getUser().equals(optionalUser.get())) {
            throw new WrongAccessException("You don't own this station!");
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
            throw new WrongUserInServiceException("Wrong user in station service!");
        }
        if (!station.getUser().equals(optionalUser.get())) {
            throw new WrongAccessException("You don't own this station!");
        }

        return chargingPointRepository.findChargingPointsByStation_Id(stationId);
    }
    public List<ChargingPoint> getChargingPointsByStationId(long id){
        return chargingPointRepository.getChargingPointsByStation_Id(id);
    }
    public void deleteChargingPoint(ChargingPoint chargingPoint) {
        List<ChargingPlug> chargingPlugs = chargingPlugRepository.findChargingPlugsByChargingPointId(chargingPoint.getId());
        if(chargingPlugs.isEmpty()){
            chargingPointRepository.deleteById(chargingPoint.getId());
        }else{
            for(ChargingPlug plug:chargingPlugs){
                chargingPlugRepository.deleteById(plug.getId());
            }
            chargingPointRepository.deleteById(chargingPoint.getId());
        }

    }

    public void deleteChargingPointById(Long cId, Long id) {
        Optional<ChargingPoint> chargingPoint = chargingPointRepository.getChargingPointById(cId);

        if (chargingPoint.isEmpty()) {
            throw new NoSuchElementException("No charging point found!");
        }
        if (chargingPoint.get().getStation().getId() != id) {
            throw new WrongAccessException("You don't own this station!");
        }
        chargingPointRepository.deleteById(cId);
    }

}
