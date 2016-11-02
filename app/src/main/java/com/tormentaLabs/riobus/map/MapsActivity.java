package com.tormentaLabs.riobus.map;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.drive.internal.StringListResponse;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tormentaLabs.riobus.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapsController controller;

    @BindView(R.id.txt_from) TextView txtFrom;
    @BindView(R.id.txt_to) TextView txtTo;

    public static final String LINE_TITLE = "lineTitle";
    public static final String LINE_DESCRIPTION = "lineDescription";
    private String queryString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        queryString = getIntent().getStringExtra(LINE_TITLE);
        setTitle(queryString);

        String description = getIntent().getStringExtra(LINE_DESCRIPTION);
        if (description.contains(" X ")) {
            String[] tmp = getIntent().getStringExtra(LINE_DESCRIPTION).split(" X ");
            txtFrom.setText(tmp[0]);
            txtTo.setText(tmp[1]);
        } else {
            txtFrom.setText(getString(R.string.map_snackbar_sense));
            txtTo.setText(getString(R.string.maps_snackbar_unknown));
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        controller.getMap().addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        controller.getMap().moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
