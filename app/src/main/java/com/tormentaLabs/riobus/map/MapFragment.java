package com.tormentaLabs.riobus.map;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.tormentaLabs.riobus.R;
import com.tormentaLabs.riobus.RioBusActivity_;
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
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.sharedpreferences.Pref;

/**
 * @author limazix
 * @since 2.0
 * Created on 01/09/15
 */
@EFragment(R.layout.fragment_google_map)
public class MapFragment extends Fragment implements MapComponentListener {

    private static final String TAG = MapFragment_.class.getName();
    private GoogleMap map;
    private SupportMapFragment mapFragment;

    @SystemService
    InputMethodManager inputMethodManager;

    @Pref
    MapPrefs_ mapPrefs;

    @Bean
    BusMarkerConponent busMapComponent;

    @Bean
    UserMarkerComponent userMarkerComponent;

    @Bean
    ItineraryComponent itineraryComponent;

    @AfterViews
    public void afterViews() {
        mapFragment = getMapFragment();
        map = mapFragment.getMap();
        setupMap();

        userMarkerComponent.setMap(map)
                .setListener(this)
                .buildComponent();
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

    /**
     * Listener of input search for keybord event action.
     * It is called every time the user press enter button.
     * @param textView
     * @param actionId
     */
    @EditorAction(R.id.search)
    public void onPerformSearch(TextView textView, int actionId) {
        String keyword = textView.getText().toString();
        if(MapUtils.isSearchAction(actionId) && MapUtils.isValidString(keyword)) {
            inputMethodManager.hideSoftInputFromWindow(textView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            busMapComponent.setLine(keyword)
            .setListener(this)
            .setMap(map)
            .buildComponent();

            itineraryComponent.setLine(keyword)
            .setListener(this)
            .setMap(map)
            .buildComponent();
        }
    };

    /**
     * Used to show/hide sidemenu based on sidemenu button click
     */
    @Click(R.id.sidemenu_toggle)
    public void sidemenuToggle() {
        ((RioBusActivity_)getActivity()).sidemenuToggle();
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

}
