package com.tormentaLabs.riobus.model;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tormentaLabs.riobus.R;

import org.joda.time.DateTime;
import org.joda.time.Minutes;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by pedro on 20/07/2014.
 */
public class MarkerOnibusMapa {

    private Context contexto;

    public MarkerOnibusMapa(Context contexto){
        this.contexto = contexto;
    }

    public void adicionaMarcadoresNoMapa(GoogleMap mapa, List<Ponto> pontos) {
            for (Ponto ponto : pontos) {
                mapa.addMarker(getMarker(ponto));
            }
    }

    private MarkerOptions getMarker(Ponto ponto) {
        MarkerOptions marker = new MarkerOptions();
        LatLng posicao = new LatLng(ponto.getLatitude(), ponto.getLongitude());
        marker.position(posicao);
        marker.icon(getIcon(ponto.getDataHora()));
        marker.title(contexto.getString(R.string.marker_codigo) + " " + String.valueOf(ponto.getOrdem()));
        marker.snippet(contexto.getString(R.string.marker_hora) + " " + String.valueOf(ponto.getDataHora()));
        marker.snippet(contexto.getString(R.string.marker_velocidade) + " " + String.valueOf(ponto.getVelocidade()) + " " + contexto.getString(R.string.marker_velocidade_unidade));
        return marker;
    }

    private BitmapDescriptor getIcon(Date data) {
        BitmapDescriptor icon;

        DateTime momento = new DateTime(Calendar.getInstance());
        DateTime ultima = new DateTime(data);

        int diferenca =  Minutes.minutesBetween(ultima, momento).getMinutes();

        if(diferenca <= 5) {
           icon = BitmapDescriptorFactory
                .fromResource(R.drawable.bus_green);
        } else if(diferenca > 10 ) {
           icon = BitmapDescriptorFactory
                .fromResource(R.drawable.bus_red);
        } else {
           icon = BitmapDescriptorFactory
                .fromResource(R.drawable.bus_yellow);
        }
        return icon;
    }

}
