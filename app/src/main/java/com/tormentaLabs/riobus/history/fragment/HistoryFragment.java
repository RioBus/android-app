package com.tormentaLabs.riobus.history.fragment;

import android.support.v4.app.Fragment;
import android.widget.ExpandableListView;

import com.tormentaLabs.riobus.R;
import com.tormentaLabs.riobus.history.adapter.HistoryListAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Used to expose history data
 * @author limazix
 * @since 3.0
 * Created at 24/10/15
 * @// TODO: 19/11/15 ADD Search and endless scroll list
 */
@EFragment(R.layout.fragment_history)
public class HistoryFragment extends Fragment {

    @ViewById(R.id.historyListView)
    ExpandableListView historyListView;

    @Bean
    HistoryListAdapter historyListAdapter;

    @AfterViews
    void afterViews() {
        historyListView.setAdapter(historyListAdapter);
    }
}
