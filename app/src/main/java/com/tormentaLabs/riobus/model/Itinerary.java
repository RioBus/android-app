package com.tormentaLabs.riobus.model;

import java.util.List;

public class Itinerary {

    private String line;
    private String description;
    private String agency;
    private String keywords;
    private List<Spot> spots;

    public Itinerary() {}

    public Itinerary(String line, String description, String agency, String keywords, List<Spot> spots) {
        this.line = line;
        this.description = description;
        this.agency = agency;
        this.keywords = keywords;
        this.spots = spots;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public List<Spot> getSpots() {
        return spots;
    }

    public void setSpots(List<Spot> spots) {
        this.spots = spots;
    }

    @Override
    public String toString() {
        return "Object(Itinerary){" +
                "line: " + line +
                ", description: '" + description + "'" +
                ", agency: " + agency +
                ", keywords: " + keywords +
                "}";
    }
}
