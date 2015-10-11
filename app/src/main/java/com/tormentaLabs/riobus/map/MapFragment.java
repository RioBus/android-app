package com.tormentaLabs.riobus.map;

import android.app.SearchManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.tormentaLabs.riobus.R;
import com.tormentaLabs.riobus.RioBusActivity_;
import com.tormentaLabs.riobus.history.controller.HistoryController;
import com.tormentaLabs.riobus.itinerary.ItineraryComponent;
import com.tormentaLabs.riobus.marker.BusMarkerConponent;
import com.tormentaLabs.riobus.map.listener.MapComponentListener;
import com.tormentaLabs.riobus.map.utils.MapPrefs_;
import com.tormentaLabs.riobus.map.utils.MapUtils;
import com.tormentaLabs.riobus.marker.UserMarkerComponent;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.EditorAction;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

/**
 * @author limazix
 * @since 2.0
 * Created on 01/09/15
 */
@OptionsMenu(R.menu.map_fragment)
@EFragment(R.layout.fragment_map)
public class MapFragment extends Fragment implements MapComponentListener, SearchView.OnQueryTextListener {

    private static final String TAG = MapFragment_.class.getName();
    private GoogleMap map;
    private SupportMapFragment mapFragment;

    @Pref
    MapPrefs_ mapPrefs;

    @Bean
    BusMarkerConponent busMapComponent;

    @Bean
    UserMarkerComponent userMarkerComponent;

    @Bean
    ItineraryComponent itineraryComponent;

    @Bean
    HistoryController historyController;

    @OptionsMenuItem(R.id.search)
    MenuItem menuSearch;

    @AfterViews
    public void afterViews() {
        mapFragment = getMapFragment();
        map = mapFragment.getMap();
        setupMap();

        userMarkerComponent.setMap(map)
                .setListener(this)
                .buildComponent();

//        setupAutoComplete();
    }
/*
    private void setupAutoComplete() {
        final String[] lines = historyController.getHistory();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, lines);

        searchTextView.setAdapter(adapter);
    }

    /**
     * Used to setup global map preferences
     */
    private void setupMap() {
        if(map.getUiSettings() != null) {
            map.getUiSettings().setMapToolbarEnabled(mapPrefs.isMapToolbarEnable().get());
            map.getUiSettings().setCompassEnabled(mapPrefs.isMapCompasEnable().get());
            map.setTrafficEnabled(mapPrefs.isMapTrafficEnable().get());
        }
        map.setMyLocationEnabled(mapPrefs.isMapMyLocationEnable().get());
    }

    @Click(R.id.button_user_location)
    void updateUserLocation() {
        userMarkerComponent.updateUserLocation();
    }

    @OptionsItem(R.id.search)
    boolean menuSearch() {
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuSearch);
        searchView.setOnQueryTextListener(this);
        return false;
    }

    /**
     * Used to access the map fragment which is a child fragment called map_fragment
     * @return SupportMapFragment
     */
    private SupportMapFragment getMapFragment() {
        return (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_fragment);
    }

    @Override
    public void onComponentMapReady() {
        Log.d(TAG, "Map Ready");
    }

    @Override
    public void onComponentMapError(Exception error) {
        Log.e(TAG, error.getMessage());
    }

    @Override
    public boolean onQueryTextSubmit(String keyword) {

        if (MapUtils.isValidString(keyword)) {
            busMapComponent.setLine(keyword)
                    .setListener(this)
                    .setMap(map)
                    .buildComponent();

            itineraryComponent.setLine(keyword)
                    .setListener(this)
                    .setMap(map)
                    .buildComponent();

            String[] lines = MapUtils.separateMultiLines(keyword);
            historyController.addLines(lines);
        }

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
