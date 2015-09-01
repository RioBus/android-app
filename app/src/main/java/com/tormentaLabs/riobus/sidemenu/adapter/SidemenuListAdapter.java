package com.tormentaLabs.riobus.sidemenu.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.tormentaLabs.riobus.R;
import com.tormentaLabs.riobus.sidemenu.view.SidemenuItemView;
import com.tormentaLabs.riobus.sidemenu.view.SidemenuItemView_;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

/**
 * Created by limazix on 31/08/15.
 */
@EBean
public class SidemenuListAdapter extends BaseAdapter {

    private static final String TAG = SidemenuListAdapter.class.getName();
    private String[] items;

    @RootContext
    Context context;

    @AfterInject
    public void afterInject() {
        items = context.getResources().getStringArray(R.array.menu_items);
    }

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public Object getItem(int i) {
        return items[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        SidemenuItemView sidemenuItemView;
        if(view == null)
            sidemenuItemView = SidemenuItemView_.build(context);
        else
            sidemenuItemView = (SidemenuItemView) view;

        sidemenuItemView.bind(items[i]);

        return sidemenuItemView;
    }
}
