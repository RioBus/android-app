package com.tormentaLabs.riobus.favorites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.snappydb.SnappydbException;
import com.tormentaLabs.riobus.R;
import com.tormentaLabs.riobus.common.dao.FavoritesDAO;
import com.tormentaLabs.riobus.common.dao.HistoryDAO;
import com.tormentaLabs.riobus.common.interfaces.OnLineInteractionListener;
import com.tormentaLabs.riobus.common.models.Line;
import com.tormentaLabs.riobus.map.MapsActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoritesActivity extends AppCompatActivity implements OnLineInteractionListener {

    private static final String TAG = FavoritesActivity.class.getName();

    @BindView(R.id.favorites_list) RecyclerView favoritesList;

    private List<Line> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorites_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            FavoritesDAO dao = new FavoritesDAO(getBaseContext());
            items = dao.getFavorites();
            dao.close();
            updateView();
        } catch (SnappydbException e) {
            Log.e(TAG, e.getLocalizedMessage());
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

    private void updateView() {
        FavoritesAdapter adapter = new FavoritesAdapter(getBaseContext(), this, items);
        favoritesList.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
        favoritesList.setLayoutManager(layoutManager);
    }

    @Override
    public void onLineInteraction(Line line) {
        addToHistory(line);
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra(MapsActivity.LINE_TITLE, line.getLine());
        intent.putExtra(MapsActivity.LINE_DESCRIPTION, line.getDescription());
        startActivity(intent);
    }

    private void addToHistory(Line line) {
        try {
            HistoryDAO dao = new HistoryDAO(getBaseContext());
            dao.addSearch(line);
            dao.close();
        } catch (SnappydbException e) {
            Log.e(TAG, e.getLocalizedMessage());
        }
    }
}
