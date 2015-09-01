package com.tormentaLabs.riobus;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.ListView;
import android.widget.Toast;

import com.tormentaLabs.riobus.sidemenu.adapter.SidemenuListAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

@Fullscreen
@EActivity(R.layout.activity_rio_bus)
@OptionsMenu(R.menu.menu_rio_bus)
public class RioBusActivity extends AppCompatActivity {

    private static final String TAG = RioBusActivity_.class.getName();

    @Bean
    SidemenuListAdapter sidemenuListAdapter;

    @ViewById(R.id.rio_bus_drawer_layout)
    DrawerLayout rioBusDrawerLayout;

    @ViewById(R.id.sidemenu_drawer_list)
    ListView sidemenuDrawerList;

    @AfterViews
    public void afterViews() {
        sidemenuDrawerList.setAdapter(sidemenuListAdapter);
    }

    @OptionsItem(R.id.sidemenu_toggle)
    public void sidemenuToggle() {
        if(rioBusDrawerLayout.isDrawerOpen(Gravity.LEFT))
            rioBusDrawerLayout.closeDrawer(Gravity.LEFT);
        else
            rioBusDrawerLayout.openDrawer(Gravity.LEFT);
    }

    @ItemClick(R.id.sidemenu_drawer_list)
    public void sidemenuItemClicked(String item) {
        rioBusDrawerLayout.closeDrawer(Gravity.LEFT);
        Toast.makeText(this, item, Toast.LENGTH_LONG).show();
    }

}
