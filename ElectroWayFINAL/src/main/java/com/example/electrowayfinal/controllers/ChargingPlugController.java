package com.example.electrowayfinal.controllers;

import com.example.electrowayfinal.models.ChargingPlug;
import com.example.electrowayfinal.models.ChargingPoint;
import com.example.electrowayfinal.service.ChargingPlugService;
import com.example.electrowayfinal.service.ChargingPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/station")
public class ChargingPlugController {
    private final ChargingPlugService chargingPlugService;
    private final ChargingPointService chargingPointService;

    @Autowired
    public ChargingPlugController(ChargingPlugService chargingPlugService, ChargingPointService chargingPointService) {
        this.chargingPlugService = chargingPlugService;
        this.chargingPointService = chargingPointService;
    }

    @PostMapping(value = "{id}/points/{cId}")
    @ResponseStatus(HttpStatus.OK)
    public ChargingPlug createChargingPlug(@RequestBody ChargingPlug chargingPlug, @PathVariable("cId") Long cId, @PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws Exception {
        chargingPlugService.createChargingPlug(chargingPlug, cId, id, httpServletRequest);
        return chargingPlug;
    }

    @GetMapping(value = "{id}/points/{cId}/plugs")
    public List<ChargingPlug> getAllPlugsFromStation(@PathVariable("cId") Long cId, @PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws Exception {
        Optional<ChargingPoint> chargingPoint = chargingPointService.findChargingPointById(cId, id, httpServletRequest);
        if (chargingPoint.isEmpty())
            throw new Exception("There is no such chargingPlug bruh");
        return chargingPlugService.getChargingPlugsByChargingPoint(chargingPoint.get(), id);
    }

    @GetMapping(path = "/{id}/points/{cId}/plugs/{pId}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<ChargingPlug> getChargingPlug(@PathVariable("pId") Long pId, @PathVariable("id") Long id, @PathVariable("cId") Long cId, HttpServletRequest httpServletRequest) throws Exception {
        return chargingPlugService.getChargingPlugById(pId, id, cId, httpServletRequest);
    }

    @DeleteMapping(path = "/{id}/points/{cId}/plugs/{pId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("pId") Long pId, @PathVariable("id") Long id, @PathVariable("cId") Long cId, HttpServletRequest httpServletRequest) throws Exception {
        chargingPlugService.deleteChargingPlugById(pId, id, cId, httpServletRequest);
    }

}
