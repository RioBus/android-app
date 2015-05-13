package com.tormentaLabs.riobus.business;

public class BusinessFactory {

    public static IBusiness getSearchBusiness(){
        return new SearchBusiness();
    }
}
