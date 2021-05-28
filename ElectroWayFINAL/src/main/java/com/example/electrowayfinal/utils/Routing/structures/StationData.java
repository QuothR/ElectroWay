package com.example.electrowayfinal.utils.Routing.structures;

import com.example.electrowayfinal.models.ChargingPlug;
import com.example.electrowayfinal.models.Station;

public class StationData {
    private Station station;
    private ChargingPlug chargingPlug;

    public StationData(Station station, ChargingPlug chargingPlug) {
        this.station = station;
        this.chargingPlug = chargingPlug;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public ChargingPlug getChargingPlug() {
        return chargingPlug;
    }

    public void setChargingPlug(ChargingPlug chargingPlug) {
        this.chargingPlug = chargingPlug;
    }

    @Override
    public String toString() {
        return "StationData{" +
                "\n\tstation=" + station +
                "\n\tchargingPlug=" + chargingPlug +
                "\n}\n";
    }
}
