package com.tormentaLabs.riobus.common.actions;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.tormentaLabs.riobus.common.APIAddess;
import com.tormentaLabs.riobus.common.interfaces.BusDataReceiver;
import com.tormentaLabs.riobus.common.models.Bus;

import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class BusDownloadAction extends AsyncTask<String, Integer, String> {

    private static final String TAG = BusDownloadAction.class.getName();
    private OkHttpClient client = new OkHttpClient();
    private BusDataReceiver receiver;

    public BusDownloadAction(BusDataReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    protected String doInBackground(String ...args) {
        Request request = new Request.Builder()
            .url(String.format(Locale.getDefault(), APIAddess.BUS_SEARCH, args[0]))
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
        Bus[] tmp = new Gson().fromJson(response, Bus[].class);
        receiver.onBusListReceived(Arrays.asList(tmp));
    }
}
