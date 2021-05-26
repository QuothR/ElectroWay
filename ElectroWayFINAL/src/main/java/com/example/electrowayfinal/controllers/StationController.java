package com.example.electrowayfinal.controllers;

import com.example.electrowayfinal.exceptions.ForbiddenRoleAssignmentAttemptException;
import com.example.electrowayfinal.exceptions.UserNotFoundException;
import com.example.electrowayfinal.models.Station;
import com.example.electrowayfinal.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/station")
@Qualifier("stations")
@CrossOrigin(origins = "https://electrowayweb.herokuapp.com")
public class StationController {

    private final StationService stationService;

    @Autowired
    public StationController(StationService stationService) {

        this.stationService = stationService;
    }

    @GetMapping()
    public List<Station> getUserStations(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws UserNotFoundException {
        return stationService.getStations(httpServletRequest, httpServletResponse);
    }

    @GetMapping(path = "/all")
    public List<Station> getAllStations() throws UserNotFoundException {
        return stationService.getAllStations();
    }

    @PostMapping()
    public Station createStation(@RequestBody Station station, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws RoleNotFoundException, ForbiddenRoleAssignmentAttemptException, UserNotFoundException {
        stationService.createStation(station, httpServletRequest, httpServletResponse);
        return httpServletResponse.getStatus() == HttpServletResponse.SC_FORBIDDEN ? null : stationService.getStation(station.getId());
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public Station updateStation(@RequestBody Station newStation, @PathVariable Long id, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws UserNotFoundException {
        return stationService.updateStation(newStation, id, httpServletRequest, httpServletResponse);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws UserNotFoundException {
        stationService.deleteStation(id, httpServletRequest, httpServletResponse);
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Station getStation(@PathVariable("id") Long id, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws UserNotFoundException {
        return stationService.getCurrentStation(id, httpServletRequest, httpServletResponse);
    }

}
