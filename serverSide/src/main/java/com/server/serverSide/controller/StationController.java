package com.server.serverSide.controller;

import com.server.serverSide.model.Station;
import com.server.serverSide.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api")
public class StationController {
    private final StationService stationService;

    @Autowired
    public StationController(StationService stationService) {
        this.stationService = stationService;
    }

    @GetMapping(path = "/station/{id}")
    public Station getStation(String id) {
        return stationService.getStation(id);
    }

    @GetMapping(path="/stations")
    public List<Station> getStations() {
        return stationService.getStations();
    }

    @PostMapping(path = "/stations")
    public void postStation(@RequestBody Station newStation) {
        stationService.addNewStation(newStation);
    }

    @PatchMapping(path = "/stations/{id}")
    public void putStation(@RequestBody Station station, @PathVariable String id){
        stationService.updateStation(station, id);
    }

    @DeleteMapping(path = "/stations")
    public void deleteStation(@RequestBody Station station){
        stationService.deleteStation(station);
    }
}
