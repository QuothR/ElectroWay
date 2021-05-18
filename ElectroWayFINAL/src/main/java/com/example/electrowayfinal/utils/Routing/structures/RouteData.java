package com.example.electrowayfinal.utils.Routing.structures;

import java.util.List;

public class RouteData {
    private List<Coords> locationsCoords;
    private String avoid;
    private Car car;
    private Double currentChargeInkWh;
    private List<Consumption> constantSpeedConsumptionInkWhPerHundredkm;

    public RouteData() {
    }

    public RouteData(RoutingRequestData routingRequestData) {
        this.locationsCoords = routingRequestData.getLocationsCoords();
        this.avoid = routingRequestData.getAvoid();
    }

    public RouteData(RouteData obj) {
        this.locationsCoords = obj.getLocationsCoords();
        this.avoid = obj.getAvoid();
        this.car = obj.getCar();
        this.currentChargeInkWh = obj.getCurrentChargeInkWh();
        this.constantSpeedConsumptionInkWhPerHundredkm = obj.getConstantSpeedConsumptionInkWhPerHundredkm();
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

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Double getCurrentChargeInkWh() {
        return currentChargeInkWh;
    }

    public void setCurrentChargeInkWh(Double currentChargeInkWh) {
        this.currentChargeInkWh = currentChargeInkWh;
    }

    public List<Consumption> getConstantSpeedConsumptionInkWhPerHundredkm() {
        return constantSpeedConsumptionInkWhPerHundredkm;
    }

    public void setConstantSpeedConsumptionInkWhPerHundredkm(List<Consumption> constantSpeedConsumptionInkWhPerHundredkm) {
        this.constantSpeedConsumptionInkWhPerHundredkm = constantSpeedConsumptionInkWhPerHundredkm;
    }

    @Override
    public String toString() {
        return "RouteData{" +
                "locationsCoords=" + locationsCoords +
                ", avoid='" + avoid + '\'' +
                ", car=" + car +
                ", currentChargeInkWh=" + currentChargeInkWh +
                ", constantSpeedConsumptionInkWhPerHundredkm=" + constantSpeedConsumptionInkWhPerHundredkm +
                '}';
    }
}
