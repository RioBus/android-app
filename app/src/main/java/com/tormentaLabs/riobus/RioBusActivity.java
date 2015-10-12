package com.tormentaLabs.riobus;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.tormentaLabs.riobus.sidemenu.adapter.SidemenuListAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_rio_bus)
public class RioBusActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = RioBusActivity_.class.getName();

    @Bean
    SidemenuListAdapter sidemenuListAdapter;

    @ViewById(R.id.riobusToolbar)
    Toolbar rioBusToolBar;

    @ViewById(R.id.rio_bus_drawer_layout)
    DrawerLayout rioBusDrawerLayout;
/*
    @ViewById(R.id.sidemenu_drawer_list)
    ListView sidemenuDrawerList;
*/
    @AfterViews
    public void afterViews() {
        setupToolBar();
        //sidemenuDrawerList.setAdapter(sidemenuListAdapter);
    }

    private void setupToolBar() {
        setSupportActionBar(rioBusToolBar);
        rioBusToolBar.setNavigationIcon(R.drawable.ic_menu);
        rioBusToolBar.setNavigationOnClickListener(this);
    }
    /*
    @ItemClick(R.id.sidemenu_drawer_list)
    public void sidemenuItemClicked(String item) {
        rioBusDrawerLayout.closeDrawer(Gravity.LEFT);
        Toast.makeText(this, item, Toast.LENGTH_LONG).show();
    }
*/
    @Override
    public void onClick(View view) {
        if(rioBusDrawerLayout.isDrawerOpen(Gravity.LEFT))
            rioBusDrawerLayout.closeDrawer(Gravity.LEFT);
        else
            rioBusDrawerLayout.openDrawer(Gravity.LEFT);
    }
}
