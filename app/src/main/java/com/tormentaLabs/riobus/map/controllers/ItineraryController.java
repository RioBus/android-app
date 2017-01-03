package com.tormentaLabs.riobus.map.controllers;

import android.graphics.Color;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.PolylineOptions;
import com.tormentaLabs.riobus.common.models.Itinerary;

import java.util.ArrayList;
import java.util.List;

class ItineraryController {

    private static final int ITINERARY_WIDTH = 20;

    private GoogleMap mMap;
    private List<Marker> markers = new ArrayList<>();
    private LatLngBounds.Builder boundsBuilder;
    private static final int BOUNDS_PADDING = 200;

    ItineraryController(GoogleMap mMap, LatLngBounds.Builder boundsBuilder) {
        this.mMap = mMap;
        this.boundsBuilder = boundsBuilder;
    }

    void addItinerary(Itinerary itinerary) {
        PolylineOptions options = new PolylineOptions();
        options.width(ITINERARY_WIDTH).color(Color.BLUE);
        for (Itinerary.Spot spot : itinerary.getSpots()) {
            LatLng tmp = new LatLng(spot.getLatitude(), spot.getLongitude());
            options.add(tmp);
        }
        mMap.addPolyline(options);
    }
}
