package com.example.electrowayfinal.service;

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

    public void createStation(Station station, HttpServletRequest httpServletRequest) throws Exception {

        String bearerToken = httpServletRequest.getHeader("Authorization");
        bearerToken = bearerToken.substring(6);

        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(bearerToken).getBody();
        String username = claims.getSubject();

        Optional<User> optionalUser = userService.getOptionalUserByUsername(username);

        if (optionalUser.isEmpty())
            throw new Exception("wrong user in station service");

        station.setUser(optionalUser.get());

        stationRepository.save(station);

    }

    public void deleteStation(Long id) {
        stationRepository.deleteById(id);
    }

    public Station getStation(Long id) {
        return stationRepository.getOne(id);
    }

    public List<Station> getStations(HttpServletRequest httpServletRequest) {

        String bearToken = httpServletRequest.getHeader("Authorization");
        bearToken = bearToken.substring(6);

        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(bearToken).getBody();
        String username = claims.getSubject();

        return stationRepository.findAll().stream().filter(s -> s.getUser().getUsername().equals(username)).collect(Collectors.toList());
    }

    public Station updateStation(Station station, Long id, HttpServletRequest httpServletRequest) throws Exception {
        Station stationToUpdate = stationRepository.findStationById(id).get();
        stationToUpdate.setLongitude(station.getLongitude());
        stationToUpdate.setAddress(station.getAddress());
        stationToUpdate.setLatitude(station.getLatitude());

        String bearerToken = httpServletRequest.getHeader("Authorization");
        bearerToken = bearerToken.substring(6);

        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(bearerToken).getBody();
        String username = claims.getSubject();

        Optional<User> optionalUser = userService.getOptionalUserByUsername(username);

        if (optionalUser.isEmpty())
            throw new Exception("wrong user in station service");

        station.setUser(optionalUser.get());
        stationRepository.save(stationToUpdate);
        return stationToUpdate;
    }
}
