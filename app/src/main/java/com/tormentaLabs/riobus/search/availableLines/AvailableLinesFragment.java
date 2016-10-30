package com.tormentaLabs.riobus.search.availableLines;

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
 * Activities that contain this fragment must implement the
 * {@link OnLineInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AvailableLinesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AvailableLinesFragment extends Fragment {

    private OnLineInteractionListener mListener;
    @BindView(R.id.lines_list) ListView linesList;

    public AvailableLinesFragment() {}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RecentSearchesFragment.
     */
    public static AvailableLinesFragment newInstance() {
        return new AvailableLinesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_available_lines, container, false);
        ButterKnife.bind(this, v);
        List<Line> lines = LineService.getInstance().getLines();
        LinesAdapter adapter = new LinesAdapter(getContext(), lines);
        linesList.setAdapter(adapter);
        return v;
    }

    @OnItemClick(R.id.lines_list)
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Line line = (Line) adapterView.getItemAtPosition(i);
        mListener.onLineInteraction(line);
    }

    // TODO: Rename method, update argument and hook method into UI event
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
                    + " must implement OnAvailableLinesFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
