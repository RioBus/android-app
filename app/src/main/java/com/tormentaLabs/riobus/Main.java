package com.tormentaLabs.riobus;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tormentaLabs.riobus.asyncTasks.BusSearchTask;
import com.tormentaLabs.riobus.domain.Bus;
import com.tormentaLabs.riobus.common.BusDataReceptor;
import com.tormentaLabs.riobus.common.Util;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;

@EActivity(R.layout.mapa)
public class Main extends ActionBarActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, BusDataReceptor {

    @ViewById
    public AutoCompleteTextView search;
    public GoogleMap map; // Might be null if Google Play services APK is not available.
    public GoogleApiClient mGoogleApiClient;

    Location currentLocation;
    MarkerOptions userMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildGoogleApiClient();
    }

    @AfterViews
    public void onViewCreatedByAA() {
        setUpMapIfNeeded();
        setSuggestions(); // Shows the previous searched lines
        getSupportActionBar().hide();
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onResume() {
        super.onResume();
        clearMap();
    }

    @Override
    protected void onStop() {
        clearMap();
        super.onStop();
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
        // Do a null check to confirm that we have not already instantiated the map.
        if (map == null) {
            // Try to obtain the map from the SupportMapFragment.
            map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();

            // Hide the zoom controls in the bottom of the screen
            map.getUiSettings().setZoomControlsEnabled(false);
            // Check if we were successful in obtaining the map.
            if (map != null) {
                setUpMap();
            }
        }

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

    void clearMap(){
        if(map != null)
            map.clear();
    }

    private void setUpMap() {
        clearMap();
        map.setTrafficEnabled(true);
    }

    public void atualizaMapaLocalizacao(Location location) {
        LatLng position = new LatLng(location.getLatitude(), location.getLongitude());
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

    @Click(R.id.button_about)
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
    public void onConnected(Bundle bundle) {
        currentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        atualizaMapaLocalizacao(currentLocation);
    }

    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult){}

    @Override
    public void retrieveBusData(List<Bus> buses) {
        System.out.println(buses.size());
    }
}
