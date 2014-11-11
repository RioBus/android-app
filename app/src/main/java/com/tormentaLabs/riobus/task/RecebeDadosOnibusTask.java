package com.tormentaLabs.riobus.task;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tormentaLabs.riobus.R;
import com.tormentaLabs.riobus.exception.HttpException;
import com.tormentaLabs.riobus.http.HttpImpl;
import com.tormentaLabs.riobus.http.HttpUrls;
import com.tormentaLabs.riobus.interfaces.IRecebeDadosOnibus;
import com.tormentaLabs.riobus.model.Ponto;
import com.tormentaLabs.riobus.util.Util;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by pedro on 05/07/2014.
 */
public class RecebeDadosOnibusTask extends AsyncTask<String,Void, List<Ponto>> {


    private Context context;
    private String linha;
    ProgressDialog dialog;
    IRecebeDadosOnibus iRecebeDadosOnibus;
    String erro;

    public RecebeDadosOnibusTask(Context context) {

        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(context);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(context.getString(R.string.buscando_dados));
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected List<Ponto> doInBackground(String... params) {
        linha = params[0];


        HttpImpl http = HttpImpl.getInstance();
        List<NameValuePair> parametros = new ArrayList<NameValuePair>();
        parametros.add(new BasicNameValuePair("linha", linha));
        Util.print(parametros.toString());
        Util.print(linha);

        List<Ponto> pontos = new ArrayList<Ponto>();
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        Calendar calendar = Calendar.getInstance();

        try {
            BufferedReader reader =  http.executaChamadoGetURL(HttpUrls.urlRioBusLinhas, parametros);
            pontos = getPonto(reader);
        } catch (HttpException e) {
            erro = context.getString(R.string.msg_conexao_prefeitura);
            Toast.makeText(context, erro, Toast.LENGTH_LONG).show();
            Log.e(Util.TAG, e.getMessage());
        }
        return pontos;
    }

    @Override
    protected void onPostExecute(List<Ponto> pontos) {
        super.onPostExecute(pontos);
        dialog.dismiss();
        iRecebeDadosOnibus = (IRecebeDadosOnibus) context;
        iRecebeDadosOnibus.recebeListaPontosCallback(pontos, erro);

    }

    // criar classe json parser
    private List<Ponto> getPonto(BufferedReader reader) {
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        List<Ponto> pontos = new ArrayList<Ponto>();

        if(reader == null) return null;

        JsonElement json = parser.parse(reader);

        if(!validaJson(json)) return null;

        JsonArray data = json.getAsJsonObject().getAsJsonArray("DATA");

        //TODO: usar os columns para pegar qual o indice de cada coluna no array data
        //retirar no futuro o hardcoded das colunas abaixo
        JsonArray collumns = json.getAsJsonObject().getAsJsonArray("COLUMNS");


        if(!validaJson(data)) return null;

        for (JsonElement elem: data) {

            JsonArray obj = elem.getAsJsonArray();
            Ponto ponto = new Ponto();
            ponto.setDataHora(converteStringToDate(getJsonAsString(obj.get(0))));
            ponto.setOrdem(getJsonAsString(obj.get(1)));
            ponto.setLinha(getJsonAsString(obj.get(2)));
            ponto.setLatitude(getJsonAsDouble(obj.get(3)));
            ponto.setLongitude(getJsonAsDouble(obj.get(4)));
            ponto.setVelocidade(getJsonAsDouble(obj.get(5)));
            pontos.add(ponto);
        }
        return pontos;
    }

    private Date converteStringToDate(String data) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:SS");
        try {
            return sdf.parse(data);
        } catch (ParseException e) {
            Log.e(Util.TAG, e.getMessage());
        }
        return null;
    }

    private boolean validaJson(JsonElement json){
        if(json == null || json.isJsonNull()){
            return false;
        }
        return true;
    }

    private String getJsonAsString(JsonElement json){
        if(validaJson(json)) {
            return json.getAsString();
        }
        return null;
    }
    private Double getJsonAsDouble(JsonElement json){
        if(validaJson(json)) {
            return json.getAsDouble();
        }
        return null;
    }
}
