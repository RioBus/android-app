package com.tormentaLabs.riobus.favorite.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tormentaLabs.riobus.R;
import com.tormentaLabs.riobus.favorite.listener.OnFavoriteStatusChangedListener;
import com.tormentaLabs.riobus.favorite.model.FavoriteModel;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by limazix on 19/11/15.
 */
@EViewGroup(R.layout.view_favorite_item)
public class FavoriteView extends RelativeLayout {

    private static final String TAG = FavoriteView.class.getName();
    private FavoriteModel favorite;

    @ViewById(R.id.itemFavoriteStar)
    FavoriteStarView favoriteStar;

    @ViewById(R.id.favoriteLine)
    TextView favoriteLine;

    public FavoriteView(Context context) {
        super(context);
    }

    public void bind(FavoriteModel favorite, OnFavoriteStatusChangedListener listener) {
        this.favorite = favorite;
        favoriteLine.setText(favorite.line.number);
        favoriteStar.build(favorite.line, listener);
    }

}
