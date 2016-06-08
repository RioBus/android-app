package com.tormentaLabs.riobus.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.gson.Gson;
import com.tormentaLabs.riobus.R;
import com.tormentaLabs.riobus.model.Bus;
import com.tormentaLabs.riobus.model.Itinerary;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Seconds;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

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
            try {
                Bus bus = new Gson().fromJson(marker.getSnippet(), Bus.class);
                view = context.getLayoutInflater().inflate(R.layout.info_window_layout, null);

                TextView lineView = (TextView) view.findViewById(R.id.titulo);

                String line = bus.getLine();
                try {
                    int lineInteger = (int) Double.parseDouble(line);
                    line = Integer.toString(lineInteger);
                } catch (Exception e) {
                }

                lineView.setText(context.getString(R.string.marker_title, bus.getOrder(), line));

                TextView dateTime = (TextView) view.findViewById(R.id.hora);
                dateTime.setText(prepareDate(bus.getTimestamp()));

                TextView velocity = (TextView) view.findViewById(R.id.velocity);
                velocity.setText(context.getString(R.string.marker_velocity, String.valueOf(bus.getVelocity())));

                TextView sense = (TextView) view.findViewById(R.id.sense);
                if (bus.getSense() == null || bus.getSense().trim().equals("")) {
                    sense.setText(context.getString(R.string.marker_sense, context.getString(R.string.marker_sense_null)));
                } else {

                    sense.setText(context.getString(R.string.marker_sense, String.valueOf(bus.getSense())));
                }
            }catch (Exception exp){
                String[] snippet = marker.getSnippet().split(">");
                Itinerary it = new Gson().fromJson(snippet[0], Itinerary.class);
                view = context.getLayoutInflater().inflate(R.layout.info_window_layout_itinerary, null);

                TextView lineView = (TextView) view.findViewById(R.id.titulo);

                String line = it.getLine();
                try {
                    int lineInteger = (int) Double.parseDouble(line);
                    line = Integer.toString(lineInteger);
                } catch (Exception e) {
                }

                lineView.setText(context.getString(R.string.marker_title_itinerary, line));

                TextView descricao = (TextView) view.findViewById(R.id.descricao);
                if (it.getDescription() == null || it.getDescription().trim().equals("")) {
                    descricao.setText(context.getString(R.string.marker_description, context.getString(R.string.marker_description_null)));
                } else {

                    descricao.setText(context.getString(R.string.marker_description, String.valueOf(it.getDescription())));
                }

                TextView retornando = (TextView) view.findViewById(R.id.retornando);
                if (snippet[1].equalsIgnoreCase("true")){
                    retornando.setText(context.getString(R.string.marker_returning_true));
                }
                else{
                    retornando.setText(context.getString(R.string.marker_returning_false));
                }

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
