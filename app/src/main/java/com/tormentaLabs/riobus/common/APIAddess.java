package com.tormentaLabs.riobus.common;

public interface APIAddess {
    String BASE = "http://rest.riob.us/";
    String BUS_SEARCH = BASE + "v3/search/%s";
    String LINE_SEARCH = BASE + "v3/itinerary";
    String ITINERARY_SEARCH = BASE + "v3/itinerary/%s";
}
