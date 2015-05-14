package com.tormentaLabs.riobus.asyncTasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.tormentaLabs.riobus.R;
import com.tormentaLabs.riobus.domain.Bus;
import com.tormentaLabs.riobus.service.IService;
import com.tormentaLabs.riobus.service.ServiceFactory;
import com.tormentaLabs.riobus.common.BusDataReceptor;

import java.util.ArrayList;
import java.util.List;

public class BusSearchTask extends AsyncTask<String, Void, List<Bus>>{

    private Context context;
    ProgressDialog dialog;

    public BusSearchTask(Context context){
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(context);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(context.getString(R.string.searching_data));
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected List<Bus> doInBackground(String... params) {
        String data = params[0];
        IService service = ServiceFactory.getSearchService();
        List<Bus> buses = new ArrayList<Bus>();
        try {
            buses = (List) service.execute(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buses;
    }

    @Override
    protected void onPostExecute(List<Bus> buses){
        super.onPostExecute(buses);
        System.out.println("Resultado: " + String.valueOf(buses.size()) + " carros");
        System.out.println("Ocultando dialog...");
        dialog.dismiss();
        BusDataReceptor receptor = (BusDataReceptor) context;
        receptor.retrieveBusData(buses);
    }
}
