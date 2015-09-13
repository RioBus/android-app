package com.tormentaLabs.riobus.map.utils;

import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;

import org.androidannotations.annotations.EBean;

/**
 * Created by limazix on 01/09/15.
 */
@EBean
public class MapUtils {

    public static boolean isValidString(String entry) {
        return !(entry == null || entry.equals("") || entry.trim().equals(""));
    }

    public static boolean isSearchAction(int actionId) {
        return actionId == EditorInfo.IME_ACTION_SEARCH;
    }
}
