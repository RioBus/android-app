package com.tormentaLabs.riobus.map.controllers;

import android.graphics.Color;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.PolylineOptions;
import com.tormentaLabs.riobus.common.models.Itinerary;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class ItineraryController {

    private static final int ITINERARY_WIDTH = 20;

    private GoogleMap mMap;
    private List<Marker> markers = new ArrayList<>();
    private static final int BOUNDS_PADDING = 200;

    ItineraryController(GoogleMap mMap) {
        this.mMap = mMap;
    }

    void addItinerary(Itinerary itinerary) {
        PolylineOptions options = new PolylineOptions();
        options.width(ITINERARY_WIDTH).color(getRandomColor());
        for (Itinerary.Spot spot : itinerary.getSpots()) {
            LatLng tmp = new LatLng(spot.getLatitude(), spot.getLongitude());
            options.add(tmp);
        }
        mMap.addPolyline(options);
    }

    private int getRandomColor() {
        Random random = new Random();
        int r = random.nextInt(256), g = random.nextInt(256), b = random.nextInt(256);
        return (r <= (g + b) && g <= (r + b)) ? Color.argb(255, r, g, b) : getRandomColor();
    }
}
