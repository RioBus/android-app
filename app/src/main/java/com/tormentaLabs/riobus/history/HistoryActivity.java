package com.tormentaLabs.riobus.history;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.snappydb.SnappydbException;
import com.tormentaLabs.riobus.R;
import com.tormentaLabs.riobus.common.dao.HistoryDAO;
import com.tormentaLabs.riobus.common.models.Line;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryActivity extends AppCompatActivity {

    private static final String TAG = HistoryActivity.class.getName();

    @BindView(R.id.history_list) RecyclerView historyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_activity);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupList();
    }

    private void setupList() {
        List<DateItem> items = new ArrayList<>();
        try {
            HistoryDAO dao = new HistoryDAO(getBaseContext());
            List<String> dates = dao.getHistoryKeys();
            for (String dt : dates) {
                String[] tmp = dt.split(":")[1].split("-");
                DateItem item = new DateItem(tmp[2]+"/"+tmp[1]+"/"+tmp[0], dao.getHistory(dt));
                items.add(item);
            }
            dao.close();
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
        HistoryAdapter adapter = new HistoryAdapter(this, items);
        historyList.setAdapter(adapter);
        historyList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
