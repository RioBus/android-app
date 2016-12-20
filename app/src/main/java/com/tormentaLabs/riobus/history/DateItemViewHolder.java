package com.tormentaLabs.riobus.history;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.tormentaLabs.riobus.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


class DateItemViewHolder extends ParentViewHolder {

    @BindView(R.id.txtTitle) TextView txtTitle;

    DateItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    void bind(DateItem dateItem) {
        txtTitle.setText(dateItem.getTitle());
    }

    @OnClick(R.id.collapseArrow)
    void onClickArrow(View view) {
        if (isExpanded()) collapseView();
        else expandView();
    }
}
