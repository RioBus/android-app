package com.tormentaLabs.riobus.service;

import com.tormentaLabs.riobus.model.Bus;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Pedro on 7/14/15.
 */
public interface HttpService {

    @Headers({
        "Accept:application/json",
        "Cache-Control: max-age=640000",
        "userAgent: riobus-android-2.0"
    })
    @GET("/v3/search/{lines}")
    void getPage(@Path("lines")String lines, Callback< List<Bus> > buses);

    @Headers({
        "Accept:application/json",
        "Cache-Control: max-age=640000",
        "userAgent: riobus-android-2.0"
    })
    @GET("/v3/search/{lines}")
    List<Bus> getPage(@Path("lines")String lines);
}
