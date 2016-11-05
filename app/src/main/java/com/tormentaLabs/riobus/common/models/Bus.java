package com.tormentaLabs.riobus.common.models;

import java.util.Date;

public class Bus {

    private String line;
    private String order;
    private double speed;
    private int direction;
    private double latitude;
    private double longitude;
    private String sense;
    private Date timeStamp;

    public Bus() {}

    public Bus(String line, String order, double speed, int direction, double latitude, double longitude, Date timestamp, String sense) {
        this.line = line;
        this.order = order;
        this.speed = speed;
        this.direction = direction;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timeStamp = timestamp;
        this.sense = sense;
    }

    public int getDirection() {
        return direction;
    }

    public Date getTimestamp() {
        return timeStamp;
    }

    public String getOrder() {
        return order;
    }

    public String getLine() {
        return line;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getSpeed() {
        return speed;
    }

    public String getSense() {
        return sense;
    }
}
