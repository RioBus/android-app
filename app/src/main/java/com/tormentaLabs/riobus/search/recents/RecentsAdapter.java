package com.tormentaLabs.riobus.search.recents;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tormentaLabs.riobus.R;
import com.tormentaLabs.riobus.common.models.Line;

import java.util.ArrayList;
import java.util.List;

public class RecentsAdapter extends BaseAdapter {

    private List<Line> items = new ArrayList<Line>();
    private Context context;

    public RecentsAdapter(Context context, List<Line> items) {
        this.context = context;
        if (items.size()>0) this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Line getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Line line = items.get(i);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.fragment_recent_search_item_adapter, null);

        TextView txtLine = (TextView) v.findViewById(R.id.txtLine);
        txtLine.setText(line.getLine());
        TextView txtDescription = (TextView) v.findViewById(R.id.txtDescription);
        txtDescription.setText(line.getDescription());

        return v;
    }
}
