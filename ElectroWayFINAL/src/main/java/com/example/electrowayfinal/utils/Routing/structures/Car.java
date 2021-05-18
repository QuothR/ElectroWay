package com.example.electrowayfinal.utils.Routing.structures;

public class Car {
    private String model;
    private Long year;
    private Double maxChargeInkWh;
    private Double chargingSpeed;
    private String plugType;
    private Long vehicleMaxSpeed;
    private Double auxiliaryInkW;

    public Car() {
    }

    public Car(String model, Long year, Double maxChargeInkWh, Double chargingSpeed, String plugType, Long vehicleMaxSpeed, Double auxiliaryInkW) {
        this.model = model;
        this.year = year;
        this.maxChargeInkWh = maxChargeInkWh;
        this.chargingSpeed = chargingSpeed;
        this.plugType = plugType;
        this.vehicleMaxSpeed = vehicleMaxSpeed;
        this.auxiliaryInkW = auxiliaryInkW;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Long getYear() {
        return year;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    public Double getMaxChargeInkWh() {
        return maxChargeInkWh;
    }

    public void setMaxChargeInkWh(Double maxChargeInkWh) {
        this.maxChargeInkWh = maxChargeInkWh;
    }

    public Double getChargingSpeed() {
        return chargingSpeed;
    }

    public void setChargingSpeed(Double chargingSpeed) {
        this.chargingSpeed = chargingSpeed;
    }

    public String getPlugType() {
        return plugType;
    }

    public void setPlugType(String plugType) {
        this.plugType = plugType;
    }

    public Long getVehicleMaxSpeed() {
        return vehicleMaxSpeed;
    }

    public void setVehicleMaxSpeed(Long vehicleMaxSpeed) {
        this.vehicleMaxSpeed = vehicleMaxSpeed;
    }

    public Double getAuxiliaryInkW() {
        return auxiliaryInkW;
    }

    public void setAuxiliaryInkW(Double auxiliaryInkW) {
        this.auxiliaryInkW = auxiliaryInkW;
    }

    @Override
    public String toString() {
        return "Car{" +
                "model='" + model + '\'' +
                ", year=" + year +
                ", maxChargeInkWh=" + maxChargeInkWh +
                ", chargingSpeed=" + chargingSpeed +
                ", plugType='" + plugType + '\'' +
                ", vehicleMaxSpeed=" + vehicleMaxSpeed +
                ", auxiliaryInkW=" + auxiliaryInkW +
                '}';
    }
}
