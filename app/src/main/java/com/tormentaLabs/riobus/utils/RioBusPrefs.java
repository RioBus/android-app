package com.tormentaLabs.riobus.utils;

import com.tormentaLabs.riobus.R;

import org.androidannotations.annotations.sharedpreferences.DefaultRes;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * Created by limazix on 01/09/15.
 */
@SharedPref(value = SharedPref.Scope.UNIQUE)
public interface RioBusPrefs {

    @DefaultRes(R.string.server_addr_v3)
    String getServerAddr();
}
