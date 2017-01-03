package com.tormentaLabs.riobus.search;

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

class LineViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = LineViewHolder.class.getName();
    @BindView(R.id.txtTitle) TextView txtTitle;
    private TextView txtDescription;
    private ImageView btnStar;

    private OnLineInteractionListener mListener;
    private Context context;
    private Line line;

    LineViewHolder(Context context, OnLineInteractionListener mListener, View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.mListener = mListener;
        this.context = context;
        try {
            txtDescription = (TextView) itemView.findViewById(R.id.txtDescription);
            btnStar = (ImageView) itemView.findViewById(R.id.btnStar);
            btnStar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onStarClick(view);
                }
            });
        } catch (Exception e) {
            Log.e(TAG, e.getLocalizedMessage());
        }

    }

    void setLine(Line line) {
        this.line = line;
        txtTitle.setText(line.getLine());
        if (txtDescription != null)
            txtDescription.setText(line.getDescription());
        if (btnStar != null) setupStar();
    }

    @OnClick(R.id.item_container)
    void onHolderClick(View v) {
        if (!line.getDescription().equals(""))
            mListener.onLineInteraction(line);
    }

    private void setupStar() {
        try {
            FavoritesDAO dao = new FavoritesDAO(context);
            if (dao.isFavorite(line.getLine())) btnStar.setImageResource(R.drawable.ic_favorite);
            else btnStar.setImageResource(R.drawable.ic_not_favorite);
            dao.close();
        } catch (SnappydbException e) {
            Log.e(TAG, e.getLocalizedMessage());
        }
    }

    private void onStarClick(View v) {
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
        Log.d(TAG, "Adding "+line.getLine()+" to favorites...");
        dao.addFavorite(line.getLine(), line);
        view.setImageResource(R.drawable.ic_favorite);
    }

    private void removeFromFavorites(FavoritesDAO dao, ImageView view) throws SnappydbException {
        Log.d(TAG, "Removing "+line.getLine()+" from favorites...");
        dao.removeFavorite(line.getLine());
        view.setImageResource(R.drawable.ic_not_favorite);
    }
}
