package com.tormentaLabs.riobus.search;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snappydb.SnappydbException;
import com.tormentaLabs.riobus.R;
import com.tormentaLabs.riobus.common.dao.HistoryDAO;
import com.tormentaLabs.riobus.common.dao.LineDAO;
import com.tormentaLabs.riobus.common.interfaces.LineDataReceiver;
import com.tormentaLabs.riobus.common.interfaces.OnLineInteractionListener;
import com.tormentaLabs.riobus.common.interfaces.OnSearchLines;
import com.tormentaLabs.riobus.common.models.Line;
import com.tormentaLabs.riobus.common.tasks.LineDownloadTask;
import com.tormentaLabs.riobus.common.tasks.LineStoreTask;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment implements LineDataReceiver, OnSearchLines {

    private static final String TAG = MainFragment.class.getName();
    private static final int RECENTS_LIMIT = 2;

    @BindView(R.id.search_list) RecyclerView searchList;

    private RecyclerView.Adapter defaultAdapter;
    private OnLineInteractionListener mListener;

    public MainFragment() {}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MainFragment.
     */
    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.search_fragment_main, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        List<Line> lines = loadLines();
        if (lines.size() > 0){
            List<Line> recents = loadRecents();
            updateView(lines, recents);
        }
        else downloadLines();
    }

    private List<Line> loadLines() {
        List<Line> lines = new ArrayList<>();
        try {
            LineDAO dao = new LineDAO(getContext());
            lines = dao.getLines();
            dao.close();
        } catch (SnappydbException e) {
            Log.e(TAG, e.getLocalizedMessage());
        }
        return lines;
    }

    private List<Line> loadRecents() {
        List<Line> lines = new ArrayList<>();
        try {
            HistoryDAO dao = new HistoryDAO(getContext());
            lines = dao.getRecentLines(RECENTS_LIMIT);
            dao.close();
        } catch (SnappydbException e) {
            Log.e(TAG, e.getLocalizedMessage());
        }
        return lines;
    }

    private void downloadLines() {
        LineDownloadTask task = new LineDownloadTask(this);
        task.execute();
    }

    private void updateView(List<Line> items, List<Line> recents) {
        defaultAdapter = (recents.size()>0) ?
                new LinesAdapter(getContext(), mListener, items, recents) : new LinesAdapter(getContext(), mListener, items);

        searchList.setAdapter(defaultAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        searchList.setLayoutManager(layoutManager);
    }

    @Override
    public void onLineListReceived(List<Line> items) {
        new LineStoreTask(items, getContext()).execute();
        updateView(items, new ArrayList<Line>());
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onLineInteraction(null);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnLineInteractionListener) {
            mListener = (OnLineInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnRecentSearchesFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void resetViewAdapter() {
        if (searchList.getAdapter() instanceof SearchAdapter)
            searchList.setAdapter(defaultAdapter);
    }

    @Override
    public boolean onSubmitSearch(String query) {
        if (query.equals("")) resetViewAdapter();

        List<Line> lines = new ArrayList<>();
        try {
            LineDAO dao = new LineDAO(getContext());
            lines.addAll(dao.getLines(query));
            dao.close();
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
        if (lines.size() == 0) {
            lines.add(new Line(query, getContext().getString(R.string.search_for_query)));
        }

        SearchAdapter tmp = new SearchAdapter(getContext(), mListener, lines);
        searchList.setAdapter(tmp);
        return true;
    }
}
