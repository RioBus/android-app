package com.tormentaLabs.riobus;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.tormentaLabs.riobus.adapter.BusInfoWindowAdapter;
import com.tormentaLabs.riobus.asyncTasks.BusSearchTask;
import com.tormentaLabs.riobus.common.BusDataReceptor;
import com.tormentaLabs.riobus.common.NetworkUtil;
import com.tormentaLabs.riobus.common.Util;
import com.tormentaLabs.riobus.model.Bus;
import com.tormentaLabs.riobus.model.MapMarker;
import com.tormentaLabs.riobus.service.HttpService;

import java.util.Date;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, BusDataReceptor,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private AutoCompleteTextView search;
    private ImageButton info;

    Location currentLocation;

    MapMarker mapMarker;
    private GoogleMap map;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapa);

        buildGoogleApiClient();
        setupSearch();
        setupInfo();
        setupMap();
        getSupportActionBar().hide();

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!mGoogleApiClient.isConnecting() && !mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    protected void buildGoogleApiClient() {

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    private void setupInfo() {
        info = (ImageButton) findViewById(R.id.button_about);

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.about_dialog);
                dialog.setTitle(getString(R.string.about_title));
                TextView tv = (TextView) dialog.findViewById(R.id.content);
                tv.setText(Html.fromHtml(getString(R.string.about_text)));
                tv.setMovementMethod(LinkMovementMethod.getInstance());
                dialog.show();
            }
        });
    }

    private void setupSearch() {

        search = (AutoCompleteTextView) findViewById(R.id.search);
        //Quando o usuario digita enter, ele faz a requisição procurando a posição daquela linha
        search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                String searchContent = search.getText().toString();
                Activity activity = MainActivity.this;

                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER) &&
                        Util.isValidString(searchContent)) {

                    InputMethodManager imm = (InputMethodManager) getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(search.getWindowToken(), 0);

                    if (NetworkUtil.checkInternetConnection(activity)) {

                        new BusSearchTask(MainActivity.this).execute(searchContent);

                    } else {
                        Toast.makeText(activity, getString(R.string.error_connection_internet), Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    private void setupMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        map = mapFragment.getMap();
        if(map.getUiSettings() != null) {
            map.getUiSettings().setMapToolbarEnabled(false);
            map.getUiSettings().setCompassEnabled(false);
        }
        map.setMyLocationEnabled(false);

        mapMarker = new MapMarker(map);
    }

    public void updateUserLocation() {
        currentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (currentLocation == null)
            return;
        LatLng position = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 13));
        map.animateCamera(CameraUpdateFactory.zoomTo(16), 2000, null);
        mapMarker.markUserPosition(this, position);

    }


    @Override
    public void retrieveBusData(List<Bus> buses) {
        if (buses == null) {
            Toast.makeText(this, getString(R.string.error_connection_server), Toast.LENGTH_SHORT).show();
            return;
        }
        if (buses.isEmpty()) {
            Toast.makeText(this, getString(R.string.error_bus_404), Toast.LENGTH_SHORT).show();
            return;
        }

        map.clear();

        map.setInfoWindowAdapter(new BusInfoWindowAdapter(this));
        MapMarker marker = new MapMarker(map);
        marker.addMarkers(buses);
        LatLngBounds.Builder builder = marker.getBoundsBuilder();

        if (currentLocation != null) {
            LatLng userPosition = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            mapMarker.markUserPosition(this, userPosition);
            builder.include(userPosition);
        }

        LatLngBounds bounds = builder.build();

        int padding = 100; // offset from edges of the map in pixels
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);

        map.moveCamera(cu);
        map.animateCamera(cu);

    }

    @Override
    public void onMapReady(GoogleMap map) {
    }


    @Override
    public void onConnected(Bundle bundle) {
        updateUserLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }
}
