package com.tormentaLabs.riobus.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BusData {

    private List<Bus> buses = new ArrayList<Bus>();
    private List<Itinerary> itineraries = new ArrayList<Itinerary>();

    public BusData() {}

    public BusData(List<Bus> buses) {
        this.buses = buses;
    }

    public BusData(List<Bus> buses, List<Itinerary> itineraries) {
        this(buses);
        this.itineraries = itineraries;
    }

    public List<Bus> getBuses() {
        return buses;
    }

    public void setBuses(List<Bus> buses) {
        this.buses = buses;
    }

    public List<Itinerary> getItineraries() {
        return itineraries;
    }

    public void setItineraries(List<Itinerary> itineraries) {
        this.itineraries = itineraries;
    }
}
