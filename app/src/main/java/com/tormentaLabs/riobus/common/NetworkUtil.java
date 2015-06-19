package com.tormentaLabs.riobus.common;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by Pedro on 6/19/15.
 */
public class NetworkUtil {
    public static boolean checkInternetConnection(Activity act) {
        ConnectivityManager conectivtyManager = (ConnectivityManager) act.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conectivtyManager.getActiveNetworkInfo() != null
                && conectivtyManager.getActiveNetworkInfo().isAvailable()
                && conectivtyManager.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            return false;
        }
    }
}
