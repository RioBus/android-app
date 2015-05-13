package com.tormentaLabs.riobus.dataAccess;

import android.util.Log;

import com.github.kevinsawicki.http.HttpRequest;
import com.tormentaLabs.riobus.EnvironmentConfig;
import com.tormentaLabs.riobus.domain.Bus;

import java.util.ArrayList;
import java.util.List;

public class BusDataAccess implements IDataAccess{

    @Override
    public List execute(Object... obj) {
        if(obj.length<inputLength) throw new RuntimeException("Missing argument.");
        String data = obj[0].toString();
        return getBuses(data);
    }

    private List<Bus> getBuses(String data){
        String URL = EnvironmentConfig.serverBaseURL+"search/"+EnvironmentConfig.platformId+"/"+data;
        String content = "[]";
        try{
            HttpRequest request = HttpRequest.get(URL);
            request.acceptGzipEncoding().uncompress(true);
            if(request.ok()){
                content = request.body();
            }
        } catch (HttpRequest.HttpRequestException e){
            Log.i("BUSDA", e.toString());
        }
        System.out.println(content);
        return new ArrayList<Bus>();
    }
}
