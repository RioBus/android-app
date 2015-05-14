package com.tormentaLabs.riobus.common;

import com.tormentaLabs.riobus.domain.Bus;

import java.util.List;

public interface BusDataReceptor {

    void retrieveBusData(List<Bus> buses);
}
