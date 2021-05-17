package com.example.electrowayfinal.utils.Routing.structures;

import java.util.ArrayList;
import java.util.List;

public class RoutingFinalResponse {
    Double totalTravelTime;
    Double totalTravelDistance;
    Double totalTravelPrice;
    List<LegData> legs;

    public RoutingFinalResponse() {
        totalTravelTime = 0.0;
        totalTravelDistance = 0.0;
        totalTravelPrice = 0.0;
        legs = new ArrayList<>();
    }

    public RoutingFinalResponse(Double totalTravelTime, Double totalTravelDistance, Double totalTravelPrice, List<LegData> legs) {
        this.totalTravelTime = totalTravelTime;
        this.totalTravelDistance = totalTravelDistance;
        this.totalTravelPrice = totalTravelPrice;
        this.legs = legs;
    }

    public Double getTotalTravelTime() {
        return totalTravelTime;
    }

    public void setTotalTravelTime(Double totalTravelTime) {
        this.totalTravelTime = totalTravelTime;
    }

    public Double getTotalTravelDistance() {
        return totalTravelDistance;
    }

    public void setTotalTravelDistance(Double totalTravelDistance) {
        this.totalTravelDistance = totalTravelDistance;
    }

    public Double getTotalTravelPrice() {
        return totalTravelPrice;
    }

    public void setTotalTravelPrice(Double totalTravelPrice) {
        this.totalTravelPrice = totalTravelPrice;
    }

    public List<LegData> getLegs() {
        return legs;
    }

    public void setLegs(List<LegData> legs) {
        this.legs = legs;
    }

    @Override
    public String toString() {
        return "RoutingFinalResponse{" +
                "\n\ttotalTravelTime=" + totalTravelTime +
                "\n\ttotalTravelDistance=" + totalTravelDistance +
                "\n\ttotalTravelPrice=" + totalTravelPrice +
                "\n\tlegs=" + legs +
                "\n}";
    }
}
