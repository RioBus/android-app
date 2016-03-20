package com.tormentaLabs.riobus.favorite.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.tormentaLabs.riobus.core.model.LineModel;
import com.tormentaLabs.riobus.favorite.controller.FavoriteController;
import com.tormentaLabs.riobus.favorite.model.FavoriteModel;
import com.tormentaLabs.riobus.favorite.view.FavoriteView;
import com.tormentaLabs.riobus.favorite.view.FavoriteView_;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

/**
 * @author limazix
 * @since 3.0
 */
@EBean
public class FavoriteAdapter extends BaseAdapter {

    private ArrayList<FavoriteModel> favorites;

    @Bean
    FavoriteController favoriteController;

    @RootContext
    Context context;

    @AfterInject
    void afterInject() {
        favorites = (ArrayList<FavoriteModel>) favoriteController.getAllFavorites();
    }

    @Override
    public int getCount() {
        return favorites.size();
    }

    @Override
    public FavoriteModel getItem(int i) {
        return favorites.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        FavoriteView favoriteView;

        if(view == null) {
            favoriteView = FavoriteView_.build(context);
        } else {
            favoriteView = (FavoriteView) view;
        }

        favoriteView.bind(getItem(i));

        return favoriteView;
    }

    public void updateFavorites(ArrayList<FavoriteModel> favorites) {
        this.favorites = favorites;
        notifyDataSetChanged();
    }
}
