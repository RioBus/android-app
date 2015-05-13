package com.tormentaLabs.riobus.dataAccess;

public class DataAccessFactory {

    public static BusDataAccess getBusDataAccess(){
        return new BusDataAccess();
    }
}
