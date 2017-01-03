package com.tormentaLabs.riobus.common.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.tormentaLabs.riobus.common.APIAddess;
import com.tormentaLabs.riobus.common.interfaces.ItineraryDataReceiver;
import com.tormentaLabs.riobus.common.models.Itinerary;

import java.io.IOException;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class ItineraryDownloadTask extends AsyncTask<String, Integer, String> {

    private static final String TAG = ItineraryDownloadTask.class.getName();
    private OkHttpClient client = new OkHttpClient();
    private ItineraryDataReceiver receiver;

    public ItineraryDownloadTask(ItineraryDataReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    protected String doInBackground(String... args) {
        Request request = new Request.Builder()
                .url(String.format(Locale.getDefault(), APIAddess.ITINERARY_SEARCH, args[0]))
                .build();

        try {
            return client.newCall(request).execute().body().string();
        } catch (IOException e) {
            Log.v(TAG, e.getLocalizedMessage());
            return "[]";
        }
    }

    @Override
    protected void onProgressUpdate(Integer ...values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);
        Itinerary tmp = new Gson().fromJson(response, Itinerary.class);
        receiver.onItineraryReceived(tmp);
    }
}
