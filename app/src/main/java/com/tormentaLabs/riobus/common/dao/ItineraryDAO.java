package com.tormentaLabs.riobus.common.dao;

import android.content.Context;

import com.google.gson.Gson;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;
import com.tormentaLabs.riobus.common.models.Itinerary;

public class ItineraryDAO {

    private static final String TAG = ItineraryDAO.class.getName();
    private static final String KEYBASE = "itinerary";
    private DB db;

    public ItineraryDAO(Context context) throws SnappydbException {
        db = DBFactory.open(context);
    }

    public void addItinerary(String key, Itinerary itinerary) throws SnappydbException {
        String tmp = new Gson().toJson(itinerary);
        db.put(getPath(key), tmp);
    }

    public void removeItinerary(String key) throws SnappydbException {
        db.del(getPath(key));
    }

    public Itinerary getItinerary(String line) throws SnappydbException {
        String tmp = db.get(line);
        return new Gson().fromJson(tmp, Itinerary.class);
    }

    private String getPath(String key) {
        return KEYBASE+":"+key;
    }

    public void close() throws SnappydbException {
        db.close();
    }
}
