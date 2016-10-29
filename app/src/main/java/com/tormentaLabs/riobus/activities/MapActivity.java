package com.tormentaLabs.riobus.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tormentaLabs.riobus.R;

public class MapActivity extends AppCompatActivity {

    public static final String LINE_TITLE = "lineTitle";
    private String queryString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        queryString = getIntent().getStringExtra(LINE_TITLE);
        setTitle(queryString);
    }
}
