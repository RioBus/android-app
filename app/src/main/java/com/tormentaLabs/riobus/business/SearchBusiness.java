package com.tormentaLabs.riobus.business;

import com.tormentaLabs.riobus.dataAccess.DataAccessFactory;
import com.tormentaLabs.riobus.dataAccess.IDataAccess;

import java.util.List;

public class SearchBusiness implements IBusiness{

    private IDataAccess dataAccess;

    public SearchBusiness(){
        dataAccess = DataAccessFactory.getBusDataAccess();
    }

    @Override
    public List execute(Object... obj) {
        if(obj.length<inputLength) throw new RuntimeException("Missing argument.");

        String data = obj[0].toString();
        List buses;
        buses = getByLine(data);
        if(buses.size()<1){
            buses = getByCode(data);
        }
        return buses;
    }

    private List getByLine(String lines){
        return (List) dataAccess.execute(lines);
    }

    private List getByCode(String codes){
        return (List) dataAccess.execute(codes);
    }
}
