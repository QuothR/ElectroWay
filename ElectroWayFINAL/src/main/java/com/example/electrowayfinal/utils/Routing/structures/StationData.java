package com.example.electrowayfinal.utils.Routing.structures;

public class StationData {
    private Long stationId;
    private Coords coords;
    private Double priceKw;
    private Double chargingSpeedKw;
    // Pentru Testing, ca sa ne fie mai usor sa vedem ce statii a ales.
    private String address;

    public StationData() {
    }

    public StationData(Long stationId, Coords coords, Double priceKw, Double chargingSpeedKw, String address) {
        this.stationId = stationId;
        this.coords = coords;
        this.priceKw = priceKw;
        this.chargingSpeedKw = chargingSpeedKw;
        this.address = address;
    }

    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public Coords getCoords() {
        return coords;
    }

    public void setCoords(Coords coords) {
        this.coords = coords;
    }

    public Double getPriceKw() {
        return priceKw;
    }

    public void setPriceKw(Double priceKw) {
        this.priceKw = priceKw;
    }

    public Double getChargingSpeedKw() {
        return chargingSpeedKw;
    }

    public void setChargingSpeedKw(Double chargingSpeedKw) {
        this.chargingSpeedKw = chargingSpeedKw;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "StationData{" +
                "stationId=" + stationId +
                ", coords=" + coords +
                ", priceKw=" + priceKw +
                ", chargingSpeedKw=" + chargingSpeedKw +
                ", address='" + address + '\'' +
                '}';
    }
}
