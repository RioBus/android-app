package com.tormentaLabs.riobus.common.interfaces;

import com.tormentaLabs.riobus.common.models.Bus;

import java.util.List;

public interface BusDataReceiver {
    void onBusListReceived(List<Bus> items);
}