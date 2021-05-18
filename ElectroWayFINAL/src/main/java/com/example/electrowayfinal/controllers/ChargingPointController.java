package com.example.electrowayfinal.controllers;

import com.example.electrowayfinal.models.ChargingPoint;
import com.example.electrowayfinal.service.ChargingPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RequestMapping("/station")
@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ChargingPointController {
    private final ChargingPointService chargingPointService;

    @Autowired
    public ChargingPointController(ChargingPointService chargingPointService) {
        this.chargingPointService = chargingPointService;
    }

    @GetMapping(value = "{id}/points")
    public List<ChargingPoint> getAllPointsFromStation(@PathVariable Long id, HttpServletRequest httpServletRequest) {
        return chargingPointService.getAllChargingPointsByStationId(id, httpServletRequest);
    }

    @GetMapping(path = "/{id}/points/{cId}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<ChargingPoint> getChargingPoint(@PathVariable("id") Long id, @PathVariable("cId") Long cId, HttpServletRequest httpServletRequest) {
        return chargingPointService.findChargingPointById(id, cId, httpServletRequest);
    }

    @PostMapping(value = "{id}/points")
    @ResponseStatus(HttpStatus.OK)
    public ChargingPoint createChargingPoint(@PathVariable Long id, HttpServletRequest httpServletRequest) {
        return chargingPointService.createChargingPoint(id, httpServletRequest);
    }


    @DeleteMapping(path = "/{id}/points/{cId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("cId") Long cId, @PathVariable("id") Long id) {
        chargingPointService.deleteChargingPointById(cId, id);
    }
    //cId = id charging point
    //id = id station

}
