package com.tormentaLabs.riobus.common.dao;


import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;
import com.tormentaLabs.riobus.common.models.Line;

import java.util.ArrayList;
import java.util.List;

public class FavoritesDAO {

    private static final String TAG = FavoritesDAO.class.getName();
    private static final String KEYBASE = "favorite";
    private DB db;

    public FavoritesDAO(Context context) throws SnappydbException {
        db = DBFactory.open(context);
    }

    public boolean isFavorite(String key) throws SnappydbException {
        return db.exists(getPath(key));
    }

    public void addFavorite(String key, Line line) throws SnappydbException {
        String tmp = new Gson().toJson(line);
        db.put(getPath(key), tmp);
    }

    private Line getFavorite(String key) throws SnappydbException {
        String tmp = db.get(key);
        return new Gson().fromJson(tmp, Line.class);
    }

    public void removeFavorite(String key) throws SnappydbException {
        db.del(getPath(key));
    }

    public List<Line> getFavorites() throws SnappydbException {
        String[] keys = db.findKeys(KEYBASE);
        List<Line> lines = new ArrayList<>();
        for (String key: keys) lines.add(getFavorite(key));
        return lines;
    }

    private String getPath(String key) {
        return KEYBASE+":"+key;
    }

    public void close() throws SnappydbException {
        db.close();
    }
}
