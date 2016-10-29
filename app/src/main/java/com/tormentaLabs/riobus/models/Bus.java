package com.tormentaLabs.riobus.models;

import java.util.Date;

public class Bus {

    private Date timestamp;
    private String order;
    private String line;
    private double latitude;
    private double longitude;
    private double speed;
    private String sense;

    public Bus() {}

    public Bus(Date timestamp, String order, String line, double latitude, double longitude, double speed, String sense) {
        this.timestamp = timestamp;
        this.order = order;
        this.line = line;
        this.latitude = latitude;
        this.longitude = longitude;
        this.speed = speed;
        this.sense = sense;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
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

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public String getSense() {
        return sense;
    }

    public void setSense(String sense){
        this.sense = sense;
    }

    @Override
    public String toString() {
        return "Object(Bus){" +
                "timestamp: " + timestamp +
                ", order: '" + order + "'" +
                ", line: " + line +
                ", latitude: " + latitude +
                ", longitude: " + longitude +
                ", speed: " + speed +
                ", sense: " + sense +
                "}";
    }
}
