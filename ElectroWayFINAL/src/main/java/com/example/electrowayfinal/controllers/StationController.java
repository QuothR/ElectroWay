package com.example.electrowayfinal.controllers;

import com.example.electrowayfinal.exceptions.UserNotFoundException;
import com.example.electrowayfinal.models.Station;
import com.example.electrowayfinal.service.StationService;
//import org.json.JSONException;
//import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/station")
@Qualifier("stations")
@CrossOrigin(origins = "http://localhost:3000")
public class StationController {

    private final StationService stationService;

    @Autowired
    public StationController(StationService stationService) {

        this.stationService = stationService;
    }

    @GetMapping()
    public List<Station> getUserStations(HttpServletRequest httpServletRequest) {
        return stationService.getStations(httpServletRequest);
    }

    @PostMapping()
    public Station createStation(@RequestBody Station station, HttpServletRequest httpServletRequest) throws UserNotFoundException, RoleNotFoundException {
        stationService.createStation(station, httpServletRequest);
        return stationService.getStation(station.getId());

    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public Station updateStation(@RequestBody Station newStation, @PathVariable Long id, HttpServletRequest httpServletRequest) {
        return stationService.updateStation(newStation, id, httpServletRequest);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) {
        stationService.deleteStation(id, httpServletRequest);
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Station> getStation(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) {
        return stationService.getCurrentStation(id, httpServletRequest);
    }

}
