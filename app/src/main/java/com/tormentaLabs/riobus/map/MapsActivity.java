package com.tormentaLabs.riobus.map;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.snappydb.SnappydbException;
import com.tormentaLabs.riobus.R;
import com.tormentaLabs.riobus.common.dao.FavoritesDAO;
import com.tormentaLabs.riobus.common.interfaces.BusDataReceiver;
import com.tormentaLabs.riobus.common.interfaces.ItineraryDataReceiver;
import com.tormentaLabs.riobus.common.models.Bus;
import com.tormentaLabs.riobus.common.models.Itinerary;
import com.tormentaLabs.riobus.common.models.Line;
import com.tormentaLabs.riobus.common.actions.BusDownloadAction;
import com.tormentaLabs.riobus.common.actions.ItineraryDownloadAction;
import com.tormentaLabs.riobus.map.controllers.MapsController;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, BusDataReceiver, ItineraryDataReceiver {

    private static final String TAG = MapsActivity.class.getName();
    private static final int PERMISSION_LOCATION_CODE = 99;

    private MapsController controller;

    @BindView(R.id.txt_from) TextView txtFrom;
    @BindView(R.id.txt_to) TextView txtTo;
    @BindView(R.id.btnStar) ImageView btnStar;
    @BindView(R.id.container) RelativeLayout viewContainer;

    public static final String LINE_TITLE = "lineTitle";
    public static final String LINE_DESCRIPTION = "lineDescription";
    private String queryString;
    private String description;
    private boolean isFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maps_activity);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        queryString = getIntent().getStringExtra(LINE_TITLE);
        setTitle(queryString);

        setupSnackbar();
        checkForPermissions();
    }

    private void checkForPermissions() {
        String[] permission = {"android.permission.ACCESS_FINE_LOCATION"};
        if (checkCallingOrSelfPermission(permission[0]) == PackageManager.PERMISSION_GRANTED) setupView();
        else ActivityCompat.requestPermissions(this, permission, PERMISSION_LOCATION_CODE);
    }

    private void setupView() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void setupSnackbar() {
        description = getIntent().getStringExtra(LINE_DESCRIPTION);
        if (description.contains(" X ")) {
            String[] tmp = getIntent().getStringExtra(LINE_DESCRIPTION).split(" X ");
            txtFrom.setText(tmp[0]);
            txtTo.setText(tmp[1]);
        } else {
            txtFrom.setText(getString(R.string.map_snackbar_sense));
            txtTo.setText(getString(R.string.maps_snackbar_unknown));
        }

        try {
            FavoritesDAO dao = new FavoritesDAO(getBaseContext());
            isFavorite = dao.isFavorite(queryString);
            dao.close();
            updateStar();
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }

    private void updateStar() {
        if (isFavorite) btnStar.setImageResource(R.drawable.ic_favorite);
        else btnStar.setImageResource(R.drawable.ic_not_favorite);
    }

    @OnClick(R.id.btnStar)
    public void onClickStar() {
        if (isFavorite) removeFromFavorites();
        else addToFavorites();
    }

    private void addToFavorites() {
        try {
            FavoritesDAO dao = new FavoritesDAO(getBaseContext());
            dao.addFavorite(queryString, new Line(queryString, description));
            isFavorite = true;
            dao.close();
            updateStar();
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }

    private void removeFromFavorites() {
        try {
            FavoritesDAO dao = new FavoritesDAO(getBaseContext());
            dao.removeFavorite(queryString);
            isFavorite = false;
            dao.close();
            updateStar();
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {

            case PERMISSION_LOCATION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    setupView();
                else {
                    hideSnackbar();
                    Snackbar.make(viewContainer, "The location permission was not granted ;(", Snackbar.LENGTH_LONG).show();
                    Log.v(TAG, "The location permission was not granted ;(");
                }
                break;
        }
    }

    private void hideSnackbar() {
        viewContainer.removeView(findViewById(R.id.snackbar_container));
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        controller = new MapsController(googleMap);
        googleMap.setTrafficEnabled(true);
        googleMap.setInfoWindowAdapter(new BusInfowindowAdapter(getBaseContext()));
        new BusDownloadAction(this).execute(queryString);
        new ItineraryDownloadAction(this).execute(queryString);
    }

    @Override
    public void onBusListReceived(List<Bus> items) {
        if (items.size() > 0)
            controller.addBuses(items);
        else{
            hideSnackbar();
            Snackbar.make(viewContainer, getString(R.string.map_no_buses), Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItineraryReceived(Itinerary itinerary) {
        if (itinerary != null) controller.addItinerary(itinerary);
    }
}
