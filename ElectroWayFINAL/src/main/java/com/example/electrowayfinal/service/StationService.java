package com.example.electrowayfinal.service;

import com.example.electrowayfinal.exceptions.*;
import com.example.electrowayfinal.models.Role;
import com.example.electrowayfinal.models.Station;
import com.example.electrowayfinal.models.User;
import com.example.electrowayfinal.repositories.StationRepository;
import com.example.electrowayfinal.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
        return stationRepository.getOne(id);
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

        if (station.get().getUser() != user) {
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

        station.setUser(stationToUpdate.getUser());// TODO is this necessary?
        stationRepository.save(stationToUpdate);
        return stationToUpdate;
    }

    public void deleteStation(Long stationId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws UserNotFoundException {
        checkUserAndStation(stationId, httpServletRequest, httpServletResponse);

        stationRepository.deleteById(stationId);
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
