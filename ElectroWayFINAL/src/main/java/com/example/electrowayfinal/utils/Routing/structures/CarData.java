package com.example.electrowayfinal.utils.Routing.structures;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CarData {
    private Long carId;
    private Double currentChargeInkW;

    public CarData() {
    }

    @JsonCreator
    public CarData(
            @JsonProperty("carId") Long carId,
            @JsonProperty("currentChargeInkW") Double currentChargeInkW) {
        this.carId = carId;
        this.currentChargeInkW = currentChargeInkW;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public Double getCurrentChargeInkW() {
        return currentChargeInkW;
    }

    public void setCurrentChargeInkW(Double currentChargeInkW) {
        this.currentChargeInkW = currentChargeInkW;
    }

    @Override
    public String toString() {
        return "CarData{" +
                "carId='" + carId + '\'' +
                ", currentChargeInkW=" + currentChargeInkW +
                '}';
    }
}
