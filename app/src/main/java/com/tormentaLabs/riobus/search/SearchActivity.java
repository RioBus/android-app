package com.tormentaLabs.riobus.search;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.tormentaLabs.riobus.R;
import com.tormentaLabs.riobus.favorite.listener.OnFavoriteItemClickListener;
import com.tormentaLabs.riobus.favorite.model.FavoriteModel;
import com.tormentaLabs.riobus.search.utils.SearchUtils;
import com.tormentaLabs.riobus.favorite.adapter.FavoriteAdapter;
import com.tormentaLabs.riobus.favorite.controller.FavoriteController;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

/**
 * TODO Add discription here
 *
 * @author limazix
 * @since 3.0.0
 * Created on 14/03/16
 */
@OptionsMenu(R.menu.activity_search)
@EActivity(R.layout.activity_search)
public class SearchActivity extends AppCompatActivity implements
        SearchView.OnQueryTextListener, OnFavoriteItemClickListener {

    private static final String TAG = SearchActivity.class.getName();
    private SearchView searchView;

    @Bean
    FavoriteController favoriteController;

    @Bean
    FavoriteAdapter favoriteAdapter;

    @ViewById(R.id.riobusSearchToolbar)
    Toolbar rioBusToolBar;

    @ViewById(R.id.favoriteList)
    ListView favoriteList;

    @ViewById(R.id.historyList)
    ListView historyList;

    @OptionsMenuItem(R.id.search)
    MenuItem menuSearch;

    @AfterViews
    void afterViews() {
        setSupportActionBar(rioBusToolBar);

        favoriteList.setAdapter(favoriteAdapter);
    }

    @OptionsItem(R.id.search)
    boolean menuSearch() {
        searchView = (SearchView) MenuItemCompat.getActionView(menuSearch);
        searchView.setOnQueryTextListener(this);
        searchView.setIconifiedByDefault(false);
        searchView.requestFocus();

        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        sendToMap(query);
        return false;
    }

    private void sendToMap(String query) {
        Intent i = new Intent();
        i.putExtra(SearchUtils.EXTRA_SEARCH_RESULT, query);
        setResult(Activity.RESULT_OK, i);
        finish();
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if(!newText.isEmpty()) {
            ArrayList<FavoriteModel> favorites = (ArrayList < FavoriteModel >) favoriteController.searchFavorites(newText);
            favoriteAdapter.updateFavorites(favorites);
        }
        return false;
    }

    @Override
    public void onFavoriteItemClicked(FavoriteModel favorite) {
        sendToMap(favorite.line.number);
    }

    @Override
    public void onFavoriteItemClickedError(String errorMessage) {

    }
}
