package com.tormentaLabs.riobus.googlemap;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.tormentaLabs.riobus.R;
import com.tormentaLabs.riobus.RioBusActivity_;
import com.tormentaLabs.riobus.googlemap.utils.RioBusGoogleMapUtils;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.EditorAction;
import org.androidannotations.annotations.SystemService;

/**
 * Created by limazix on 01/09/15.
 */
@EFragment(R.layout.fragment_google_map)
public class GoogleMapFragment extends Fragment {

    private static final String TAG = GoogleMapFragment_.class.getName();

    @SystemService
    InputMethodManager inputMethodManager;

    @EditorAction(R.id.search)
    public void onPerformSearch(TextView textView, int actionId, KeyEvent event) {
        String keyword = textView.getText().toString();
        if(event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && RioBusGoogleMapUtils.isValidString(keyword)) {
            inputMethodManager.hideSoftInputFromWindow(textView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    };

    @Click(R.id.sidemenu_toggle)
    public void sidemenuToggle() {
        ((RioBusActivity_)getActivity()).sidemenuToggle();
    }

}
