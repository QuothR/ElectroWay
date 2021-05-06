package com.example.electrowayfinal.service;

import com.example.electrowayfinal.models.ChargingPlug;
import com.example.electrowayfinal.models.ChargingPoint;
import com.example.electrowayfinal.repositories.ChargingPlugRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Service
public class ChargingPlugService {
    private final ChargingPlugRepository chargingPlugRepository;
    private final ChargingPointService chargingPointService;
    private final StationService stationService;
    private final UserService userService;
    private String secret;

    @Value("${jwt.secret}")
    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Autowired
    public ChargingPlugService(ChargingPlugRepository chargingPlugRepository, ChargingPointService chargingPointService, StationService stationService, UserService userService) {
        this.chargingPlugRepository = chargingPlugRepository;
        this.chargingPointService = chargingPointService;
        this.stationService = stationService;
        this.userService = userService;
    }

    public void createChargingPlug(ChargingPlug chargingPlug, Long cId, Long id, HttpServletRequest httpServletRequest) throws Exception {
        Optional<ChargingPoint> chargingPoint = chargingPointService.findChargingPointById(cId, id, httpServletRequest);
        if (chargingPoint.isEmpty())
            throw new Exception("cannot set chargingPoint to chargingPlug bruh");
        if (chargingPoint.get().getStation().getId() != id) {
            throw new Exception("Hopa, se pare ca nu detii aceasta statie, ce vrei sa faci????????!");
        }
        chargingPlug.setChargingPoint(chargingPoint.get());

        chargingPlugRepository.save(chargingPlug);
    }

    public void deleteChargingPlug(ChargingPlug chargingPlug) {
        chargingPlugRepository.delete(chargingPlug);
    }

    public void deleteChargingPlugById(Long pId, Long id, Long cId, HttpServletRequest httpServletRequest) throws Exception {
        Optional<ChargingPoint> chargingPoint = chargingPointService.findChargingPointById(cId, id, httpServletRequest);
        if (chargingPoint.isEmpty()) {
            throw new Exception("Charging point in charging plug search is empty?");
        }
        if (chargingPoint.get().getStation().getId() != id) {
            throw new Exception("Hopa, nu detii acest station");
        }
        Optional<ChargingPlug> chargingPlug = chargingPlugRepository.findChargingPlugById(pId);
        if (chargingPlug.isEmpty()) {
            throw new Exception("Acest charging plug nu exista");
        }
        if (chargingPoint.get().getId() != chargingPlug.get().getChargingPoint().getId()) {
            throw new Exception("Alt hopa, se pare ca nu detii acest charging point??");
        }
        chargingPlugRepository.deleteById(pId);
    }

    public List<ChargingPlug> getChargingPlugsByChargingPoint(ChargingPoint chargingPoint, Long id) throws Exception {
        if (chargingPoint.getStation().getId() != id) {
            throw new Exception("HOPAAAA, se pare ca nu detii aceasta statie, pacat");
        }
        return chargingPlugRepository.findChargingPlugsByChargingPoint(chargingPoint);
    }

    public List<ChargingPlug> getAll() {
        return chargingPlugRepository.findAll();
    }

    public List<ChargingPlug> getChargingPlugsByConnectorType(String connectorType) {
        return chargingPlugRepository.findChargingPlugsByConnectorType(connectorType);
    }

    public Optional<ChargingPlug> getChargingPlugById(Long pId, Long id, Long cId, HttpServletRequest httpServletRequest) throws Exception {
        Optional<ChargingPoint> chargingPoint = chargingPointService.findChargingPointById(cId, id, httpServletRequest);
        if (chargingPoint.isEmpty()) {
            throw new Exception("Charging point in charging plug search is empty?");
        }
        if (chargingPoint.get().getStation().getId() != id) {
            throw new Exception("Hopa, nu detii acest station");
        }
        Optional<ChargingPlug> chargingPlug = chargingPlugRepository.findChargingPlugById(pId);
        if (chargingPlug.isEmpty()) {
            throw new Exception("Acest charging plug nu exista");
        }
        if (chargingPoint.get().getId() != chargingPlug.get().getChargingPoint().getId()) {
            throw new Exception("Alt hopa, se pare ca nu detii acest charging point??");
        }
        return chargingPlug;
    }

    public ChargingPlug updateChargingPlug(ChargingPlug chargingPlug, Long id, HttpServletRequest httpServletRequest) throws Exception {
        Optional<ChargingPlug> chargingPlugToUpdate = chargingPlugRepository.findChargingPlugById(id);
        if (chargingPlugToUpdate.isEmpty())
            throw new Exception("there is no chargingPlug to update");
        chargingPlugToUpdate.get().setChargingSpeedKw(chargingPlug.getChargingSpeedKw());
        chargingPlugToUpdate.get().setConnectorType(chargingPlug.getConnectorType());
        chargingPlugToUpdate.get().setPriceKw(chargingPlug.getPriceKw());

        return chargingPlugToUpdate.get();
    }
}
