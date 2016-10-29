package com.tormentaLabs.riobus.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.tormentaLabs.riobus.R;
import com.tormentaLabs.riobus.adapters.RecentsAdapter;
import com.tormentaLabs.riobus.models.Line;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecentSearchesFragment.OnRecentSearchesFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecentSearchesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecentSearchesFragment extends Fragment {

    private OnRecentSearchesFragmentInteractionListener mListener;

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

        List<Line> mockedLines = new ArrayList<Line>();
        mockedLines.add(new Line("485", "FUNDAO X GENERAL OSORIO"));
        mockedLines.add(new Line("345", "BARRA DA TIJUCA X CANDELARIA"));

        RecentsAdapter adapter = new RecentsAdapter(getContext(), mockedLines);
        listView.setAdapter(adapter);

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRecentSearchesFragmentInteractionListener) {
            mListener = (OnRecentSearchesFragmentInteractionListener) context;
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnRecentSearchesFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
