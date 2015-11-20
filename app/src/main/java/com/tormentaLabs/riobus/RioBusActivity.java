package com.tormentaLabs.riobus;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.tormentaLabs.riobus.favorite.fragment.FavoriteFragment;
import com.tormentaLabs.riobus.favorite.fragment.FavoriteFragment_;
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
    private Fragment lastFragment;
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
        navigationView.setNavigationItemSelectedListener(this);
        MapFragment mapFrag = new MapFragment_();
        switchFragment(mapFrag);
    }

    private void setupToolBar() {
        rioBusDrawerToggle = new ActionBarDrawerToggle(this, rioBusDrawerLayout, rioBusToolBar, R.string.drawer_open, R.string.drawer_close);
        rioBusDrawerLayout.setDrawerListener(rioBusDrawerToggle);

        setSupportActionBar(rioBusToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void switchFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if(lastFragment != null)
            transaction.addToBackStack(lastFragment.getClass().getName());

        transaction.replace(R.id.content_frame, fragment)
                .commit();

        lastFragment = fragment;
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

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() > 0)
            getSupportFragmentManager().popBackStack();
        else
            super.onBackPressed();
    }

    private void navigate(int selectedViewId) {
        switch (selectedViewId) {
            case R.id.action_home:
                MapFragment mapFrag = new MapFragment_();
                switchFragment(mapFrag);
                break;
            case R.id.action_history:
                HistoryFragment historyFrag = new HistoryFragment_();
                switchFragment(historyFrag);
                break;
            case R.id.action_favorite:
                FavoriteFragment favoriteFrag = new FavoriteFragment_();
                switchFragment(favoriteFrag);
                break;
            default:
                break;
        }
    }
}