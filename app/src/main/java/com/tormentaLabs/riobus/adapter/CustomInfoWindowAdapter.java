package com.tormentaLabs.riobus.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.gson.Gson;
import com.tormentaLabs.riobus.R;
import com.tormentaLabs.riobus.model.Ponto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by fred on 16/10/14.
 */
public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {


    private Activity context;

    public CustomInfoWindowAdapter(Activity context) {
        this.context = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View v;

        if(marker.getTitle()==null){
            Ponto ponto = new Gson().fromJson(marker.getSnippet(), Ponto.class);
            v = context.getLayoutInflater().inflate(R.layout.info_window_layout, null);

            TextView linha = (TextView) v.findViewById(R.id.titulo);
            linha.setText(context.getString(R.string.marker_linha, ponto.getLinha()));

            TextView codigo = (TextView) v.findViewById(R.id.codigo);
            codigo.setText(context.getString(R.string.marker_codigo, ponto.getOrdem().toString()));

            TextView hora = (TextView) v.findViewById(R.id.hora);
            String data = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(ponto.getDataHora());
            hora.setText(context.getString(R.string.marker_hora, data));

            TextView velocidade = (TextView) v.findViewById(R.id.velocidade);
            velocidade.setText(context.getString(R.string.marker_velocidade, String.valueOf(ponto.getVelocidade())));
        }
        else{
            v = context.getLayoutInflater().inflate(R.layout.info_window_layout_client, null);
        }

        return v;
    }
}
