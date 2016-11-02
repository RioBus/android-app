package com.tormentaLabs.riobus.search;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tormentaLabs.riobus.R;
import com.tormentaLabs.riobus.common.interfaces.OnLineInteractionListener;
import com.tormentaLabs.riobus.common.models.Line;
import com.tormentaLabs.riobus.common.services.LineService;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    @BindView(R.id.search_list) ListView searchList;
    private OnLineInteractionListener mListener;

    public MainFragment() {
        // Required empty public constructor
    }

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.search_fragment_main, container, false);
        ButterKnife.bind(this, v);

        List<Line> recents = LineService.getInstance().getLines().subList(0, 2);
        List<Line> lines = LineService.getInstance().getLines();
        LinesAdapter adapter;
        adapter = (recents.size()>0) ? new LinesAdapter(getContext(), lines, recents) : new LinesAdapter(getContext(), lines);
        searchList.setAdapter(adapter);
        return v;
    }

    @OnItemClick(R.id.search_list)
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Line line = (Line) adapterView.getItemAtPosition(i);
        if (!line.getDescription().equals("")) mListener.onLineInteraction(line);
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


}
