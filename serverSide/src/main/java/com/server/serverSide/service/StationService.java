package com.server.serverSide.service;

import com.server.serverSide.dataBase.StationsDB;
import com.server.serverSide.model.Station;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationService {
    private final StationsDB dataBase;

    public StationService(@Qualifier("dataBase") StationsDB dataBase) {
        this.dataBase = dataBase;
    }

    public Station getStation(String id) {
        return dataBase.selectStation(id);
    }

    public List<Station> getStations() {
        return dataBase.getFakeStations();
    }

    public void addNewStation(Station station) {
        dataBase.insertStation(station);
    }

    public void updateStation(Station station, String id) {
        dataBase.updateStation(station, id);
    }

    public void deleteStation(Station station) {
        dataBase.deleteStation(station);
    }
}
