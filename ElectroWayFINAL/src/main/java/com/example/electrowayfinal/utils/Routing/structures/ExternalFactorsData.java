package com.example.electrowayfinal.utils.Routing.structures;

public class ExternalFactorsData {
    private Double temperature;
    private Double wind;
    private Double direction;

    // Alte date pentru calculul final.
    public int badConditionPercent;
    public Double bearing;

    public ExternalFactorsData() {
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getWind() {
        return wind;
    }

    public void setWind(Double wind) {
        this.wind = wind;
    }

    public Double getDirection() {
        return direction;
    }

    public void setDirection(Double direction) {
        this.direction = direction;
    }

    public int getBadConditionPercent() {
        return badConditionPercent;
    }

    public void setBadConditionPercent(int badConditionPercent) {
        this.badConditionPercent = badConditionPercent;
    }

    public Double getBearing() {
        return bearing;
    }

    public void setBearing(Double bearing) {
        this.bearing = bearing;
    }

    @Override
    public String toString() {
        return "ExternalFactorsData{" +
                "\n\ttemperature=" + temperature +
                "\n\twind=" + wind +
                "\n\tdirection=" + direction +
                "\n\tbadConditionPercent=" + badConditionPercent +
                "\n\tbearing=" + bearing +
                "\n}";
    }
}
