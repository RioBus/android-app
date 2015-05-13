package com.tormentaLabs.riobus.service;

public class ServiceFactory {

    public static IService getSearchService(){
        return new SearchService();
    }
}
