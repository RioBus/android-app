package com.tormentaLabs.riobus.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tormentaLabs.riobus.R;
import com.tormentaLabs.riobus.models.Line;

import java.util.ArrayList;
import java.util.List;

public class LinesAdapter extends BaseAdapter {

    private List<Line> items = new ArrayList<Line>();
    private Context context;

    public LinesAdapter(Context context, List<Line> items) {
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
        View v = inflater.inflate(R.layout.line_item_adapter, null);

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
