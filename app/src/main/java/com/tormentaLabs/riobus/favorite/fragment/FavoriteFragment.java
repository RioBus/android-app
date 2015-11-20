package com.tormentaLabs.riobus.favorite.fragment;

import android.support.v4.app.Fragment;
import android.widget.ListView;

import com.tormentaLabs.riobus.R;
import com.tormentaLabs.riobus.favorite.adapter.FavoriteAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Class used to list all favorite lines
 * @author limazix
 * @since 3.0
 * @// TODO: 19/11/15 ADD Search and endless scroll list
 */
@EFragment(R.layout.fragment_favorite)
public class FavoriteFragment extends Fragment {

    @Bean
    FavoriteAdapter favoriteAdapter;

    @ViewById(R.id.favorityList)
    ListView favoriteList;

    @AfterViews
    void afterViews() {
        favoriteList.setAdapter(favoriteAdapter);
    }
}
