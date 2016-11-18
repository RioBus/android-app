package com.tormentaLabs.riobus.favorites;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tormentaLabs.riobus.R;
import com.tormentaLabs.riobus.common.interfaces.OnLineInteractionListener;
import com.tormentaLabs.riobus.common.models.Line;

import java.util.ArrayList;
import java.util.List;

class FavoritesAdapter extends RecyclerView.Adapter {

    private OnLineInteractionListener mListener;
    private LayoutInflater inflater;
    private Context context;
    private List<Line> items = new ArrayList<>();

    FavoritesAdapter(Context context, OnLineInteractionListener listener, List<Line> items) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        mListener = listener;
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.search_adapter_list_item_default, parent, false);
        return new FavoriteViewHolder(context, mListener, view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Line line = items.get(position);
        FavoriteViewHolder tmp = (FavoriteViewHolder) holder;
        tmp.setLine(line);
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return items.size();
    }
}
