package com.tormentaLabs.riobus.search;

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

class SearchAdapter extends RecyclerView.Adapter {

    private List<Line> items = new ArrayList<>();
    private Context context;
    private OnLineInteractionListener mListener;
    private LayoutInflater inflater;

    SearchAdapter(Context context, OnLineInteractionListener listener, List<Line> items) {
        this.context = context;
        mListener = listener;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.items.addAll(items);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.search_adapter_list_item_default, parent, false);
        return new LineViewHolder(context, mListener, view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Line line = items.get(position);
        LineViewHolder tmp = (LineViewHolder) holder;
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
