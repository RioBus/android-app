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
import com.tormentaLabs.riobus.model.BusData;
import com.tormentaLabs.riobus.model.Itinerary;
import com.tormentaLabs.riobus.service.HttpService;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.converter.GsonConverter;

public class BusSearchTask extends AsyncTask<String, Void, BusData>{

    private Context context;
    private HttpService service;
    ProgressDialog dialog;

    public BusSearchTask(Context context){
        this.context = context;
    }

    private BusData getNewAPI(String lines){
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(EnvironmentConfig.URL_ENDPOINT)
                .setConverter(new GsonConverter(gson))
                .build();
        service = restAdapter.create(HttpService.class);

        try{

            List<Bus> buses = service.getPage(lines);
            List<Itinerary> itineraries = new ArrayList<Itinerary>();

            String[] busLines = lines.split(",");
            for(String line: busLines) {
                try {
                    itineraries.add(service.getItineraryPage(line));
                }catch(RetrofitError exp){exp.printStackTrace();}
            }

            //Codigo para que as diversas linhas de itinerario sejam plotadas quando se pesquisa linhas de onibus por bairro.
            //Resulta em visualizacao muito confusa por causa da quantidade de linhas plotadas ao mesmo tempo.
            /*if(itineraries.isEmpty()){
                Set<String> busLines2 = new HashSet<String>();
                for(Bus bus: buses) busLines2.add(bus.getLine());
                for(String line: busLines2) {
                    try {
                        itineraries.add(service.getItineraryPage(line));
                    }catch(RetrofitError exp){exp.printStackTrace();}
                }
            }*/

            return new BusData(buses,itineraries);

        } catch (RetrofitError e){
            try{
                return new BusData(service.getPage(lines));
            } catch (RetrofitError e2){
                e2.printStackTrace();
                return new BusData();
            }
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
    protected BusData doInBackground(String... params) {
        String data = params[0].replaceAll("\\s", "");

        BusData busData = getNewAPI(data);
        return busData;
    }

    @Override
    protected void onPostExecute(BusData busData){
        super.onPostExecute(busData);
        TimeZone tz = TimeZone.getTimeZone("America/Sao_Paulo");
        int offset = tz.getOffset(new Date().getTime())/1000/60/60;
        List<Bus> buses = busData.getBuses();
        for(int i=0; i<buses.size(); i++){
            Bus bus = buses.get(i);
            DateTime dt = new DateTime(bus.getTimestamp());
            dt = dt.plusHours(offset);
            bus.setTimestamp(dt.toDate());
            buses.set(i, bus);
        }
        busData.setBuses(buses);
        dialog.dismiss();

        BusDataReceptor receptor = (BusDataReceptor) context;
        receptor.retrieveBusData(busData);
    }
}
