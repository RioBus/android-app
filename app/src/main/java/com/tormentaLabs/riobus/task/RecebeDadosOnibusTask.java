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


    private List<Ponto> getPonto(BufferedReader reader) {
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        List<Ponto> pontos = new ArrayList<Ponto>();

        JsonElement json = parser.parse(reader);
        JsonArray data = json.getAsJsonObject().getAsJsonArray("DATA");
        JsonArray collumns = json.getAsJsonObject().getAsJsonArray("COLUMNS");
        for (JsonElement elem: data) {
            JsonArray obj = elem.getAsJsonArray();
            Ponto ponto = new Ponto();
            ponto.setDataHora(converteStringToDate(obj.get(0).getAsString()));
            ponto.setOrdem(obj.get(1).getAsString());
            ponto.setLinha(obj.get(2).getAsString());
            ponto.setLatitude(obj.get(3).getAsDouble());
            ponto.setLongitude(obj.get(4).getAsDouble());
            ponto.setVelocidade(obj.get(5).getAsDouble());
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
}
