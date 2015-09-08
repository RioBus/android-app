package com.tormentaLabs.riobus.map.listener;

/**
 * Created by limazix on 01/09/15.
 */
public interface MapComponentListener {
    public void onComponentMapReady();
    public void onComponentMapError(Exception error);
}
