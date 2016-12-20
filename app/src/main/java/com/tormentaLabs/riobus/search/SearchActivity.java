package com.tormentaLabs.riobus.search;

import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.snappydb.SnappydbException;
import com.tormentaLabs.riobus.R;
import com.tormentaLabs.riobus.about.AboutActivity;
import com.tormentaLabs.riobus.common.dao.HistoryDAO;
import com.tormentaLabs.riobus.common.interfaces.OnLineInteractionListener;
import com.tormentaLabs.riobus.common.models.Line;
import com.tormentaLabs.riobus.favorites.FavoritesActivity;
import com.tormentaLabs.riobus.history.HistoryActivity;
import com.tormentaLabs.riobus.map.MapsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnLineInteractionListener {

    private static final String TAG = SearchActivity.class.getName();

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    @BindView(R.id.nav_view) NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        FragmentManager fragmentManager = getSupportFragmentManager();

        Fragment mainFragment = fragmentManager.findFragmentById(R.id.main_container);
        if (mainFragment == null) {
            mainFragment = MainFragment.newInstance();
            fragmentManager.beginTransaction().replace(R.id.main_container, mainFragment).commit();
        }

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
        // Retrieve the SearchView and plug it into SearchManager
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.

        switch (item.getItemId()) {

            case R.id.nav_favorites:
                openFavorites();
                break;
            case R.id.nav_history:
                openHistory();
                break;
            case R.id.nav_send_feedback:
                openEmailForFeedback();
                break;
            case R.id.nav_about:
                openAbout();
                break;
            case R.id.nav_rate_app:
                openPlayStore();
                break;
            case R.id.nav_like_facebook:
                openFacebook();
                break;
            default: break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    @Override
    public void onLineInteraction(Line line) {
        if (line != null) {
            addToHistory(line);
            Intent intent = new Intent(this, MapsActivity.class);
            intent.putExtra(MapsActivity.LINE_TITLE, line.getLine());
            intent.putExtra(MapsActivity.LINE_DESCRIPTION, line.getDescription());
            startActivity(intent);
        }
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

    private void openFavorites() {
        startActivity(new Intent(this, FavoritesActivity.class));
    }

    private void openHistory() {
        startActivity(new Intent(this, HistoryActivity.class));
    }

    private void openEmailForFeedback() {
        String feedbackEmail = "fred+androidfeedback@riob.us";
        String subject = "Vamos falar sobre o app";
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, feedbackEmail);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        Intent mailer = Intent.createChooser(intent, null);
        startActivity(mailer);
    }

    private void openAbout() {
        startActivity(new Intent(this, AboutActivity.class));
    }

    private void openPlayStore() {
        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    private void openFacebook() {
        Intent intent;
        try {
            getPackageManager().getPackageInfo("com.facebook.katana", 0);
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/1408367169433222"));
        } catch (Exception e) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/1408367169433222"));
        }
        startActivity(intent);
    }


}
