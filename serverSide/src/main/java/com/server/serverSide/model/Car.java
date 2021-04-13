package com.server.serverSide.model;

import java.util.List;

public class Car {
    private Integer vehicleMaxSpeed;
    private List<SpeedConsumptionRate> constantSpeedConsumptionInkWhPerHundredkm;
    private Integer currentChargeInkWh;
    private Integer maxChargeInkWh;
    private Integer auxiliaryInkW;

    public Car() { }

    public Car(Integer vehicleMaxSpeed, List<SpeedConsumptionRate> constantSpeedConsumptionInkWhPerHundredkm, Integer currentChargeInkWh, Integer maxChargeInkWh, Integer auxiliaryInkW) {
        this.vehicleMaxSpeed = vehicleMaxSpeed;
        this.constantSpeedConsumptionInkWhPerHundredkm = constantSpeedConsumptionInkWhPerHundredkm;
        this.currentChargeInkWh = currentChargeInkWh;
        this.maxChargeInkWh = maxChargeInkWh;
        this.auxiliaryInkW = auxiliaryInkW;
    }

    public Integer getVehicleMaxSpeed() {
        return vehicleMaxSpeed;
    }

    public void setVehicleMaxSpeed(Integer vehicleMaxSpeed) {
        this.vehicleMaxSpeed = vehicleMaxSpeed;
    }

    public List<SpeedConsumptionRate> getConstantSpeedConsumptionInkWhPerHundredkm() {
        return constantSpeedConsumptionInkWhPerHundredkm;
    }

    public void setConstantSpeedConsumptionInkWhPerHundredkm(List<SpeedConsumptionRate> constantSpeedConsumptionInkWhPerHundredkm) {
        this.constantSpeedConsumptionInkWhPerHundredkm = constantSpeedConsumptionInkWhPerHundredkm;
    }

    public Integer getCurrentChargeInkWh() {
        return currentChargeInkWh;
    }

    public void setCurrentChargeInkWh(Integer currentChargeInkWh) {
        this.currentChargeInkWh = currentChargeInkWh;
    }

    public Integer getMaxChargeInkWh() {
        return maxChargeInkWh;
    }

    public void setMaxChargeInkWh(Integer maxChargeInkWh) {
        this.maxChargeInkWh = maxChargeInkWh;
    }

    public Integer getAuxiliaryInkW() {
        return auxiliaryInkW;
    }

    public void setAuxiliaryInkW(Integer auxiliaryInkW) {
        this.auxiliaryInkW = auxiliaryInkW;
    }

    @Override
    public String toString() {
        return "Car{" +
                "vehicleMaxSpeed=" + vehicleMaxSpeed +
                ", constantSpeedConsumptionInkWhPerHundredkm=" + constantSpeedConsumptionInkWhPerHundredkm +
                ", currentChargeInkWh=" + currentChargeInkWh +
                ", maxChargeInkWh=" + maxChargeInkWh +
                ", auxiliaryInkW=" + auxiliaryInkW +
                '}';
    }
}
