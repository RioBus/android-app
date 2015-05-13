package com.tormentaLabs.riobus.dataAccess;

public interface IDataAccess {
    int inputLength = 1;
    Object execute(Object ...obj);
}
