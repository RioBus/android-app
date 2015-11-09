package com.tormentaLabs.riobus;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.tormentaLabs.riobus.history.fragment.HistoryFragment;
import com.tormentaLabs.riobus.history.fragment.HistoryFragment_;
import com.tormentaLabs.riobus.map.MapFragment;
import com.tormentaLabs.riobus.map.MapFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_rio_bus)
public class RioBusActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = RioBusActivity_.class.getName();

    private int currentViewId;
    private ActionBarDrawerToggle rioBusDrawerToggle;

    @ViewById(R.id.riobusToolbar)
    Toolbar rioBusToolBar;

    @ViewById(R.id.rio_bus_drawer_layout)
    DrawerLayout rioBusDrawerLayout;

    @ViewById(R.id.navigation)
    NavigationView navigationView;

    @AfterViews
    public void afterViews() {
        setupToolBar();
        showMapFragment();
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setupToolBar() {
        rioBusDrawerToggle = new ActionBarDrawerToggle(this, rioBusDrawerLayout, rioBusToolBar, R.string.drawer_open, R.string.drawer_close);
        rioBusDrawerLayout.setDrawerListener(rioBusDrawerToggle);

        setSupportActionBar(rioBusToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void showMapFragment() {
        MapFragment mapFragment = new MapFragment_();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, mapFragment)
                .commit();
        currentViewId = R.id.action_home;
    }

    private void showHistoryFragment() {
        HistoryFragment historyFragment = new HistoryFragment_();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, historyFragment)
                .commit();
        currentViewId = R.id.action_history;
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        rioBusDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        rioBusDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (rioBusDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        rioBusDrawerLayout.closeDrawers();
        if(menuItem.getItemId() != currentViewId)
            navigate(menuItem.getItemId());
        return true;
    }

    private void navigate(int selectedViewId) {
        switch (selectedViewId) {
            case R.id.action_home:
                showMapFragment();
                break;
            case R.id.action_history:
                showHistoryFragment();
                break;
            default:
                break;
        }
    }
}