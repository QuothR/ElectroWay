package com.example.electrowayfinal.utils.Routing.structures;

import com.example.electrowayfinal.Validation.RoutingRequestDataConstraint;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@RoutingRequestDataConstraint
public class RoutingRequestData {
    private List<Coords> locationsCoords;
    private String avoid;
    private CarData carData;

    @JsonCreator
    public RoutingRequestData(
            @JsonProperty("locationsCoords") List<Coords> locationsCoords,
            @JsonProperty("avoid") String avoid,
            @JsonProperty("carData") CarData carData) {
        this.locationsCoords = locationsCoords;
        this.avoid = avoid;
        this.carData = carData;
    }

    public List<Coords> getLocationsCoords() {
        return locationsCoords;
    }

    public void setLocationsCoords(List<Coords> locationsCoords) {
        this.locationsCoords = locationsCoords;
    }

    public String getAvoid() {
        return avoid;
    }

    public void setAvoid(String avoid) {
        this.avoid = avoid;
    }

    public CarData getCarData() {
        return carData;
    }

    public void setCarData(CarData carData) {
        this.carData = carData;
    }

    @Override
    public String toString() {
        return "RoutingRequestData{" +
                "locationsCoords=" + locationsCoords +
                ", avoid='" + avoid + '\'' +
                ", carData=" + carData +
                '}';
    }
}
