package com.tormentaLabs.riobus.search;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.tormentaLabs.riobus.R;
import com.tormentaLabs.riobus.common.models.Line;

import java.util.ArrayList;
import java.util.List;

class LinesAdapter extends RecyclerView.Adapter {

    static final int DEFAULT_ITEM = 999;
    static final int HEADER_ITEM = 998;
    static final int RECENT_ITEM = 997;

    private List<Line> defaultList = new ArrayList<>();
    private List<Line> recents = new ArrayList<>();
    private List<Line> items = new ArrayList<>();
    private Context context;
    private LayoutInflater inflater;

    LinesAdapter(Context context, List<Line> items) {
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.defaultList = items;

        this.items.add(new Line(context.getString(R.string.fragment_available_lines_header), ""));
        this.items.addAll(this.defaultList);
    }

    LinesAdapter(Context context, List<Line> items, List<Line> recents) {
        this.context = context;
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

    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * {@link #onBindViewHolder(ViewHolder, int, List)}. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ViewHolder, int)
     */
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
        return new LineViewHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link LineViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     * <p>
     * Override {@link LinesAdapter#onBindViewHolder(RecyclerView.ViewHolder, int, List)} instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Line line = items.get(position);
        LineViewHolder tmp = (LineViewHolder) holder;
        tmp.setTitle(line.getLine());
        tmp.setDescription(line.getDescription());
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
