package com.server.serverSide.model;

public class Station {

    private Location location;
    private int review;
    private String id;

    public Station(Location location, int review, String id) {
        this.location = location;
        this.review = review;
        this.id = id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
