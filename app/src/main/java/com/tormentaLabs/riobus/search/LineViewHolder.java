package com.tormentaLabs.riobus.search;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.tormentaLabs.riobus.R;
import com.tormentaLabs.riobus.common.interfaces.OnLineInteractionListener;
import com.tormentaLabs.riobus.common.models.Line;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

class LineViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.txtTitle) TextView txtTitle;
    TextView txtDescription;

    private OnLineInteractionListener mListener;
    private Line line;

    LineViewHolder(OnLineInteractionListener mListener, View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.mListener = mListener;
        try {
            txtDescription = (TextView) itemView.findViewById(R.id.txtDescription);
        } catch (Exception e) {}

    }

    void setLine(Line line) {
        this.line = line;
        txtTitle.setText(line.getLine());
        if (txtDescription != null)
            txtDescription.setText(line.getDescription());
    }

    void setTitle(String title) {
        txtTitle.setText(title);
    }

    void setDescription(String description) {
        if (txtDescription != null)
            txtDescription.setText(description);
    }

    @OnClick(R.id.item_container)
    void onHolderClick(View v) {
        if (!line.getDescription().equals(""))
            mListener.onLineInteraction(line);
    }
}
