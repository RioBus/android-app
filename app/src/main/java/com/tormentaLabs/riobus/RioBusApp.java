package com.tormentaLabs.riobus;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.activeandroid.app.Application;

import org.androidannotations.annotations.EApplication;

/**
 * Created by limazix on 11/10/15.
 */
@EApplication
public class RioBusApp extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
