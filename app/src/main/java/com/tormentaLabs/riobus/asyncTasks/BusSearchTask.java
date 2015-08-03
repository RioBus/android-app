package com.tormentaLabs.riobus.asyncTasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tormentaLabs.riobus.EnvironmentConfig;
import com.tormentaLabs.riobus.R;
import com.tormentaLabs.riobus.common.BusDataReceptor;
import com.tormentaLabs.riobus.model.Bus;
import com.tormentaLabs.riobus.service.HttpService;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.converter.GsonConverter;

public class BusSearchTask extends AsyncTask<String, Void, List<Bus>>{

    private Context context;
    private HttpService service;
    ProgressDialog dialog;

    public BusSearchTask(Context context){
        this.context = context;
    }

    private List<Bus> getOldAPI(String lines){
        Gson gson = new GsonBuilder()
                .setDateFormat("MM-dd-yyyy HH:mm:ss")
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(EnvironmentConfig.URL_ENDPOINT)
                .setConverter(new GsonConverter(gson))
                .build();
        service = restAdapter.create(HttpService.class);

        try{
            return service.getOldPage(lines);
        } catch (RetrofitError e){
            e.printStackTrace();
            return new ArrayList<Bus>();
        }
    }

    private List<Bus> getNewAPI(String lines){
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(EnvironmentConfig.URL_ENDPOINT)
                .setConverter(new GsonConverter(gson))
                .build();
        service = restAdapter.create(HttpService.class);

        try{
            return service.getPage(lines);
        } catch (RetrofitError e){
            e.printStackTrace();
            return new ArrayList<Bus>();
        }
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
        String data = params[0].replaceAll("\\s", "");

        List<Bus> buses = getNewAPI(data);
        return (buses.size()>0)? buses : getOldAPI(data);
    }

    @Override
    protected void onPostExecute(List<Bus> buses){
        super.onPostExecute(buses);
        TimeZone tz = TimeZone.getTimeZone("America/Sao_Paulo");
        int offset = tz.getOffset(new Date().getTime())/1000/60/60;
        for(int i=0; i<buses.size(); i++){
            Bus bus = buses.get(i);
            DateTime dt = new DateTime(bus.getTimestamp());
            dt = dt.plusHours(offset);
            bus.setTimestamp(dt.toDate());
            buses.set(i, bus);
        }
        dialog.dismiss();

        BusDataReceptor receptor = (BusDataReceptor) context;
        receptor.retrieveBusData(buses);
    }
}
