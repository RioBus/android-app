package com.tormentaLabs.riobus.favorite;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.tormentaLabs.riobus.R;
import com.tormentaLabs.riobus.core.model.LineModel;
import com.tormentaLabs.riobus.favorite.adapter.FavoriteAdapter;
import com.tormentaLabs.riobus.favorite.listener.OnFavoriteStatusChangedListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Class used to list all favorite lines
 * @author limazix
 * @since 3.0
 * @// TODO: 10/01/16 ADD Search and endless scroll list
 */
@EActivity(R.layout.activity_favorite)
public class FavoriteActivity extends AppCompatActivity {

    @ViewById(R.id.riobusFavoriteToolbar)
    Toolbar rioBusToolBar;

    @Bean
    FavoriteAdapter favoriteAdapter;

    @ViewById(R.id.favorityList)
    ListView favoriteList;

    @AfterViews
    void afterViews() {
        setSupportActionBar(rioBusToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        favoriteList.setAdapter(favoriteAdapter);
    }

}
