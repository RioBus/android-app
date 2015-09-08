package com.tormentaLabs.riobus.map.utils;

import org.androidannotations.annotations.EBean;

/**
 * Created by limazix on 01/09/15.
 */
@EBean
public class MapUtils {

    public static boolean isValidString(String entry) {
        return !(entry == null || entry.equals("") || entry.trim().equals(""));
    }
}
