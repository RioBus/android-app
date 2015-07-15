package com.tormentaLabs.riobus.asyncTasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tormentaLabs.riobus.EnvironmentConfig;
import com.tormentaLabs.riobus.R;
import com.tormentaLabs.riobus.common.BusDataReceptor;
import com.tormentaLabs.riobus.model.Bus;
import com.tormentaLabs.riobus.service.HttpService;

import java.util.List;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public class BusSearchTask extends AsyncTask<String, Void, List<Bus>>{

    private Context context;
    ProgressDialog dialog;

    public BusSearchTask(Context context){
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(context);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(context.getString(R.string.searching_data));
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected List<Bus> doInBackground(String... params) {
        String data = params[0];

        Gson gson = new GsonBuilder()
                .setDateFormat("MM-dd-yyyy HH:mm:ss")
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(EnvironmentConfig.URL_ENDPOINT)
                .setConverter(new GsonConverter(gson))
                .build();


        HttpService service = restAdapter.create(HttpService.class);

        List<Bus> buses = service.getPage(data);

        return buses;
    }

    @Override
    protected void onPostExecute(List<Bus> buses){
        super.onPostExecute(buses);
        dialog.dismiss();

        BusDataReceptor receptor = (BusDataReceptor) context;
        receptor.retrieveBusData(buses);
    }
}
