package com.tormentaLabs.riobus.itinerary.service;

import com.tormentaLabs.riobus.itinerary.model.ItineraryModel;

import org.androidannotations.annotations.rest.Accept;
import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * Created by limazix on 05/10/15.
 */
@Accept(MediaType.APPLICATION_JSON)
@Rest(rootUrl = "http://rest.riob.us/v3",converters = {MappingJackson2HttpMessageConverter.class})
public interface ItineraryService {

    @Get("/itinerary/{line}")
    ItineraryModel getItinarary(String line);
}
