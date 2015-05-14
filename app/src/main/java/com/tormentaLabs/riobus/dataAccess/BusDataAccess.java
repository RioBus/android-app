package com.tormentaLabs.riobus.dataAccess;

import android.util.Log;

import com.github.kevinsawicki.http.HttpRequest;
import com.tormentaLabs.riobus.EnvironmentConfig;
import com.tormentaLabs.riobus.common.Util;
import com.tormentaLabs.riobus.domain.Bus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BusDataAccess implements IDataAccess{

    @Override
    public List execute(Object... obj) {
        if(obj.length<inputLength) throw new RuntimeException("Missing argument.");
        String data = obj[0].toString();
        return getBuses(data);
    }

    private List<Bus> getBuses(String data){
        String URL = EnvironmentConfig.serverBaseURL+"search/"+EnvironmentConfig.platformId+"/"+data;
        try{
            HttpRequest request = HttpRequest.get(URL);
            request.acceptJson().acceptGzipEncoding().uncompress(true);
            if(request.ok()){
                String body = request.body();
                request.disconnect();
                return parseData(body);
            }
            return null;
        } catch (HttpRequest.HttpRequestException e){
            Log.e(Util.TAG, e.getMessage());
            return null;
        }
    }

    private List<Bus> parseData(String body) {
        JSONArray list;
        List<Bus> buses = new ArrayList<Bus>();
        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss", Locale.US);
        try {
            list = new JSONArray(body);

            for(int i=0; i<list.length(); i++){
                JSONObject obj = list.getJSONObject(i);
                Bus bus = new Bus();
                bus.setLine(obj.getString("line"));
                bus.setLatitude(obj.getDouble("latitude"));
                bus.setLongitude(obj.getDouble("longitude"));
                bus.setOrder(obj.getString("order"));
                bus.setVelocity(obj.getDouble("speed"));
                bus.setTimestamp(format.parse(obj.getString("timeStamp")));
                buses.add(bus);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return buses;
    }
}
