package com.tormentaLabs.riobus.bus;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tormentaLabs.riobus.R;
import com.tormentaLabs.riobus.bus.model.BusModel;
import com.tormentaLabs.riobus.bus.service.BusService;
import com.tormentaLabs.riobus.map.listener.MapComponentListener;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.rest.RestService;
import org.joda.time.DateTime;
import org.joda.time.Minutes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Class used to add the buses of some given line to the map as a component
 *
 * @author limazix
 * @since 2.0
 * Created on 02/09/15.
 */
@EBean
public class BusMapConponent {

    private static final String TAG = BusMapConponent.class.getName();
    public static final int BOUNDS_PADDING = 50;

    @RootContext
    Context context;

    @RestService
    BusService busService;

    private String line;
    private MapComponentListener listener;
    private GoogleMap map;
    private LatLngBounds.Builder boundsBuilder;

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public MapComponentListener getListener() {
        return listener;
    }

    public void setListener(MapComponentListener listener) {
        this.listener = listener;
    }

    public GoogleMap getMap() {
        return map;
    }

    public void setMap(GoogleMap map) {
        this.map = map;
        boundsBuilder = new LatLngBounds.Builder();
    }

    public void buildComponent() {
        boundsBuilder = new LatLngBounds.Builder();
        getBusesByLine();
    }

    /**
     * Used to aceess the server and get the list of buses of some given lie
     */
    @Background
    void getBusesByLine() {
        List<BusModel> buses = busService.getBusesByLine(line);

        if (buses != null)
            addMarkers(buses);

        listener.onComponentMapReady();
    }

    /**
     * Used to add a marker for each buson map
     *
     * @param buses List of buses
     */
    @UiThread
    void addMarkers(List<BusModel> buses) {
        for (BusModel bus : buses)
            map.addMarker(getMarker(bus));
        map.moveCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), BOUNDS_PADDING));
    }

    /**
     * Used to build the bus marker based on its features
     *
     * @param bus
     * @return MarkerOptions the bus marker
     */
    private MarkerOptions getMarker(BusModel bus) {
        MarkerOptions options = new MarkerOptions();
        LatLng position = new LatLng(bus.getLatitude(), bus.getLongitude());
        options.position(position);
        options.icon(getIcon(bus.getTimeStamp()));
        boundsBuilder.include(position);
        return options;
    }


    private BitmapDescriptor getIcon(String timestamp) {
        BitmapDescriptor bitmap = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date = dateFormat.parse(timestamp);

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
