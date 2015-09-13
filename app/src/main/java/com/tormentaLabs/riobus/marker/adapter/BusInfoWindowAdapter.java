package com.tormentaLabs.riobus.marker.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.tormentaLabs.riobus.R;
import com.tormentaLabs.riobus.marker.model.BusModel;
import com.tormentaLabs.riobus.utils.RioBusUtils;

import org.androidannotations.annotations.EBean;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Seconds;
;
import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

// TODO need tobe refectored!!!
public class BusInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {


    private static final String TAG = BusInfoWindowAdapter.class.getName();
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
        View view = null;

        if(marker.getTitle()==null){
            BusModel bus = null;
            try {
                bus = new ObjectMapper().readValue(marker.getSnippet(), BusModel.class);
                view = context.getLayoutInflater().inflate(R.layout.info_window_layout, null);

                TextView lineView = (TextView) view.findViewById(R.id.titulo);

                String line = bus.getLine();
                try {
                    int lineInteger = (int) Double.parseDouble(line);
                    line = Integer.toString(lineInteger);
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                }

                lineView.setText(context.getString(R.string.marker_title, bus.getOrder(), line));

                try {
                    TextView dateTime = (TextView) view.findViewById(R.id.hora);
                    String datePrepared = prepareDate(RioBusUtils.parseStringToDate(bus.getTimeStamp()));
                    dateTime.setText(datePrepared);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                TextView velocity = (TextView) view.findViewById(R.id.velocity);
                velocity.setText(context.getString(R.string.marker_velocity, String.valueOf(bus.getSpeed())));

                TextView sense = (TextView) view.findViewById(R.id.sense);
                if(bus.getSense() == null || bus.getSense().trim().equals("")) {
                    sense.setText(context.getString(R.string.marker_sense, context.getString(R.string.marker_sense_null)));
                } else {

                    sense.setText(context.getString(R.string.marker_sense, String.valueOf(bus.getSense())));
                }

            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            }
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
