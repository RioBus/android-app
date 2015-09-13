package com.tormentaLabs.riobus.map.bean;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.tormentaLabs.riobus.map.listener.MapComponentListener;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

/**
 * Used as basic structure for any map component.
 * @author limazix
 * @since 2.0
 * Created on 12/09/15.
 */
@EBean
public abstract class MapComponent {

    @RootContext
    public Context context;

    private String line;
    private MapComponentListener listener;
    private GoogleMap map;

    public Context getContext() {
        return context;
    }

    public String getLine() {
        return line;
    }

    public MapComponent setLine(String line) {
        this.line = line;
        return this;
    }

    public MapComponentListener getListener() {
        return listener;
    }

    public MapComponent setListener(MapComponentListener listener) {
        this.listener = listener;
        return this;
    }

    public GoogleMap getMap() {
        return map;
    }

    public MapComponent setMap(GoogleMap map) {
        this.map = map;
        return this;
    }

    public abstract void buildComponent();
}
