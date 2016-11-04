package com.tormentaLabs.riobus.search;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.tormentaLabs.riobus.R;

class LineViewHolder extends RecyclerView.ViewHolder {

    private TextView txtTitle;
    private TextView txtDescription;

    LineViewHolder(View itemView) {
        super(itemView);
        txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
        try {
            txtDescription = (TextView) itemView.findViewById(R.id.txtDescription);
        } catch (Exception e) {}
    }

    public void setTitle(String title) {
        txtTitle.setText(title);
    }

    public void setDescription(String description) {
        if (txtDescription != null)
            txtDescription.setText(description);
    }
}
