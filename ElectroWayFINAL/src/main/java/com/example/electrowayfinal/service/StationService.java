package com.example.electrowayfinal.service;

import com.example.electrowayfinal.exceptions.*;
import com.example.electrowayfinal.models.*;
import com.example.electrowayfinal.repositories.ChargingPlugRepository;
import com.example.electrowayfinal.repositories.ChargingPointRepository;
import com.example.electrowayfinal.repositories.StationRepository;
import com.example.electrowayfinal.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StationService {
    private final StationRepository stationRepository;
    private final UserService userService;
    private final ChargingPointRepository chargingPointRepository;
    private final ChargingPlugRepository chargingPlugRepository;
    private String secret;

    @Value("${jwt.secret}")
    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Lazy
    @Autowired
    public StationService(StationRepository stationRepository, UserService userService, ChargingPointRepository chargingPointRepository, ChargingPlugRepository chargingPlugRepository) {
        this.stationRepository = stationRepository;
        this.userService = userService;
        this.chargingPointRepository = chargingPointRepository;
        this.chargingPlugRepository = chargingPlugRepository;
    }

    public void createStation(Station station, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws RoleNotFoundException, ForbiddenRoleAssignmentAttemptException, UserNotFoundException {
        User user = JwtUtil.getUserFromToken(userService, secret, httpServletRequest);

        if (!user.getRoles().stream().map(Role::getName).collect(Collectors.toList()).contains("ROLE_OWNER")) {
            httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            throw new WrongPrivilegesException("Can't access station without being a station owner!");
        }

        if (user.getRoles().stream().map(Role::getName).noneMatch(s -> s.equals("ROLE_OWNER"))) {
            userService.addRole(user, "ROLE_OWNER");
        }
        station.setUser(user);

        stationRepository.save(station);

    }

    public Station getStation(Long id) {
        if (stationRepository.findStationById(id).isEmpty()) {
            throw new NoSuchElementException("Station does not exist!");
        }
        return stationRepository.findStationById(id).get();
    }

    //station
    public Station getCurrentStation(Long stationId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws UserNotFoundException {
        User user = JwtUtil.getUserFromToken(userService, secret, httpServletRequest);

        if (!user.getRoles().stream().map(Role::getName).collect(Collectors.toList()).contains("ROLE_OWNER")) {
            httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            throw new WrongPrivilegesException("Can't access station without being a station owner!");
        }

        Optional<Station> station = stationRepository.findStationById(stationId);
        if (station.isEmpty()) {
            throw new NoSuchElementException("Station does not exist!");
        }

        if (!station.get().getUser().getEmailAddress().equals(user.getEmailAddress())) {
            throw new WrongAccessException("You don't own this station!");
        }

        return station.get();
    }

    public List<Station> getStations(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws UserNotFoundException {
        User user = JwtUtil.getUserFromToken(userService, secret, httpServletRequest);

        if (!user.getRoles().stream().map(Role::getName).collect(Collectors.toList()).contains("ROLE_OWNER")) {
            httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            throw new WrongPrivilegesException("Can't access station without being a station owner!");
        }

        return stationRepository.findAll().stream().filter(s -> s.getUser().getUsername().equals(user.getUsername())).collect(Collectors.toList());
    }

    public Station updateStation(Station station, Long stationId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws UserNotFoundException {
        Station stationToUpdate = checkUserAndStation(stationId, httpServletRequest, httpServletResponse);

        stationToUpdate.setLongitude(station.getLongitude());
        stationToUpdate.setAddress(station.getAddress());
        stationToUpdate.setLatitude(station.getLatitude());
        stationToUpdate.setDescription(station.getDescription());

        station.setUser(stationToUpdate.getUser());// TODO is this necessary?
        stationRepository.save(stationToUpdate);
        return stationToUpdate;
    }

    public void deleteStation(Long stationId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws UserNotFoundException {
        checkUserAndStation(stationId, httpServletRequest, httpServletResponse);
        List<ChargingPoint> chargingPoints = chargingPointRepository.getChargingPointsByStation_Id(stationId);
        if (!chargingPoints.isEmpty()) {
            for (ChargingPoint point : chargingPoints) {
                List<ChargingPlug> chargingPlugs = chargingPlugRepository.findChargingPlugsByChargingPointId(point.getId());
                if (!chargingPlugs.isEmpty()) {
                    for (ChargingPlug plug : chargingPlugs) {
                        chargingPlugRepository.deleteById(plug.getId());
                    }
                }
                chargingPointRepository.deleteById(point.getId());

            }
        }
        stationRepository.deleteById(stationId);
    }

    public List<Station> getAllStations() throws UserNotFoundException {
        return stationRepository.findAll();
    }

    private Station checkUserAndStation(Long stationId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws UserNotFoundException {
        User user = JwtUtil.getUserFromToken(userService, secret, httpServletRequest);

        if (!user.getRoles().stream().map(Role::getName).collect(Collectors.toList()).contains("ROLE_OWNER")) {
            httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            throw new WrongPrivilegesException("Can't access station without being a station owner!");
        }

        Station station = getCurrentStation(stationId, httpServletRequest, httpServletResponse);

        if (station.getUser() != user) {
            throw new WrongAccessException("You don't own this station!");
        }
        return station;
    }

}
