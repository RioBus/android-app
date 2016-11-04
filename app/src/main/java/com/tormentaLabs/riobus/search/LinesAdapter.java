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

class LinesAdapter extends RecyclerView.Adapter {

    private static final int DEFAULT_ITEM = 999;
    private static final int HEADER_ITEM = 998;
    private static final int RECENT_ITEM = 997;

    private List<Line> defaultList = new ArrayList<>();
    private List<Line> recents = new ArrayList<>();
    private List<Line> items = new ArrayList<>();
    private Context context;
    private OnLineInteractionListener mListener;
    private LayoutInflater inflater;

    LinesAdapter(Context context, OnLineInteractionListener listener, List<Line> items) {
        this.context = context;
        mListener = listener;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.defaultList = items;

        this.items.add(new Line(context.getString(R.string.fragment_available_lines_header), ""));
        this.items.addAll(this.defaultList);
    }

    LinesAdapter(Context context, OnLineInteractionListener listener, List<Line> items, List<Line> recents) {
        this.context = context;
        mListener = listener;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.defaultList = items;
        this.recents = recents;

        this.items.add(new Line(context.getString(R.string.fragment_recent_searches_header), ""));
        this.items.addAll(recents);
        this.items.add(new Line(context.getString(R.string.fragment_available_lines_header), ""));
        this.items.addAll(items);
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position).getDescription().equals("")) return HEADER_ITEM;
        else if (recents.size()>0 && position < recents.size()+1) return RECENT_ITEM;
        else return DEFAULT_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        switch (viewType) {

            case RECENT_ITEM:
                view = inflater.inflate(R.layout.search_adapter_list_recent, parent, false);
                break;

            case HEADER_ITEM:
                view = inflater.inflate(R.layout.search_adapter_list_header, parent, false);
                break;

            default:
                view = inflater.inflate(R.layout.search_adapter_list_item_default, parent, false);
                break;
        }
        return new LineViewHolder(mListener, view);
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
