package com.opendata.yu_hsienchou.taiwanair;

import android.os.AsyncTask;

import java.util.ArrayList;

public class AirBoxAsyncTask extends AsyncTask<Void,Void,ArrayList<AirDataModel>> {
    private EdimaxAirBox edimaxAirBox;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(ArrayList<AirDataModel> airDataModels) {
        super.onPostExecute(airDataModels);
    }

    @Override
    protected ArrayList<AirDataModel> doInBackground(Void... voids) {
        try {
            edimaxAirBox = new EdimaxAirBox();
        }catch (Exception e){
            e.printStackTrace();
        }
        return edimaxAirBox.getModel();
    }
}
