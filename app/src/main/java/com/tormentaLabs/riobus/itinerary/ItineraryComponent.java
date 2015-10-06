package com.tormentaLabs.riobus.itinerary;

import android.util.Log;

import com.tormentaLabs.riobus.itinerary.model.ItineraryModel;
import com.tormentaLabs.riobus.itinerary.service.ItineraryService;
import com.tormentaLabs.riobus.map.bean.MapComponent;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.rest.RestService;

/**
 * Class used to manage the itinarary of some given line to the map as a component.
 * @author limazix
 * @since 2.1
 * Created on 05/09/15.
 */
@EBean
public class ItineraryComponent extends MapComponent {

    private static final String TAG = ItineraryComponent.class.getName();
    @RestService
    ItineraryService itineraryService;

    @Background
    void getItineraries(){
        ItineraryModel itinerary = itineraryService.getItinarary(getLine());
        Log.e(TAG, itinerary.getLine());
    }

    @Override
    public void buildComponent() {
        getItineraries();
    }

    @Override
    public void removeComponent() {

    }
}
