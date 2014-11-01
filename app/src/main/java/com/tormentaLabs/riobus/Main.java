package com.tormentaLabs.riobus;

import android.app.Dialog;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tormentaLabs.riobus.adapter.CustomInfoWindowAdapter;
import com.tormentaLabs.riobus.interfaces.IRecebeDadosOnibus;
import com.tormentaLabs.riobus.model.MarkerOnibusMapa;
import com.tormentaLabs.riobus.model.Ponto;
import com.tormentaLabs.riobus.task.RecebeDadosOnibusTask;
import com.tormentaLabs.riobus.util.Util;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Rio Bus
 * @author Pedro Cortez
 * @version 2.0.0
 */
@EActivity(R.layout.mapa)
public class Main extends ActionBarActivity implements IRecebeDadosOnibus ,GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener {

    public GoogleMap mapa; // Might be null if Google Play services APK is not available.

    @ViewById
    public AutoCompleteTextView search;

    LocationClient clienteLocalizacao;
    Location localizacaoAtual;
    MarkerOptions marcadorCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clienteLocalizacao = new LocationClient(this, this, this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        conecta();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mapa!=null) mapa.clear();
        conecta();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mapa!=null) mapa.clear();
        desconecta();

    }

    private void conecta(){
        if(!clienteLocalizacao.isConnected()) {
            clienteLocalizacao.connect();
        }
    }

    private void desconecta(){
        if(clienteLocalizacao.isConnected()) {
            clienteLocalizacao.disconnect();
        }
    }

    @AfterViews
    public void onViewCreatedByAA(){
        setUpMapIfNeeded();
        setSuggestions(); // Shows the previous searched lines
    }

    private void setSuggestions() {
        String[] lineHistory = Util.getHistory(this);
        if (lineHistory!= null){
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_dropdown_item_1line,Util.getHistory(this));
            search.setAdapter(adapter);
            search.setThreshold(0);
        }
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mapa == null) {
            // Try to obtain the map from the SupportMapFragment.
            mapa = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();

            // Hide the zoom controls in the bottom of the screen
            mapa.getUiSettings().setZoomControlsEnabled(false);
            // Check if we were successful in obtaining the map.
            if (mapa != null) {
                setUpMap();
            }
        }

        //Quando o usuario digita enter, ele faz a requisição procurando a posição daquela linha
        search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    if(validaEntradaValida()) {

                        if(Util.verificaConexaoInternet(Main.this)) {
                            Util.saveOnHistory(Main.this , search.getText().toString(),search);
                            setSuggestions(); //Updating the adapter
                            new RecebeDadosOnibusTask(Main.this).execute(search.getText().toString());
                        } else {
                          Toast.makeText(Main.this, getString(R.string.msg_conexao_internet), Toast.LENGTH_SHORT).show();
                        }
                    }
                    return true;
                }
                return false;
            }
        });
    }


    private boolean validaEntradaValida() {
        String linhaDigitada = search.getText().toString();
        return !(linhaDigitada==null
                || linhaDigitada.equals("")
                || linhaDigitada.trim().equals(""));
    }

    private void setUpMap() {
        mapa.clear();
        mapa.setTrafficEnabled(true);
    }

    @Override
    public void recebeListaPontosCallback(List<Ponto> pontos, String mensagemErro) {
        mapa.clear();
        if(mensagemErro != null) {
            Toast.makeText(this, mensagemErro, Toast.LENGTH_LONG).show();
        } else {
            if(pontos.isEmpty()) {
                Toast.makeText(this, getString(R.string.msg_onibus_404), Toast.LENGTH_SHORT).show();
            }

            MarkerOnibusMapa mom = new MarkerOnibusMapa(this);
            mom.adicionaMarcadoresNoMapa(mapa, pontos);

            LatLngBounds.Builder builder = new LatLngBounds.Builder();

            builder.include(new LatLng(localizacaoAtual.getLatitude(), localizacaoAtual.getLongitude()));

            for (Ponto ponto : pontos) {
                builder.include(new LatLng(ponto.getLatitude(), ponto.getLongitude()));
            }

            mapa.setInfoWindowAdapter(new CustomInfoWindowAdapter(this));

            LatLng posicaoCliente = new LatLng(localizacaoAtual.getLatitude(), localizacaoAtual.getLongitude());
            marcarCliente(posicaoCliente);

            LatLngBounds bounds = builder.build();

            int padding = 100; // offset from edges of the map in pixels
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);

            mapa.moveCamera(cu);
            mapa.animateCamera(cu);
        }
    }

    public void atualizaMapaLocalizacao(Location localizacao) {
        LatLng posicao = new LatLng(localizacao.getLatitude(), localizacao.getLongitude());
        mapa.moveCamera(CameraUpdateFactory.newLatLng(posicao));
        mapa.animateCamera(CameraUpdateFactory.zoomTo(16), 2000, null);
        marcarCliente(posicao);
    }

    private void marcarCliente(LatLng posicao){
        if(marcadorCliente==null) {
            marcadorCliente = new MarkerOptions().position(posicao).title(getString(R.string.marker_cliente)).icon(BitmapDescriptorFactory
                    .fromResource(R.drawable.man_maps));
        } else {
            marcadorCliente.position(posicao);
        }
        //mapa.clear();
        mapa.addMarker(marcadorCliente);
        // depois de limpar tudo, precisa readicionar os pontos que estavam no mapa, caso houvesse
    }

    @Click(R.id.button_about)
    public void clicaNoSobre(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.about_dialog);
        dialog.setTitle(getString(R.string.about_title));
        TextView tv = (TextView) dialog.findViewById(R.id.content);
        tv.setText(Html.fromHtml(getString(R.string.about_text)));
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        dialog.show();
    }

    @Override
    public void onConnected(Bundle bundle) {
        localizacaoAtual = clienteLocalizacao.getLastLocation();
        atualizaMapaLocalizacao(localizacaoAtual);
    }

    @Override
    public void onDisconnected() {}

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {}
}
