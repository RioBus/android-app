package com.tormentaLabs.riobus.favorite.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.tormentaLabs.riobus.R;
import com.tormentaLabs.riobus.core.model.LineModel;
import com.tormentaLabs.riobus.favorite.controller.FavoriteController;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * @author limazix
 * @since 3.0
 * Created on 19/11/15
 */

@EViewGroup(R.layout.view_favorite_star_button)
public class FavoriteStarView extends RelativeLayout implements View.OnClickListener {

    private static final String TAG = FavoriteStarView.class.getName();
    private boolean isFavorite = false;
    private LineModel line;

    @ViewById(R.id.favoriteStar)
    ImageView favoriteStar;

    @Bean
    FavoriteController favoriteController;

    public FavoriteStarView(Context context) {
        super(context);
    }

    public FavoriteStarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FavoriteStarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void build(LineModel line) {
        this.line = line;

        isFavorite = favoriteController.isFavorite(line);

        updateIcon();

        favoriteStar.setOnClickListener(this);
    }

    private void updateIcon() {
        if(isFavorite)
            favoriteStar.setImageResource(R.drawable.ic_favorite);
        else
            favoriteStar.setImageResource(R.drawable.ic_not_favorite);
    }

    @Override
    public void onClick(View view) {
        if(isFavorite) {
            favoriteController.removeFromFavorites(line);
            isFavorite = false;
        } else {
            favoriteController.addToFavorites(line);
            isFavorite = true;
        }
        updateIcon();
    }
}
