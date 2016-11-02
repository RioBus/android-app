package com.tormentaLabs.riobus.map;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tormentaLabs.riobus.R;

import org.joda.time.DateTime;
import org.joda.time.Minutes;

import java.util.Calendar;
import java.util.Date;

class MapsController {

    private GoogleMap mMap;

    MapsController(GoogleMap mMap) {
        this.mMap = mMap;
    }

    GoogleMap getMap() {
        return mMap;
    }

    void addBuses() {}

    MarkerOptions createBusMarker() {
        return null;
    }

    void removeMarkers() {}

    void updateUserMarker() {}

    private BitmapDescriptor getIcon(Date data) {
        DateTime current = new DateTime(Calendar.getInstance());
        DateTime last = new DateTime(data);
        int diff =  Minutes.minutesBetween(last, current).getMinutes();

        BitmapDescriptor bitmap;

        if(diff >= 5 && diff < 10 ) {
            bitmap = BitmapDescriptorFactory
                    .fromResource(R.drawable.bus_yellow);
        }  else if(diff >= 10 ) {
            bitmap = BitmapDescriptorFactory
                    .fromResource(R.drawable.bus_red);
        } else {
            bitmap = BitmapDescriptorFactory
                    .fromResource(R.drawable.bus_green);
        }
        return bitmap;
    }
}
