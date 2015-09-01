package com.tormentaLabs.riobus.sidemenu.view;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tormentaLabs.riobus.R;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by limazix on 31/08/15.
 */
@EViewGroup(R.layout.menu_list_item)
public class SidemenuItemView extends RelativeLayout {

    @ViewById(R.id.sidemenu_item_label)
    TextView menuItemLabel;

    public SidemenuItemView(Context context) {
        super(context);
    }

    public void bind(String label) {
        menuItemLabel.setText(label);
    }
}
