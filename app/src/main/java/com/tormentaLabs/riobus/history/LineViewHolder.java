package com.tormentaLabs.riobus.history;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.tormentaLabs.riobus.R;
import com.tormentaLabs.riobus.common.models.Line;

import butterknife.BindView;
import butterknife.ButterKnife;


class LineViewHolder extends ChildViewHolder {

    @BindView(R.id.txtTitle) TextView txtTitle;
    @BindView(R.id.txtDescription) TextView txtDescription;

    LineViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    void bind(Line line) {
        txtTitle.setText(line.getLine());
        txtDescription.setText(line.getDescription());
    }
}
