package com.server.serverSide.model;

public class SpeedConsumptionRate {
    private Integer speed;
    private Double consumptionRate;

    public SpeedConsumptionRate() { }

    public SpeedConsumptionRate(Integer speed, Double consumptionRate) {
        this.speed = speed;
        this.consumptionRate = consumptionRate;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Double getConsumptionRate() {
        return consumptionRate;
    }

    public void setConsumptionRate(Double consumptionRate) {
        this.consumptionRate = consumptionRate;
    }
}
