package com.server.serverSide.model;

import java.util.List;

public class RouteData {
    private List<Coords> locationsCoords;
    private Boolean computeBestOrder;
    private String routeType;
    private String avoid;
    private String travelMode;
    private Car car;

    public RouteData() { }

    public RouteData(List<Coords> locationsCoords, Boolean computeBestOrder, String routeType, String avoid, String travelMode, Car car) {
        this.locationsCoords = locationsCoords;
        this.computeBestOrder = computeBestOrder;
        this.routeType = routeType;
        this.avoid = avoid;
        this.travelMode = travelMode;
        this.car = car;
    }

    public List<Coords> getLocationsCoords() {
        return locationsCoords;
    }

    public void setLocationsCoords(List<Coords> locationsCoords) {
        this.locationsCoords = locationsCoords;
    }

    public Boolean getComputeBestOrder() {
        return computeBestOrder;
    }

    public void setComputeBestOrder(Boolean computeBestOrder) {
        this.computeBestOrder = computeBestOrder;
    }

    public String getRouteType() {
        return routeType;
    }

    public void setRouteType(String routeType) {
        this.routeType = routeType;
    }

    public String getAvoid() {
        return avoid;
    }

    public void setAvoid(String avoid) {
        this.avoid = avoid;
    }

    public String getTravelMode() {
        return travelMode;
    }

    public void setTravelMode(String travelMode) {
        this.travelMode = travelMode;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
