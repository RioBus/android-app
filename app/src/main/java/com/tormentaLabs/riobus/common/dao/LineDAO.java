package com.tormentaLabs.riobus.common.dao;


import android.content.Context;
import android.util.Log;

import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;
import com.tormentaLabs.riobus.common.models.Line;

import java.util.ArrayList;
import java.util.List;

public class LineDAO {

    private static final String TAG = LineDAO.class.getName();
    private static final String KEYBASE = "line";
    private DB db;

    public LineDAO(Context context) throws SnappydbException {
        db = DBFactory.open(context);
    }

    public void addLine(String key, Line line) throws SnappydbException {
        db.put(getPath(key), line);
    }

    public void removeLine(String key) throws SnappydbException {
        db.del(getPath(key));
    }

    public List<Line> getLines() throws SnappydbException {
        String[] keys = db.findKeys(KEYBASE);
        List<Line> lines = new ArrayList<>();
        for (String key: keys) {
            Line tmp = db.getObject(key, Line.class);
            lines.add(tmp);
        }
        return lines;
    }

    public List<Line> getLines(String query) throws SnappydbException {
        String[] keys = db.findKeys(KEYBASE);
        List<Line> lines = new ArrayList<>();
        for (String key: keys) {
            Line tmp = db.getObject(key, Line.class);
            if (tmp.getLine().toLowerCase().contains(query.toLowerCase()) ||
                    tmp.getDescription().toLowerCase().contains(query.toLowerCase()))
                lines.add(tmp);
        }
        return lines;
    }

    private String getPath(String key) {
        return KEYBASE+":"+key;
    }

    public void close() throws SnappydbException {
        db.close();
    }
}
