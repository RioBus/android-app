package com.tormentaLabs.riobus.favorites;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.snappydb.SnappydbException;
import com.tormentaLabs.riobus.R;
import com.tormentaLabs.riobus.common.dao.FavoritesDAO;
import com.tormentaLabs.riobus.common.interfaces.OnLineInteractionListener;
import com.tormentaLabs.riobus.common.models.Line;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

class FavoriteViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = FavoriteViewHolder.class.getName();

    @BindView(R.id.btnStar) ImageView btnStar;
    @BindView(R.id.txtDescription) TextView txtDescription;
    @BindView(R.id.txtTitle) TextView txtTitle;

    private OnLineInteractionListener mListener;
    private Context context;
    private Line line;

    FavoriteViewHolder(Context context, OnLineInteractionListener mListener, View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.mListener = mListener;
        this.context = context;
        btnStar.setImageResource(R.drawable.ic_favorite);
    }

    void setLine(Line line) {
        this.line = line;
        txtTitle.setText(line.getLine());
        if (txtDescription != null)
            txtDescription.setText(line.getDescription());
    }

    @OnClick(R.id.item_container)
    void onHolderClick(View v) {
        if (!line.getDescription().equals(""))
            mListener.onLineInteraction(line);
    }

    @OnClick(R.id.btnStar)
    void onStarClick(View v) {
        try {
            FavoritesDAO dao = new FavoritesDAO(context);
            if (dao.isFavorite(line.getLine())) removeFromFavorites(dao, (ImageView) v);
            else addToFavorites(dao, (ImageView) v);
            dao.close();
        } catch (SnappydbException e) {
            Log.e(TAG, e.getLocalizedMessage());
        }
    }

    private void addToFavorites(FavoritesDAO dao, ImageView view) throws SnappydbException {
        dao.addFavorite(line.getLine(), line);
        view.setImageResource(R.drawable.ic_favorite);
    }

    private void removeFromFavorites(FavoritesDAO dao, ImageView view) throws SnappydbException {
        dao.removeFavorite(line.getLine());
        view.setImageResource(R.drawable.ic_not_favorite);
    }
}
