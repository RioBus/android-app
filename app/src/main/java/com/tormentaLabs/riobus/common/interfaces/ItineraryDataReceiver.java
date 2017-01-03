package com.tormentaLabs.riobus.common.interfaces;

import com.tormentaLabs.riobus.common.models.Itinerary;

public interface ItineraryDataReceiver {
    void onItineraryReceived(Itinerary itinerary);
}