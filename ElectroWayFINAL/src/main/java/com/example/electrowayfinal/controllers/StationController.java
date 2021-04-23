package com.example.electrowayfinal.controllers;

import com.example.electrowayfinal.models.Station;
import com.example.electrowayfinal.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.util.List;

@RestController
@RequestMapping("/station")
@Qualifier("stations")
public class StationController {

    private final StationService stationService;

    @Autowired
    public StationController(StationService stationService) {

       this.stationService = stationService;
    }

    @GetMapping()
    public List<Station> station(HttpServletRequest httpServletRequest){
        return stationService.getStations(httpServletRequest);
    }

    @PostMapping()
    public Station createStation(@RequestBody Station station, HttpServletRequest httpServletRequest) throws Exception{
        int a = 3;
        stationService.createStation(station,httpServletRequest);
        return stationService.getStation(station.getId());
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public Station updateStation(@RequestBody Station newStation,@PathVariable Long id,HttpServletRequest httpServletRequest) throws Exception{
        System.out.println("in put mapping\n");
        stationService.createStation(stationService.updateStation(newStation,id,httpServletRequest),httpServletRequest);
        return newStation;
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id) {
        stationService.deleteStation(id);
    }

}
