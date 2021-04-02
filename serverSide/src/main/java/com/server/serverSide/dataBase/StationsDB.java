package com.server.serverSide.dataBase;

import com.server.serverSide.model.Location;
import com.server.serverSide.model.Station;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("dataBase")
public class StationsDB {
    private static List<Station> stations = new ArrayList<>();

    public StationsDB(){
        stations.add(new Station(new Location("Brasov", "Brasov", "Romania", "Brasov", 21.22, 23.22),3,"123"));
        stations.add(new Station(new Location("Brasov", "Brasov", "Romania", "Brasov", 21.22, 23.22),3,"124"));
    }
    public void insertStation(Station station){
        stations.add(station);
    }
    public void deleteStation(Station station){
        stations.remove(station);
    }
    public void updateStation(Station station, String id){
        for(int index = 0; index < stations.size(); index++){
            if(stations.get(index).getId().compareTo(id) == 0){
                stations.remove(stations.get(index));
                stations.add(station);
                break;
            }
        }
    }
    public Station selectStation(String id){
        for(Station station:stations)
            System.out.println(station.getId());
        return null;
    }
    public List<Location> getStationLocations(){
        List<Location> locationList = new ArrayList<>();
        for(int i =0; i< stations.size(); i++){
            locationList.add(stations.get(i).getLocation());
        }
        return locationList;
    }
    public List<Location> getLocationz() {
        List<Location> lista=new ArrayList<>();
        for(Station statie:stations){
            lista.add(statie.getLocation());
        }
        return lista;
    }
    public List<Station> getFakeStations() {

        //return stations;
        return stations;
    }
}
