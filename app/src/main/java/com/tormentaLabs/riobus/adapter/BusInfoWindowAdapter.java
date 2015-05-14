package com.tormentaLabs.riobus.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.gson.Gson;
import com.tormentaLabs.riobus.R;
import com.tormentaLabs.riobus.domain.Bus;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Seconds;

import java.util.Calendar;
import java.util.Date;

public class BusInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {


    private Activity context;

    public BusInfoWindowAdapter(Activity context) {
        this.context = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view;

        if(marker.getTitle()==null){
            Bus bus = new Gson().fromJson(marker.getSnippet(), Bus.class);
            view = context.getLayoutInflater().inflate(R.layout.info_window_layout, null);

            TextView lineView = (TextView) view.findViewById(R.id.titulo);

            String line = bus.getLine();
            try {
                int lineInteger = (int) Double.parseDouble(line);
                line = Integer.toString(lineInteger);
            } catch (Exception e) {}

            lineView.setText(context.getString(R.string.marker_title, bus.getOrder(), line));

            TextView dateTime = (TextView) view.findViewById(R.id.hora);
            dateTime.setText(prepareDate(bus.getTimestamp()));

            TextView velocity = (TextView) view.findViewById(R.id.velocity);
            velocity.setText(context.getString(R.string.marker_velocity, String.valueOf(bus.getVelocity())));

            TextView sense = (TextView) view.findViewById(R.id.sense);
            sense.setText(context.getString(R.string.marker_sense, String.valueOf(bus.getSense())));
        }
        else{
            view = context.getLayoutInflater().inflate(R.layout.info_window_layout_client, null);
        }

        return view;
    }

    private String prepareDate(Date date){
        DateTime busTimestamp = new DateTime(date);
        DateTime now = new DateTime(Calendar.getInstance());

        int time = Seconds.secondsBetween(busTimestamp, now).getSeconds();
        if(time < 60) return context.getString(R.string.marker_seconds, String.valueOf(time));

        time = Minutes.minutesBetween(busTimestamp, now).getMinutes();
        if(time < 60) return context.getString(R.string.marker_minutes, String.valueOf(time));

        time = Hours.hoursBetween(busTimestamp, now).getHours();
        if(time < 24) return context.getString(R.string.marker_hours, String.valueOf(time));

        time = Days.daysBetween(busTimestamp, now).getDays();
        return context.getString(R.string.marker_days, String.valueOf(time));
    }
}
