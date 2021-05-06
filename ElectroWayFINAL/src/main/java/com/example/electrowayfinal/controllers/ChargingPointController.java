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
@RestController
public class ChargingPointController {
    private final ChargingPointService chargingPointService;

    @Autowired
    public ChargingPointController(ChargingPointService chargingPointService) {
        this.chargingPointService = chargingPointService;
    }

    @GetMapping(value = "{id}/points")
    public List<ChargingPoint> getAllPointsFromStation(@PathVariable Long id, HttpServletRequest httpServletRequest) throws Exception {
        return chargingPointService.getAllChargingPointsByStationId(id, httpServletRequest);
    }

    @GetMapping(path = "/{id}/points/{cId}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<ChargingPoint> getChargingPoint(@PathVariable("id") Long id, @PathVariable("cId") Long cId, HttpServletRequest httpServletRequest) throws Exception {
        return chargingPointService.findChargingPointById(id, cId, httpServletRequest);
    }

    @PostMapping(value = "{id}/points")
    @ResponseStatus(HttpStatus.OK)
    public ChargingPoint createChargingPoint(@RequestBody ChargingPoint chargingPoint, @PathVariable Long id, HttpServletRequest httpServletRequest) throws Exception {
        chargingPointService.createChargingPoint(chargingPoint, id, httpServletRequest);
        return chargingPoint;
    }


    @DeleteMapping(path = "/{id}/points/{cId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("cId") Long cId, @PathVariable("id") Long id) throws Exception {
        chargingPointService.deleteChargingPointById(cId, id);
    }


}
