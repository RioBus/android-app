package com.tormentaLabs.riobus.util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by pedro on 06/07/2014.
 */
public class Util {

    public static final String TAG = "RioBus";

    public static void printInputReader(BufferedReader reader) {
        try {
            String a = reader.readLine();
            while (a!= null) {
                Log.i(Util.TAG, a);
                a = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void print(String msg) {
        Log.i(TAG, msg);
    }


    public static boolean verificaConexaoInternet(Activity act) {
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
