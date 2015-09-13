package com.tormentaLabs.riobus.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by limazix on 12/09/15.
 */
public class RioBusUtils {

    public static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";

    public static Date parseStringToDate(String timestamp) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_PATTERN);
        return dateFormat.parse(timestamp);
    }
}
