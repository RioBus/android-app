package com.tormentaLabs.riobus.common;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.util.Log;
import android.widget.AutoCompleteTextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Util {

    public static final String TAG = "RioBus";
    public static final String LINE_HISTORY = "LineHistory";

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

    public static boolean isValidEntry(String entry) {
        return !(entry == null || entry.equals("") || entry.trim().equals(""));
    }


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

    public static String[] getHistory(Context context) {
        String[] lineHistory = null;
        SharedPreferences settings = context.getSharedPreferences(TAG,0);
        String lines = settings.getString(LINE_HISTORY,"");
        if(lines.length() > 0){
            lineHistory = lines.split(",");
        }
        return lineHistory;
    };
    public static void saveOnHistory(Context context,String line , AutoCompleteTextView search) {
        StringBuilder sb = new StringBuilder();
        boolean alreadyAdded = false;
        if (getHistory(context) != null){
            List<String> lines = Arrays.asList(getHistory(context));
            for (String histLines: lines){
                if (histLines.contains(line)){
                    alreadyAdded = true;
                };
                sb.append(histLines).append(",");
            }
        }
        if (!alreadyAdded){
            sb.insert(0,line+",");
        }
        SharedPreferences settings = context.getSharedPreferences(TAG,0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(LINE_HISTORY,sb.toString());
        editor.commit();

    };

}
