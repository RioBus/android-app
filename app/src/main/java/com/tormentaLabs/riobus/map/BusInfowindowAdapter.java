package com.tormentaLabs.riobus.map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.gson.Gson;
import com.tormentaLabs.riobus.R;
import com.tormentaLabs.riobus.common.models.Bus;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Seconds;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

class BusInfowindowAdapter implements GoogleMap.InfoWindowAdapter {

    @BindView(R.id.txtOrder) TextView txtOrder;
    @BindView(R.id.txtSense) TextView txtSense;
    @BindView(R.id.txtSpeed) TextView txtSpeed;
    @BindView(R.id.txtTimestamp) TextView txtTimestamp;

    private View view;
    private Context context;

    BusInfowindowAdapter(Context context) {
        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.maps_icon_infowindow, null);
        ButterKnife.bind(this, view);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        String content = marker.getSnippet();
        Gson gson = new Gson();
        Bus bus = gson.fromJson(content, Bus.class);

        String order = String.format(Locale.getDefault(), txtOrder.getText().toString(), bus.getOrder());
        txtOrder.setText(order);

        String sense = String.format(Locale.getDefault(), txtSense.getText().toString(), bus.getSense());
        txtSense.setText(sense);

        String speed = String.format(Locale.getDefault(), txtSpeed.getText().toString(), String.valueOf(bus.getSpeed()));
        txtSpeed.setText(speed);

        String timestamp = String.format(Locale.getDefault(), txtTimestamp.getText().toString(), getPreparedTimestamp(bus.getTimestamp()));
        txtTimestamp.setText(timestamp);

        return view;
    }

    private String getPreparedTimestamp(Date timestamp) {
        DateTime current = new DateTime(Calendar.getInstance());
        DateTime last = new DateTime(timestamp);
        int diff = Seconds.secondsBetween(last, current).getSeconds();
        if (diff <= 60) return context.getString(R.string.common_time_seconds);

        diff = Minutes.minutesBetween(last, current).getMinutes();
        if (diff <= 60) return String.valueOf(diff) + " " + context.getString(R.string.common_time_minutes);

        diff = Hours.hoursBetween(last, current).getHours();
        if (diff <= 24) return String.valueOf(diff) + " " + context.getString(R.string.common_time_hours);

        diff = Days.daysBetween(last, current).getDays();
        return String.valueOf(diff) + " " + context.getString(R.string.common_time_days);
    }
}
