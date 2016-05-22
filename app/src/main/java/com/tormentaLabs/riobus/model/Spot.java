package com.tormentaLabs.riobus.model;

public class Spot {

    private double latitude;
    private double longitude;
    private String returning;

    public Spot() {}

    public Spot(double latitude, double longitude, String returning) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.returning = returning;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getReturning() {
        return returning;
    }

    public void setReturning(String returning) {
        this.returning = returning;
    }

    @Override
    public String toString() {
        return "Object(Bus){" +
                "latitude: " + latitude +
                ", longitude: " + longitude +
                ", returning: " + returning +
                "}";
    }
}
