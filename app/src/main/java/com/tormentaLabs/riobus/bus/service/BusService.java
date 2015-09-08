package com.tormentaLabs.riobus.bus.service;

import com.tormentaLabs.riobus.bus.model.BusModel;

import org.androidannotations.annotations.rest.Accept;
import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.List;

/**
 * Created by limazix on 02/09/15.
 */
@Accept(MediaType.APPLICATION_JSON)
@Rest(rootUrl = "http://rest.riob.us/v3",converters = {MappingJackson2HttpMessageConverter.class})
public interface BusService {

    @Get("/search/{line}")
    List<BusModel> getBusesByLine(String line);

}
