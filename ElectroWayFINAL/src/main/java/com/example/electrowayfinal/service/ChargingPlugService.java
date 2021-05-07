package com.example.electrowayfinal.service;

import com.example.electrowayfinal.exceptions.WrongAccessException;
import com.example.electrowayfinal.models.ChargingPlug;
import com.example.electrowayfinal.models.ChargingPoint;
import com.example.electrowayfinal.repositories.ChargingPlugRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
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
            NoSuchElementException exception = new NoSuchElementException("Charging point in charging plug search is empty!");
            log.error(exception.getMessage());
            throw exception;
        }
        if (chargingPoint.get().getStation().getId() != id) {
            WrongAccessException exception = new WrongAccessException("You don't own this station!");
            log.error(exception.getMessage());
            throw exception;
        }
        Optional<ChargingPlug> chargingPlug = chargingPlugRepository.findChargingPlugById(pId);
        if (chargingPlug.isEmpty()) {
            NoSuchElementException exception = new NoSuchElementException("Charging plug does not exist!");
            log.error(exception.getMessage());
            throw exception;
        }
        if (chargingPoint.get().getId() != chargingPlug.get().getChargingPoint().getId()) {
            WrongAccessException exception = new WrongAccessException("You don't own this charging point!");
            log.error(exception.getMessage());
            throw exception;
        }
        chargingPlugRepository.deleteById(pId);
    }

    public List<ChargingPlug> getChargingPlugsByChargingPoint(ChargingPoint chargingPoint, Long id) {
        if (chargingPoint.getStation().getId() != id) {
            WrongAccessException exception = new WrongAccessException("You don't own this station!");
            log.error(exception.getMessage());
            throw exception;
        }
        return chargingPlugRepository.findChargingPlugsByChargingPoint(chargingPoint);
    }

    public List<ChargingPlug> getAll() {
        return chargingPlugRepository.findAll();
    }

    public List<ChargingPlug> getChargingPlugsByConnectorType(String connectorType) {
        return chargingPlugRepository.findChargingPlugsByConnectorType(connectorType);
    }

    public Optional<ChargingPlug> getChargingPlugById(Long pId, Long id, Long cId, HttpServletRequest httpServletRequest) {
        Optional<ChargingPoint> chargingPoint = chargingPointService.findChargingPointById(cId, id, httpServletRequest);
        if (chargingPoint.isEmpty()) {
            NoSuchElementException exception = new NoSuchElementException("Charging point in charging plug search is empty!");
            log.error(exception.getMessage());
            throw exception;
        }
        if (chargingPoint.get().getStation().getId() != id) {
            WrongAccessException exception = new WrongAccessException("You don't own this station!");
            log.error(exception.getMessage());
            throw exception;
        }
        Optional<ChargingPlug> chargingPlug = chargingPlugRepository.findChargingPlugById(pId);
        if (chargingPlug.isEmpty()) {
            WrongAccessException exception = new WrongAccessException("You don't own this charging plug!");
            log.error(exception.getMessage());
            throw exception;
        }
        if (chargingPoint.get().getId() != chargingPlug.get().getChargingPoint().getId()) {
            WrongAccessException exception = new WrongAccessException("You don't own this charging point!");
            log.error(exception.getMessage());
            throw exception;
        }
        return chargingPlug;
    }

    public ChargingPlug updateChargingPlug(ChargingPlug chargingPlug, Long id, HttpServletRequest httpServletRequest) {
        Optional<ChargingPlug> chargingPlugToUpdate = chargingPlugRepository.findChargingPlugById(id);
        if (chargingPlugToUpdate.isEmpty()) {
            NoSuchElementException exception = new NoSuchElementException("No charging plug to update!");
            log.error(exception.getMessage());
            throw exception;
        }
        chargingPlugToUpdate.get().setChargingSpeedKw(chargingPlug.getChargingSpeedKw());
        chargingPlugToUpdate.get().setConnectorType(chargingPlug.getConnectorType());
        chargingPlugToUpdate.get().setPriceKw(chargingPlug.getPriceKw());

        return chargingPlugToUpdate.get();
    }
}
