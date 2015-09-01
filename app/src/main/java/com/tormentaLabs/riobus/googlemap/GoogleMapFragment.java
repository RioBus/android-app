package com.tormentaLabs.riobus.googlemap;

import android.support.v4.app.Fragment;

import com.tormentaLabs.riobus.R;
import com.tormentaLabs.riobus.RioBusActivity_;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;

/**
 * Created by limazix on 01/09/15.
 */
@EFragment(R.layout.fragment_google_map)
public class GoogleMapFragment extends Fragment {

    @Click(R.id.sidemenu_toggle)
    public void sidemenuToggle() {
        ((RioBusActivity_)getActivity()).sidemenuToggle();
    }

}
