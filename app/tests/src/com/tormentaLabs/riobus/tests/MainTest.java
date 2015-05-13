package com.tormentaLabs.riobus.tests;

import android.test.ActivityInstrumentationTestCase2;

import com.google.android.gms.maps.GoogleMap;
import com.tormentaLabs.riobus.Main_;

/**
 * Created by pedro on 20/07/2014.
 */
public class MainTest extends ActivityInstrumentationTestCase2 {

    private Main_ act;
    GoogleMap mapa;

    public MainTest() {
        super(Main_.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        act = (Main_) getActivity();
        mapa = act.map;
    }

    public void testPreconditions() {
        assertNotNull("Main is null", act);
        assertNotNull("Mapa is null", mapa);
    }


}
