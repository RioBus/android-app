package com.tormentaLabs.riobus.service;

import com.tormentaLabs.riobus.business.BusinessFactory;
import com.tormentaLabs.riobus.business.IBusiness;
import com.tormentaLabs.riobus.model.Bus;

import java.util.List;

public class SearchService implements IService{


    @Override
    public List<Bus> execute(Object ...obj) {
        if(obj.length<inputLength) throw new RuntimeException("Missing argument.");
        IBusiness business = BusinessFactory.getSearchBusiness();
        return (List) business.execute(obj);
    }
}
