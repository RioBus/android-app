package com.tormentaLabs.riobus.history;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.tormentaLabs.riobus.R;
import com.tormentaLabs.riobus.common.models.Line;

import java.util.List;

class HistoryAdapter extends ExpandableRecyclerAdapter<DateItemViewHolder, LineViewHolder> {

    private LayoutInflater mInflator;

    HistoryAdapter(Context context, @NonNull List<DateItem> parentItemList) {
        super(parentItemList);
        mInflator = LayoutInflater.from(context);
    }

    @Override
    public DateItemViewHolder onCreateParentViewHolder(ViewGroup parentViewGroup) {
        View view = mInflator.inflate(R.layout.history_adapter_parent, parentViewGroup, false);
        return new DateItemViewHolder(view);
    }

    @Override
    public LineViewHolder onCreateChildViewHolder(ViewGroup childViewGroup) {
        View view = mInflator.inflate(R.layout.history_adapter_child, childViewGroup, false);
        return new LineViewHolder(view);
    }

    @Override
    public void onBindParentViewHolder(DateItemViewHolder recipeViewHolder, int position, ParentListItem parentListItem) {
        DateItem dateItem = (DateItem) parentListItem;
        recipeViewHolder.bind(dateItem);
    }

    @Override
    public void onBindChildViewHolder(LineViewHolder ingredientViewHolder, int position, Object childListItem) {
        Line line = (Line) childListItem;
        ingredientViewHolder.bind(line);
    }
}