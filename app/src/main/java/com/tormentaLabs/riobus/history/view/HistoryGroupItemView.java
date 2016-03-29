package com.tormentaLabs.riobus.history.view;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tormentaLabs.riobus.R;
import com.tormentaLabs.riobus.favorite.view.FavoriteStarView;
import com.tormentaLabs.riobus.history.listener.OnHistoryItemClickListener;
import com.tormentaLabs.riobus.history.model.HistoryModel;
import com.tormentaLabs.riobus.utils.RioBusUtils;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.joda.time.DateTime;

/**
 * Used to expose history data
 * @author limazix
 * @since 3.0
 * Created at 24/10/15
 */
@EViewGroup(R.layout.view_history_group_item)
public class HistoryGroupItemView extends RelativeLayout implements View.OnClickListener {

    private OnHistoryItemClickListener listener;
    private HistoryModel historyItem;

    @ViewById(R.id.historyFavoriteStar)
    FavoriteStarView favoriteStar;

    @ViewById(R.id.historyLineTitle)
    TextView lineTitle;

    @ViewById(R.id.historyLineDate)
    TextView lineDate;

    public HistoryGroupItemView(Context context) {
        super(context);
    }

    public void bind(HistoryModel historyItem) {
        this.historyItem = historyItem;
        lineTitle.setText(historyItem.line.number);
        DateTime date = DateTime.parse(historyItem.createdAt);
        lineDate.setText(RioBusUtils.parseDateToString(date, RioBusUtils.PATTERN_FORMAT_TIME));
        favoriteStar.build(historyItem.line);
        setOnClickListener(this);
    }

    public void setOnHistoryItemClickListener(OnHistoryItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener != null)
            listener.onHistoryItemClicked(historyItem);
    }

}
