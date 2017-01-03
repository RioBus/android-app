package com.tormentaLabs.riobus.common.actions;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.snappydb.SnappydbException;
import com.tormentaLabs.riobus.common.dao.LineDAO;
import com.tormentaLabs.riobus.common.models.Line;

import java.util.List;

public class LineStoreAction extends AsyncTask<Void, Integer, Boolean> {

    private static final String TAG = LineStoreAction.class.getName();
    private List<Line> items;
    private Context context;

    public LineStoreAction(List<Line> lines, Context context) {
        items = lines;
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(Void... args) {
        Log.v(TAG, "Trying to save the lines...");
        try {
            LineDAO dao = new LineDAO(context);
            for (int i = 0; i<items.size(); i++) {
                Line tmp = items.get(i);
                dao.addLine(tmp.getLine(), tmp);
            }
            Log.v(TAG, "Saved all new lines!");
            dao.close();
        } catch (SnappydbException e) {
            Log.e(TAG, e.getLocalizedMessage());
            return false;
        }
        return true;
    }

    @Override
    protected void onProgressUpdate(Integer ...values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
    }
}
