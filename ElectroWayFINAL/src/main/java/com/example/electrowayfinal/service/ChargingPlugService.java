package com.example.electrowayfinal.service;

import com.example.electrowayfinal.exceptions.WrongAccessException;
import com.example.electrowayfinal.models.ChargingPlug;
import com.example.electrowayfinal.models.ChargingPoint;
import com.example.electrowayfinal.models.Station;
import com.example.electrowayfinal.models.User;
import com.example.electrowayfinal.repositories.ChargingPlugRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

//TODO De adaugat verificare de ROLE_OWNER la toate metodele
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

    public void createChargingPlug(ChargingPlug chargingPlug, Long id, Long cId, HttpServletRequest httpServletRequest) {
        Optional<ChargingPoint> chargingPoint = chargingPointService.findChargingPointById(id, cId, httpServletRequest);
        if (chargingPoint.isEmpty()) {
            throw new NoSuchElementException("Charging point does not exist!");
        }
        if (chargingPoint.get().getStation().getId() != id) {
            throw new WrongAccessException("You don't own this station!");
        }
        chargingPlug.setChargingPoint(chargingPoint.get());

        chargingPlugRepository.save(chargingPlug);
    }

    public User getUserFromPlug(Long plugId) {
        ChargingPlug chargingPlug = chargingPlugRepository.getOne(plugId);
        return chargingPlug.getChargingPoint().getStation().getUser();
    }

    public List<ChargingPlug> getChargingPlugsByChargingPoint(ChargingPoint chargingPoint, Long id) {
        return chargingPlugRepository.findChargingPlugsByChargingPoint(chargingPoint);
    }

    public List<ChargingPlug> getAll() {
        return chargingPlugRepository.findAll();
    }

    public List<ChargingPlug> getChargingPlugsByConnectorType(String connectorType) {
        return chargingPlugRepository.findChargingPlugsByConnectorType(connectorType);
    }

    public ChargingPlug getOnePlug(Long id) {
        if (chargingPlugRepository.findChargingPlugById(id).isEmpty()) {
            throw new NoSuchElementException(id + "charging plug does not exist");
        }
        return chargingPlugRepository.findChargingPlugById(id).get();
    }

    public Optional<ChargingPlug> getChargingPlugById(Long id, Long cId, Long pId, HttpServletRequest httpServletRequest) {
        Optional<ChargingPoint> chargingPoint = chargingPointService.findChargingPointById(id, cId, httpServletRequest);
        if (chargingPoint.isEmpty()) {
            throw new NoSuchElementException("Charging point in charging plug search is empty!");
        }
        if (chargingPoint.get().getStation().getId() != id) {
            throw new WrongAccessException("You don't own this station!");
        }
        Optional<ChargingPlug> chargingPlug = chargingPlugRepository.findChargingPlugById(pId);
        if (chargingPlug.isEmpty()) {
            throw new WrongAccessException("You don't own this charging plug!");
        }
        if (chargingPoint.get().getId() != chargingPlug.get().getChargingPoint().getId()) {
            throw new WrongAccessException("You don't own this charging point!");
        }
        return chargingPlug;
    }

    public ChargingPlug updateChargingPlug(ChargingPlug chargingPlug, Long id, HttpServletRequest httpServletRequest) {
        Optional<ChargingPlug> chargingPlugToUpdate = chargingPlugRepository.findChargingPlugById(id);
        if (chargingPlugToUpdate.isEmpty()) {
            throw new NoSuchElementException("No charging plug to update!");
        }
        chargingPlugToUpdate.get().setChargingSpeedKw(chargingPlug.getChargingSpeedKw());
        chargingPlugToUpdate.get().setConnectorType(chargingPlug.getConnectorType());
        chargingPlugToUpdate.get().setPriceKw(chargingPlug.getPriceKw());

        return chargingPlugToUpdate.get();
    }

    public void deleteChargingPlug(ChargingPlug chargingPlug) {
        chargingPlugRepository.delete(chargingPlug);
    }

    //TODO de schimbat exceptiile astea, nu asa ar trebui sa arate
    public void deleteChargingPlugById(Long pId, Long id, Long cId, HttpServletRequest httpServletRequest) {
        Optional<ChargingPoint> chargingPoint = chargingPointService.findChargingPointById(cId, id, httpServletRequest);
        if (chargingPoint.isEmpty()) {
            throw new NoSuchElementException("Charging point in charging plug search is empty!");
        }
        if (chargingPoint.get().getStation().getId() != id) {
            throw new WrongAccessException("You don't own this station!");
        }
        Optional<ChargingPlug> chargingPlug = chargingPlugRepository.findChargingPlugById(pId);
        if (chargingPlug.isEmpty()) {
            throw new NoSuchElementException("Charging plug does not exist!");
        }
        if (chargingPoint.get().getId() != chargingPlug.get().getChargingPoint().getId()) {
            throw new WrongAccessException("You don't own this charging point!");
        }
        chargingPlugRepository.deleteById(pId);
    }

    public Optional<ChargingPlug> updateChargingPlug(Long id, Long cId, Long pId, ChargingPlug chargingPlug, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        Optional<ChargingPlug> optionalChargingPlug = chargingPlugRepository.findChargingPlugById(pId);

        if (optionalChargingPlug.isEmpty())
            throw new Exception(pId.toString());
        if(chargingPointService.getChargingPoint(cId).isEmpty()){
            throw new NoSuchElementException("The charging point with the id " + cId + "does not exist");
        }
        if(stationService.getStations(httpServletRequest,httpServletResponse).isEmpty()){
            throw new NoSuchElementException("The station with the id " + id + "does not exist");
        }
        int ok=0;
        List<ChargingPoint> chargingPoints = chargingPointService.getChargingPointsByStationId(id);
        for(ChargingPoint chargingPoint: chargingPoints){
            if(chargingPoint.getId()==cId && chargingPlug.getChargingPoint().getId()==cId){
                ok=1;
                break;
            }
        }
        if(ok==0){
            throw new NoSuchMethodException("The charging point with the id "+ cId +" does not own this charging plug");
        }
        optionalChargingPlug.get().setChargingSpeedKw(chargingPlug.getChargingSpeedKw());
        optionalChargingPlug.get().setConnectorType(chargingPlug.getConnectorType());
        optionalChargingPlug.get().setPriceKw(chargingPlug.getPriceKw());
        optionalChargingPlug.get().setStatus(chargingPlug.getStatus());

        chargingPlugRepository.save(optionalChargingPlug.get());

        return optionalChargingPlug;
    }
}
