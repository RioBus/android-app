package com.tormentaLabs.riobus.bus;

import android.content.Context;
import android.support.annotation.UiThread;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.tormentaLabs.riobus.bus.model.BusModel;
import com.tormentaLabs.riobus.bus.service.BusService;
import com.tormentaLabs.riobus.map.listener.MapComponentListener;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.rest.RestService;

import java.util.List;

/**
 * Created by limazix on 02/09/15.
 */
@EBean
public class BusMapConponent {

    private static final String TAG = BusMapConponent.class.getName();

    @RootContext
    Context context;

    @RestService
    BusService busService;

    private String line;

    private MapComponentListener listener;
    private GoogleMap map;

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
    }

    public void buildComponent() {
        getBusesByLine();
    }

    @Background
    void getBusesByLine() {
        List<BusModel> buses = busService.getBusesByLine(line);
        if(buses != null) {
            for(BusModel bus : buses) {
                Log.d(TAG, bus.getOrder());
            }
        }
        listener.onComponentMapReady();
    }
}
