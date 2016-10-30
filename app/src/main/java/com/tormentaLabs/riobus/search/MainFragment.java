package com.tormentaLabs.riobus.search;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.tormentaLabs.riobus.R;
import com.tormentaLabs.riobus.search.availableLines.AvailableLinesFragment;
import com.tormentaLabs.riobus.search.recents.RecentSearchesFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    @BindView(R.id.recents_fragment) FrameLayout recentsList;
    @BindView(R.id.available_fragment) FrameLayout availableLinesList;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search_main, container, false);
        ButterKnife.bind(this, v);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        Fragment recentsFragment = fragmentManager.findFragmentById(R.id.recents_fragment);
        if (recentsFragment == null) {
            recentsFragment = RecentSearchesFragment.newInstance();
            fragmentManager.beginTransaction().replace(R.id.recents_fragment, recentsFragment).commit();
        }

        Fragment availableLinesFragment = fragmentManager.findFragmentById(R.id.available_fragment);
        if (availableLinesFragment == null) {
            availableLinesFragment = AvailableLinesFragment.newInstance();
            fragmentManager.beginTransaction().replace(R.id.available_fragment, availableLinesFragment).commit();
        }
        return v;
    }

}
