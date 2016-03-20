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
import com.tormentaLabs.riobus.core.controller.LineController;
import com.tormentaLabs.riobus.favorite.listener.OnFavoriteItemClickListener;
import com.tormentaLabs.riobus.favorite.model.FavoriteModel;
import com.tormentaLabs.riobus.search.adapter.SearchSuggestionsCursorAdapter;
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
        SearchView.OnQueryTextListener {

    private static final String TAG = SearchActivity.class.getName();
    private SearchView searchView;
    private SearchSuggestionsCursorAdapter suggestionsCursorAdapter;

    @Bean
    LineController lineController;

    @ViewById(R.id.riobusSearchToolbar)
    Toolbar rioBusToolBar;

    @ViewById(R.id.searchSuggestions)
    ListView searchSuggestions;

    @OptionsMenuItem(R.id.search)
    MenuItem menuSearch;

    @AfterViews
    void afterViews() {
        setSupportActionBar(rioBusToolBar);
        suggestionsCursorAdapter = new SearchSuggestionsCursorAdapter(this, lineController.fetchCursor());
        searchSuggestions.setAdapter(suggestionsCursorAdapter);
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
        if(!newText.isEmpty())
            suggestionsCursorAdapter.changeCursor(lineController.fetchCursor(newText));
        else
            suggestionsCursorAdapter.changeCursor(lineController.fetchCursor());
        
        return false;
    }

}
