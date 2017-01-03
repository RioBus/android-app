package com.tormentaLabs.riobus.map.controllers;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.tormentaLabs.riobus.common.models.Bus;
import com.tormentaLabs.riobus.common.models.Itinerary;

import java.util.ArrayList;
import java.util.List;

public class MapsController {

    private GoogleMap mMap;
    private MarkerController markerController;
    private ItineraryController itineraryController;
    private List<Marker> markers = new ArrayList<>();
    private LatLngBounds.Builder boundsBuilder;
    private static final int BOUNDS_PADDING = 200;

    public MapsController(GoogleMap mMap) {
        this.mMap = mMap;
        boundsBuilder = new LatLngBounds.Builder();
        markerController = new MarkerController(mMap, boundsBuilder);
        itineraryController = new ItineraryController(mMap, boundsBuilder);
    }

    public void addBuses(List<Bus> items) {
        markerController.addBuses(items);
    }

    public void addItinerary(Itinerary itinerary) {
        itineraryController.addItinerary(itinerary);
    }
}
