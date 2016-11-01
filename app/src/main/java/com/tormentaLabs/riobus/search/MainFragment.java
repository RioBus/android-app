package com.tormentaLabs.riobus.search;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.tormentaLabs.riobus.R;
import com.tormentaLabs.riobus.common.models.Line;
import com.tormentaLabs.riobus.common.services.LineService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    @BindView(R.id.search_list) ListView searchList;

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

        List<Line> recents = LineService.getInstance().getLines().subList(0, 2);
        List<Line> lines = LineService.getInstance().getLines();

        List<Line> items = new ArrayList<>();
        items.add(new Line(getString(R.string.fragment_recent_searches_header), ""));
        items.addAll(recents);
        items.add(new Line(getString(R.string.fragment_available_lines_header), ""));
        items.addAll(lines);
        LinesAdapter adapter = new LinesAdapter(getContext(), items);
        searchList.setAdapter(adapter);
        return v;
    }

}
