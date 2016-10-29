package com.tormentaLabs.riobus.fragments;

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
import com.tormentaLabs.riobus.adapters.RecentsAdapter;
import com.tormentaLabs.riobus.models.Line;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnLineInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecentSearchesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecentSearchesFragment extends Fragment implements AdapterView.OnItemClickListener {

    private OnLineInteractionListener mListener;

    public RecentSearchesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RecentSearchesFragment.
     */
    public static RecentSearchesFragment newInstance() {
        RecentSearchesFragment fragment = new RecentSearchesFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_LINE, line);
//        args.putString(ARG_DIRECTION, direction);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recent_search, container, false);
        ListView listView = (ListView) v.findViewById(R.id.recents_list);

        List<Line> lines = new ArrayList<Line>();
        lines.add(new Line("485", "FUNDAO X GENERAL OSORIO"));
        lines.add(new Line("345", "BARRA DA TIJUCA X CANDELARIA"));

        if (lines.size()>0) {
            RecentsAdapter adapter = new RecentsAdapter(getContext(), lines);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(this);
        }
        else {
            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
        }

        return v;
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Line line = (Line) adapterView.getItemAtPosition(i);
        mListener.onLineInteraction(line);
    }
}
