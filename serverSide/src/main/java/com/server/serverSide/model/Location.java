package com.server.serverSide.model;

public class Location {
    private String name;
    private String address;
    private String country;
    private String municipality;
    private Double lat;
    private Double lon;

    public Location(String name, String address, String country, String municipality, Double lat, Double lon) {
        this.name = name;
        this.address = address;
        this.country = country;
        this.municipality = municipality;
        this.lat = lat;
        this.lon = lon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    @Override
    public String toString() {
        return "Location{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", country='" + country + '\'' +
                ", municipality='" + municipality + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                '}';
    }
}
