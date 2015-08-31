package com.tormentaLabs.riobus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;

@Fullscreen
@EActivity(R.layout.activity_rio_bus)
@OptionsMenu(R.menu.menu_rio_bus)
public class RioBusActivity extends AppCompatActivity {

    private static final String TAG = RioBusActivity_.class.getName();

    @OptionsItem(R.id.action_settings)
    public void settingsSelected() {
        Log.d(TAG, "Settings Selected");
    }

}
