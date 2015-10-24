package com.tormentaLabs.riobus;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import com.tormentaLabs.riobus.history.fragment.HistoryFragment;
import com.tormentaLabs.riobus.history.fragment.HistoryFragment_;
import com.tormentaLabs.riobus.map.MapFragment;
import com.tormentaLabs.riobus.map.MapFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_rio_bus)
public class RioBusActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = RioBusActivity_.class.getName();

    private int currentViewId;

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
        setSupportActionBar(rioBusToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        rioBusToolBar.setNavigationIcon(R.drawable.ic_menu);
        rioBusToolBar.setNavigationOnClickListener(this);
    }

    private void closeSideMenu() {
        rioBusDrawerLayout.closeDrawer(Gravity.LEFT);
    }

    private void openSideMenu() {
        rioBusDrawerLayout.openDrawer(Gravity.LEFT);
    }

    @Override
    public void onClick(View view) {
        openSideMenu();
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
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        if(menuItem.getItemId() != currentViewId)
            navigate(menuItem.getItemId());

        closeSideMenu();
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