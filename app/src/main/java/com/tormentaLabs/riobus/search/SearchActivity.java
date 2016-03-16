package com.tormentaLabs.riobus.search;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.tormentaLabs.riobus.R;
import com.tormentaLabs.riobus.search.utils.SearchUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.ViewById;

/**
 * TODO Add discription here
 *
 * @author limazix
 * @since 3.0.0
 * Created on 14/03/16
 */
@OptionsMenu(R.menu.activity_search)
@EActivity(R.layout.activity_search)
public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private static final String TAG = SearchActivity.class.getName();
    private SearchView searchView;

    @ViewById(R.id.riobusSearchToolbar)
    Toolbar rioBusToolBar;

    @ViewById(R.id.favoriteListHeader)
    TextView favoriteListHeader;

    @ViewById(R.id.favoriteList)
    ListView favoriteList;

    @ViewById(R.id.historyListHeader)
    TextView historyListHeader;

    @ViewById(R.id.historyList)
    ListView historyList;

    @OptionsMenuItem(R.id.search)
    MenuItem menuSearch;

    @AfterViews
    void afterViews() {
        setSupportActionBar(rioBusToolBar);

        if(searchView != null) {
            searchView.setFocusable(true);
            searchView.setIconified(false);
            searchView.requestFocusFromTouch();
        }
    }

    @OptionsItem(R.id.search)
    boolean menuSearch() {
        searchView = (SearchView) MenuItemCompat.getActionView(menuSearch);
        searchView.setOnQueryTextListener(this);

        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Intent i = new Intent();
        i.putExtra(SearchUtils.EXTRA_SEARCH_RESULT, query);
        setResult(Activity.RESULT_OK, i);
        finish();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.e(TAG, newText);
        return false;
    }

}
