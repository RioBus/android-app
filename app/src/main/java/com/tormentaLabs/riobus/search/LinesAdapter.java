package com.tormentaLabs.riobus.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tormentaLabs.riobus.R;
import com.tormentaLabs.riobus.common.models.Line;

import java.util.ArrayList;
import java.util.List;

class LinesAdapter extends BaseAdapter {

    private List<Line> defaultList = new ArrayList<>();
    private List<Line> recents = new ArrayList<>();
    private List<Line> items = new ArrayList<>();
    private Context context;
    private LayoutInflater inflater;
    private boolean hasRecents = false;

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
        hasRecents = true;
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
        if (line.getDescription().equals("")) return getHeader(line, i);
        else if (hasRecents && i < recents.size()+1) return getRecentsView(line);
        else return getAvailableView(line);
    }

    private View getHeader(Line line, int i) {
        View v = inflater.inflate(R.layout.search_adapter_list_header, null);
        TextView textView = (TextView) v.findViewById(R.id.header_text);
        textView.setText(line.getLine());
        return v;
    }

    private View getRecentsView(Line line) {
        View v = inflater.inflate(R.layout.search_adapter_list_recent, null);
        final TextView txtLine = (TextView) v.findViewById(R.id.txtLine);
        txtLine.setText(line.getLine());
        final TextView txtDescription = (TextView) v.findViewById(R.id.txtDescription);
        txtDescription.setText(line.getDescription());

        return v;
    }

    private View getAvailableView(Line line) {
        View v = inflater.inflate(R.layout.search_adapter_list_item_default, null);
        final TextView txtLine = (TextView) v.findViewById(R.id.txtLine);
        txtLine.setText(line.getLine());
        final TextView txtDescription = (TextView) v.findViewById(R.id.txtDescription);
        txtDescription.setText(line.getDescription());
        final ImageView btnStar = (ImageView) v.findViewById(R.id.btnStar);
        btnStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnStar.setImageResource(R.drawable.ic_favorite);
            }
        });

        return v;
    }
}
