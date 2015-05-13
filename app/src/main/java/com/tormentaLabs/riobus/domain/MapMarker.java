package com.tormentaLabs.riobus.domain;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.tormentaLabs.riobus.R;

import org.joda.time.DateTime;
import org.joda.time.Minutes;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MapMarker {

    private Context context;

    public MapMarker(Context context){
        this.context = context;
    }

    public void addMarker(GoogleMap map, List<Bus> buses) {
            for (Bus bus : buses) {
                map.addMarker(getMarker(bus));
            }
    }

    private MarkerOptions getMarker(Bus bus) {
        MarkerOptions marker = new MarkerOptions();
        LatLng position = new LatLng(bus.getLatitude(), bus.getLongitude());
        marker.position(position);
        marker.icon(getIcon(bus.getTimestamp()));
        marker.snippet(new Gson().toJson(bus));
        return marker;
    }

    private BitmapDescriptor getIcon(Date data) {
        BitmapDescriptor icon;

        DateTime current = new DateTime(Calendar.getInstance());
        DateTime last = new DateTime(data);

        int diff =  Minutes.minutesBetween(last, current).getMinutes();

        if(diff <= 5) {
           icon = BitmapDescriptorFactory
                .fromResource(R.drawable.bus_green);
        } else if(diff > 10 ) {
           icon = BitmapDescriptorFactory
                .fromResource(R.drawable.bus_red);
        } else {
           icon = BitmapDescriptorFactory
                .fromResource(R.drawable.bus_yellow);
        }
        return icon;
    }

}
