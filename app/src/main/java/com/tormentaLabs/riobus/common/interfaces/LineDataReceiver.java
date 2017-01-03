package com.tormentaLabs.riobus.common.interfaces;

import com.tormentaLabs.riobus.common.models.Line;

import java.util.List;

public interface LineDataReceiver {
    void onLineListReceived(List<Line> items);
}