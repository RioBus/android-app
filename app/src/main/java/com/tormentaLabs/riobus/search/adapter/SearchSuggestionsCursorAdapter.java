package com.tormentaLabs.riobus.search.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tormentaLabs.riobus.R;
import com.tormentaLabs.riobus.core.controller.LineController;
import com.tormentaLabs.riobus.core.model.LineModel;
import com.tormentaLabs.riobus.core.utils.CoreUtils;
import com.tormentaLabs.riobus.favorite.controller.FavoriteController;

/**
 * Created by limazix on 12/01/16.
 */
public class SearchSuggestionsCursorAdapter extends CursorAdapter {

    private FavoriteController favoriteCtrl;
    private LineController lineCtrl;

    public SearchSuggestionsCursorAdapter(Context context, Cursor c) {
        super(context, c);
        favoriteCtrl = new FavoriteController();
        lineCtrl = new LineController();
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.view_search_suggestions_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView title = (TextView) view.findViewById(R.id.searchSuggestionItemTitle);
        String lineNumber = cursor.getString(cursor.getColumnIndex(CoreUtils.TABLE_LINES_COL_NUMBER));
        title.setText(lineNumber);;

        TextView subTitle = (TextView) view.findViewById(R.id.searchSuggestionItemSubTitle);

        ImageView star = (ImageView) view.findViewById(R.id.searchSuggestionItemStar);
        LineModel line = lineCtrl.getLine(lineNumber);

        if(favoriteCtrl.isFavorite(line))
            star.setImageResource(R.drawable.ic_favorite);
        else
            star.setImageResource(R.drawable.ic_not_favorite);
    }
}
