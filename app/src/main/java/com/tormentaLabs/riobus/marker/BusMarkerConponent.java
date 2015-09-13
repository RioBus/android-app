package com.tormentaLabs.riobus.marker;

import android.app.Activity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tormentaLabs.riobus.R;
import com.tormentaLabs.riobus.map.bean.MapComponent;
import com.tormentaLabs.riobus.marker.adapter.BusInfoWindowAdapter;
import com.tormentaLabs.riobus.marker.model.BusModel;
import com.tormentaLabs.riobus.marker.service.BusService;
import com.tormentaLabs.riobus.marker.utils.MarkerUtils;
import com.tormentaLabs.riobus.utils.RioBusUtils;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.rest.RestService;
import org.joda.time.DateTime;
import org.joda.time.Minutes;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Class used to manage the buses of some given line to the map as a component.
 * @author limazix
 * @since 2.0
 * Created on 02/09/15.
 */
@EBean
public class BusMarkerConponent extends MapComponent {

    private static final String TAG = BusMarkerConponent.class.getName();
    public static final int BOUNDS_PADDING = 50;

    @RestService
    BusService busService;

    private LatLngBounds.Builder boundsBuilder;

    @Override
    public void buildComponent() {
        boundsBuilder = new LatLngBounds.Builder();
        getMap().setInfoWindowAdapter(new BusInfoWindowAdapter((Activity) getContext()));
        getBusesByLine();
    }

    /**
     * Used to aceess the server and get the list of buses of some given lie
     */
    @Background
    void getBusesByLine() {
        List<BusModel> buses = busService.getBusesByLine(getLine());
        if (buses != null) addMarkers(buses);
        getListener().onComponentMapReady();
    }

    /**
     * Used to add a marker for each buson map
     * @param buses List of buses
     */
    @UiThread
    void addMarkers(List<BusModel> buses) {
        for (BusModel bus : buses)
            getMap().addMarker(getMarker(bus));
        getMap().moveCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), BOUNDS_PADDING));
    }

    /**
     * Used to build the bus marker based on its features
     * @param bus
     * @return MarkerOptions the bus marker
     */
    private MarkerOptions getMarker(BusModel bus) {
        MarkerOptions options = MarkerUtils.createMarker(bus.getLatitude(), bus.getLongitude());
        options.icon(getIcon(bus.getTimeStamp()));
        options.snippet(bus.toString());
        boundsBuilder.include(options.getPosition());
        return options;
    }

    /**
     * Used to get the bus marker icon based on its last update position time
     * @param timestamp Bus last update position time
     * @return BitmapDescriptor bus marker icon
     */
    private BitmapDescriptor getIcon(String timestamp) {
        BitmapDescriptor bitmap = null;
        try {
            Date date = RioBusUtils.parseStringToDate(timestamp);

            DateTime current = new DateTime(Calendar.getInstance());
            DateTime last = new DateTime(date);
            int diff = Minutes.minutesBetween(last, current).getMinutes();

            if (diff >= 5 && diff < 10) {
                bitmap = BitmapDescriptorFactory
                        .fromResource(R.drawable.bus_yellow);
            } else if (diff >= 10) {
                bitmap = BitmapDescriptorFactory
                        .fromResource(R.drawable.bus_red);
            } else {
                bitmap = BitmapDescriptorFactory
                        .fromResource(R.drawable.bus_green);
            }
        } catch (ParseException e) {
            Log.e(TAG, e.getMessage());
        }
        return bitmap;
    }
}
