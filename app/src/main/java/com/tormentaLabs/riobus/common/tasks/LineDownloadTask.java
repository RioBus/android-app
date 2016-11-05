package com.tormentaLabs.riobus.common.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.tormentaLabs.riobus.common.models.Line;
import com.tormentaLabs.riobus.common.APIAddess;
import com.tormentaLabs.riobus.common.interfaces.LineDataReceiver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class LineDownloadTask extends AsyncTask<Void, Integer, String> {

    private static final String TAG = LineDownloadTask.class.getName();
    private OkHttpClient client = new OkHttpClient();
    private LineDataReceiver receiver;

    public LineDownloadTask(LineDataReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    protected String doInBackground(Void... args) {
        Request request = new Request.Builder().url(APIAddess.LINE_SEARCH).build();
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
        Line[] tmp = new Gson().fromJson(response, Line[].class);
        receiver.onLineListReceived(new ArrayList<>(Arrays.asList(tmp)));
    }
}
