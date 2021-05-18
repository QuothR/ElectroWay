package com.example.electrowayfinal.utils.Routing.structures;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Coords {
    private Double lat;
    private Double lon;

    public Coords() {

    }

    @JsonCreator
    public Coords(
            @JsonProperty("lat") Double lat,
            @JsonProperty("lon") Double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    @Override
    public String toString() {
        return "Coords{" +
                "lat=" + lat +
                ", lon=" + lon +
                '}';
    }
}
