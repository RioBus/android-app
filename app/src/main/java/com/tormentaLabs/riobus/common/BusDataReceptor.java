package com.tormentaLabs.riobus.common;

import com.tormentaLabs.riobus.model.Bus;

import java.util.List;

public interface BusDataReceptor {

    void retrieveBusData(List<Bus> buses);
}
