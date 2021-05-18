package com.example.electrowayfinal.utils.Routing.structures;

public class Consumption {
    private Integer speed;
    private Double consumptionKWh;

    public Consumption() {
    }

    public Consumption(Integer speed, Double consumptionKWh) {
        this.speed = speed;
        this.consumptionKWh = consumptionKWh;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Double getConsumptionKWh() {
        return consumptionKWh;
    }

    public void setConsumptionKWh(Double consumptionKWh) {
        this.consumptionKWh = consumptionKWh;
    }

    @Override
    public String toString() {
        return "Consumption{" +
                "speed=" + speed +
                ", consumptionKWh=" + consumptionKWh +
                '}';
    }
}
