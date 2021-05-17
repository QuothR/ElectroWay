package com.example.electrowayfinal.utils.Routing.structures;

import java.util.List;

public class LegData {
    private Long stationId;
    private Double priceKw;
    private Double currentChargeInkWhAfterRecharge;
    private List<Coords> points;
    private String address;

    public LegData() {
        stationId = null;
        priceKw = null;
        currentChargeInkWhAfterRecharge = null;
        points = null;
        address = null;
    }

    public LegData(Long stationId, Double priceKw, Double currentChargeInkWhAfterRecharge, List<Coords> points, String address) {
        this.stationId = stationId;
        this.priceKw = priceKw;
        this.currentChargeInkWhAfterRecharge = currentChargeInkWhAfterRecharge;
        this.points = points;
        this.address = address;
    }

    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public Double getCurrentChargeInkWhAfterRecharge() {
        return currentChargeInkWhAfterRecharge;
    }

    public void setCurrentChargeInkWhAfterRecharge(Double currentChargeInkWhAfterRecharge) {
        this.currentChargeInkWhAfterRecharge = currentChargeInkWhAfterRecharge;
    }

    public List<Coords> getPoints() {
        return points;
    }

    public void setPoints(List<Coords> points) {
        this.points = points;
    }

    public Double getPriceKw() {
        return priceKw;
    }

    public void setPriceKw(Double priceKw) {
        this.priceKw = priceKw;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "LegData{" +
                "stationId=" + stationId +
                ", priceKw=" + priceKw +
                ", currentChargeInkWhAfterRecharge=" + currentChargeInkWhAfterRecharge +
                ", points=" + points +
                ", address='" + address + '\'' +
                '}';
    }
}
