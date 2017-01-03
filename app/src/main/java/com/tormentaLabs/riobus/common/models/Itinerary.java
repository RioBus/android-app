package com.tormentaLabs.riobus.common.models;

public class Itinerary {

    private String line;
    private String description;
    private String agency;
    private String keywords;
    private Spot[] spots;

    public Itinerary() {}

    public Itinerary(String line, String description, String agency, String keywords, Spot[] spots) {
        this.line = line;
        this.description = description;
        this.agency = agency;
        this.keywords = keywords;
        this.spots = spots;
    }

    public String getLine() {
        return line;
    }

    public String getDescription() {
        return description;
    }

    public String getAgency() {
        return agency;
    }

    public String getKeywords() {
        return keywords;
    }

    public Spot[] getSpots() {
        return spots;
    }

    public class Spot {

        private float latitude;
        private float longitude;
        private boolean returning;

        Spot(float latitude, float longitude, boolean returning) {
            this.latitude = latitude;
            this.longitude = longitude;
            this.returning = returning;
        }

        public float getLatitude() {
            return latitude;
        }

        public float getLongitude() {
            return longitude;
        }

        public boolean isReturning() {
            return returning;
        }
    }
}
