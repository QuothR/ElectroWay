package com.example.electrowayfinal.service;

import com.example.electrowayfinal.exceptions.WrongAccessException;
import com.example.electrowayfinal.exceptions.WrongUserInServiceException;
import com.example.electrowayfinal.models.Station;
import com.example.electrowayfinal.models.User;
import com.example.electrowayfinal.repositories.StationRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StationService {
    private final StationRepository stationRepository;
    private final UserService userService;
    private String secret;

    @Value("${jwt.secret}")
    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Autowired
    public StationService(StationRepository stationRepository, UserService userService) {
        this.stationRepository = stationRepository;
        this.userService = userService;
    }

    public void createStation(Station station, HttpServletRequest httpServletRequest) {

        String bearerToken = httpServletRequest.getHeader("Authorization");
        bearerToken = bearerToken.substring(6);

        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(bearerToken).getBody();
        String username = claims.getSubject();

        Optional<User> optionalUser = userService.getOptionalUserByUsername(username);

        if (optionalUser.isEmpty()) {
            throw new WrongUserInServiceException("Wrong user in station service!");
        }

        station.setUser(optionalUser.get());

        stationRepository.save(station);

    }

    public void deleteStation(Long id, HttpServletRequest httpServletRequest) {
        Optional<Station> station = getCurrentStation(id, httpServletRequest);
        if (station.isEmpty()) {
            throw new NoSuchElementException("Station does not exist!");
        }
        String bearerToken = httpServletRequest.getHeader("Authorization");
        bearerToken = bearerToken.substring(6);

        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(bearerToken).getBody();
        String username = claims.getSubject();

        Optional<User> optionalUser = userService.getOptionalUserByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new NoSuchElementException("User does not exist!");
        }

        if (station.get().getUser() != optionalUser.get()) {
            throw new WrongAccessException("You don't own this station!");
        }
        stationRepository.deleteById(id);
    }

    public Station getStation(Long id) {
        return stationRepository.getOne(id);
    }

    public Optional<Station> getCurrentStation(Long id, HttpServletRequest httpServletRequest) {
        Optional<Station> station = stationRepository.findStationById(id);
        if (station.isEmpty()) {
            throw new NoSuchElementException("Station does not exist!");
        }

        String bearerToken = httpServletRequest.getHeader("Authorization");
        bearerToken = bearerToken.substring(6);

        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(bearerToken).getBody();
        String username = claims.getSubject();

        Optional<User> optionalUser = userService.getOptionalUserByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new NoSuchElementException("User does not exist!");
        }

        if (station.get().getUser() != optionalUser.get()) {
            throw new WrongAccessException("You don't own this station!");
        }
        return station;
    }

    public List<Station> getStations(HttpServletRequest httpServletRequest) {

        String bearToken = httpServletRequest.getHeader("Authorization");
        bearToken = bearToken.substring(6);

        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(bearToken).getBody();
        String username = claims.getSubject();

        return stationRepository.findAll().stream().filter(s -> s.getUser().getUsername().equals(username)).collect(Collectors.toList());
    }

    public Station updateStation(Station station, Long id, HttpServletRequest httpServletRequest) {
        Optional<Station> stationToUpdate = stationRepository.findStationById(id);

        if (stationToUpdate.isEmpty()) {
            throw new NoSuchElementException("Station does not exist!");
        }

        stationToUpdate.get().setLongitude(station.getLongitude());
        stationToUpdate.get().setAddress(station.getAddress());
        stationToUpdate.get().setLatitude(station.getLatitude());

        String bearerToken = httpServletRequest.getHeader("Authorization");
        bearerToken = bearerToken.substring(6);

        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(bearerToken).getBody();
        String username = claims.getSubject();

        Optional<User> optionalUser = userService.getOptionalUserByUsername(username);

        if (optionalUser.isEmpty()) {
            throw new WrongUserInServiceException("Wrong user in station service!");
        }
        if (stationToUpdate.get().getUser() != optionalUser.get()) {
            throw new WrongAccessException("You don't own this station!");
        }
        station.setUser(optionalUser.get());
        stationRepository.save(stationToUpdate.get());
        return stationToUpdate.get();
    }
}
