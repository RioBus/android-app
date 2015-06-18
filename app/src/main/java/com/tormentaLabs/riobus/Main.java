package com.tormentaLabs.riobus;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.Drive;
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
import com.tormentaLabs.riobus.adapter.BusInfoWindowAdapter;
import com.tormentaLabs.riobus.asyncTasks.BusSearchTask;
import com.tormentaLabs.riobus.common.BusDataReceptor;
import com.tormentaLabs.riobus.common.Util;
import com.tormentaLabs.riobus.domain.Bus;
import com.tormentaLabs.riobus.domain.MapMarker;

import java.util.List;

public class Main extends ActionBarActivity implements OnMapReadyCallback, BusDataReceptor,
                                                       GoogleApiClient.ConnectionCallbacks,
                                                       GoogleApiClient.OnConnectionFailedListener {

    private AutoCompleteTextView search;
    private LinearLayout linearLayout;

    Location currentLocation;
    MarkerOptions userMarker;
    private GoogleMap map;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapa);

        buildGoogleApiClient();

        search = (AutoCompleteTextView) findViewById(R.id.search);
        linearLayout = (LinearLayout) findViewById(R.id.button_about);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickOnAboutButton();
            }
        });


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        map = mapFragment.getMap();

        setUpMapIfNeeded();
        setSuggestions(); // Shows the previous searched lines
        getSupportActionBar().hide();

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!mGoogleApiClient.isConnecting()  && !mGoogleApiClient.isConnected()) {
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

    private void setSuggestions() {
        String[] lineHistory = Util.getHistory(this);
        if (lineHistory != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_dropdown_item_1line, Util.getHistory(this));
            search.setAdapter(adapter);
            search.setThreshold(0);
        }
    }

    private void setUpMapIfNeeded() {
        //Quando o usuario digita enter, ele faz a requisição procurando a posição daquela linha
        search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                String searchContent = search.getText().toString();
                Activity activity = Main.this;

                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER) &&
                        Util.isValidEntry(searchContent)) {

                    InputMethodManager imm = (InputMethodManager) getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(search.getWindowToken(), 0);

                    if (Util.checkInternetConnection(activity)) {
                        Util.saveOnHistory(activity, searchContent, search);
                        setSuggestions(); //Updating the adapter
                        new BusSearchTask(activity).execute(searchContent);

                    } else {
                        Toast.makeText(activity, getString(R.string.error_connection_internet), Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    public void updateUserLocation() {
        currentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if(currentLocation == null) return;
        LatLng position = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        map.moveCamera(CameraUpdateFactory.newLatLng(position));
        map.animateCamera(CameraUpdateFactory.zoomTo(16), 2000, null);
        markUserPosition(position);
    }

    private void markUserPosition(LatLng posicao) {
        if (userMarker == null) {
            userMarker = new MarkerOptions().position(posicao).title(getString(R.string.marker_user)).icon(BitmapDescriptorFactory
                    .fromResource(R.drawable.man_maps));
        } else {
            userMarker.position(posicao);
        }
        map.addMarker(userMarker);
        // depois de limpar tudo, precisa readicionar os pontos que estavam no map, caso houvesse
    }

    public void clickOnAboutButton() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.about_dialog);
        dialog.setTitle(getString(R.string.about_title));
        TextView tv = (TextView) dialog.findViewById(R.id.content);
        tv.setText(Html.fromHtml(getString(R.string.about_text)));
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        dialog.show();
    }

    @Override
    public void retrieveBusData(List<Bus> buses) {
        if(buses == null){
            Toast.makeText(this, getString(R.string.error_connection_server), Toast.LENGTH_SHORT).show();
            return;
        }
        if(buses.isEmpty()){
            Toast.makeText(this, getString(R.string.error_bus_404), Toast.LENGTH_SHORT).show();
            return;
        }

        map.setInfoWindowAdapter(new BusInfoWindowAdapter(this));
        MapMarker marker = new MapMarker(map);
        marker.addMarkers(buses);
        LatLngBounds.Builder builder = marker.getBoundsBuilder();

        if(currentLocation!=null){
            LatLng userPosition = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            markUserPosition(userPosition);
            builder.include(userPosition);
        }

        LatLngBounds bounds = builder.build();

        int padding = 100; // offset from edges of the map in pixels
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);

        map.moveCamera(cu);
        map.animateCamera(cu);

    }

    @Override
    public void onMapReady(GoogleMap map) {}

    private void moveCameraToPosition(LatLng position) {
        LatLng location = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 13));
    }

    @Override
    public void onConnected(Bundle bundle) {
        currentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if(currentLocation != null) {
            LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());

            moveCameraToPosition(latLng);

            markUserPosition(latLng);
        }
    }

    @Override
    public void onConnectionSuspended(int i){}

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) { }
}
