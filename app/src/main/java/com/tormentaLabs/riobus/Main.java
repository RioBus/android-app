package com.tormentaLabs.riobus;

import android.app.Dialog;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.os.StrictMode;
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
import com.tormentaLabs.riobus.http.HttpRequest;
import com.tormentaLabs.riobus.http.HttpUrls;
import com.tormentaLabs.riobus.util.Util;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.mapa)
public class Main extends ActionBarActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    public GoogleMap map; // Might be null if Google Play services APK is not available.
    GoogleApiClient mGoogleApiClient;
    @ViewById
    public AutoCompleteTextView search;

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
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    if (isValidEntry(search.getText().toString())) {

                        if (Util.checkInternetConnection(Main.this)) {
                            Util.saveOnHistory(Main.this, search.getText().toString(), search);
                            setSuggestions(); //Updating the adapter
                            try {
                                String url = HttpUrls.urlRioBusLinhas+"search/2/"+search.getText().toString();
                                System.out.println(url);

                                StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().build());
                                StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().build());

                                HttpRequest response = HttpRequest.get(url);
                                response.acceptGzipEncoding().uncompress(true);
                                if(response.ok()){
                                    System.out.println(response.body());
                                }
                            } catch(HttpRequest.HttpRequestException e){
                                System.out.println(e.toString());
                            }
                        } else {
                            Toast.makeText(Main.this, getString(R.string.msg_conexao_internet), Toast.LENGTH_SHORT).show();
                        }
                    }
                    InputMethodManager imm = (InputMethodManager) getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(search.getWindowToken(), 0);
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

    private boolean isValidEntry(String entry) {
        return !(entry == null || entry.equals("") || entry.trim().equals(""));
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
            userMarker = new MarkerOptions().position(posicao).title(getString(R.string.marker_cliente)).icon(BitmapDescriptorFactory
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
}
