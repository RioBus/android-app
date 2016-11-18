package com.tormentaLabs.riobus.map;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tormentaLabs.riobus.R;
import com.tormentaLabs.riobus.common.models.Bus;

import org.joda.time.DateTime;
import org.joda.time.Minutes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

class MapsController {

    private GoogleMap mMap;
    private List<Marker> markers = new ArrayList<>();

    MapsController(GoogleMap mMap) {
        this.mMap = mMap;
    }

    void addBuses(List<Bus> items) {
        removeBusMarkers();
        for (Bus bus: items) {
            MarkerOptions markerOptions = createBusMarker(bus);
            Marker m = mMap.addMarker(markerOptions);
            markers.add(m);
        }
    }

    private MarkerOptions createBusMarker(Bus bus) {
        return new MarkerOptions()
                .position(new LatLng(bus.getLatitude(), bus.getLongitude()))
                .icon(getIcon(bus.getTimestamp()))
                .title(bus.getOrder());
    }

    private void removeBusMarkers() {
        for (Marker m : markers) m.remove();
        markers.clear();
    }

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
