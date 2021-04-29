package com.example.electrowayfinal.service;

import com.example.electrowayfinal.models.ChargingPoint;
import com.example.electrowayfinal.models.Station;
import com.example.electrowayfinal.models.User;
import com.example.electrowayfinal.repositories.ChargingPlugRepository;
import com.example.electrowayfinal.repositories.ChargingPointRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
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

    @Autowired
    public ChargingPointService(ChargingPointRepository chargingPointRepository, ChargingPlugRepository chargingPlugRepository, StationService stationService, UserService userService) {
        this.chargingPointRepository = chargingPointRepository;
        this.chargingPlugRepository = chargingPlugRepository;
        this.stationService = stationService;
        this.userService = userService;
    }
    public void createChargingPoint(ChargingPoint chargingPoint,Long id, HttpServletRequest httpServletRequest) throws Exception {
        Station station = stationService.getStation(id);
        String bearerToken = httpServletRequest.getHeader("Authorization");
        bearerToken = bearerToken.substring(6);

        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(bearerToken).getBody();
        String username = claims.getSubject();

        Optional<User> optionalUser = userService.getOptionalUserByUsername(username);

        if (optionalUser.isEmpty())
            throw new Exception("wrong user in station service");
        if(!station.getUser().equals(optionalUser.get()))
            throw new Exception("NU DETII ACEST STATION DOMNULE/DOAMNO/ ->");

        chargingPoint.setStation(station);
        chargingPointRepository.save(chargingPoint);
    }
    public void deleteChargingPoint(ChargingPoint chargingPoint){
        chargingPointRepository.delete(chargingPoint);
    }

    public void deleteChargingPointById(Long cId, Long id) throws Exception {
        Optional<ChargingPoint> chargingPoint = chargingPointRepository.getChargingPointById(cId);

        if (chargingPoint.isEmpty())
            throw new Exception("There is no such chargingPoint bruh");
        if(chargingPoint.get().getStation().getId()!=id){
            throw new Exception("hopa, nu detii aceasta statie, cf?");
        }
        chargingPointRepository.deleteById(cId);
    }

    public Optional<ChargingPoint> findChargingPointById(Long id,Long cId,HttpServletRequest httpServletRequest) throws Exception {
        Optional<ChargingPoint> chargingPoint = chargingPointRepository.getChargingPointById(id);

        if (chargingPoint.isEmpty())
            throw new Exception("There is no such chargingPoint bruh");
        if (chargingPoint.get().getStation().getId()!=cId){
            throw new Exception("hopa, nu detii aceasta statie, cf?");
        }
        Station station = stationService.getStation(chargingPoint.get().getStation().getId());
        String bearerToken = httpServletRequest.getHeader("Authorization");
        bearerToken = bearerToken.substring(6);

        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(bearerToken).getBody();
        String username = claims.getSubject();

        Optional<User> optionalUser = userService.getOptionalUserByUsername(username);

        if (optionalUser.isEmpty())
            throw new Exception("wrong user in station service");
        if(!station.getUser().equals(optionalUser.get()))
            throw new Exception("NU DETII ACEST STATION DOMNULE/DOAMNO/ ->");

        return chargingPoint;
    }
    public List<ChargingPoint> getAllChargingPointsByStationId(Long stationId,HttpServletRequest httpServletRequest) throws Exception {

        Station station = stationService.getStation(stationId);
        String bearerToken = httpServletRequest.getHeader("Authorization");
        bearerToken = bearerToken.substring(6);

        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(bearerToken).getBody();
        String username = claims.getSubject();

        Optional<User> optionalUser = userService.getOptionalUserByUsername(username);

        if (optionalUser.isEmpty())
            throw new Exception("wrong user in station service");
        if(!station.getUser().equals(optionalUser.get()))
            throw new Exception("NU DETII ACEST STATION DOMNULE/DOAMNO/ ->");

        return chargingPointRepository.findChargingPointsByStation_Id(stationId);
    }

}
