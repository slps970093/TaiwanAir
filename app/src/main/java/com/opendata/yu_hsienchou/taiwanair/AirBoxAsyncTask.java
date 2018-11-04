package com.opendata.yu_hsienchou.taiwanair;

import android.os.AsyncTask;

import java.util.ArrayList;

public class AirBoxAsyncTask extends AsyncTask <Void,Void,Void> implements APIDataWizard {
    private EdimaxAirBox airBox;
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try{
            airBox = new EdimaxAirBox();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public ArrayList<AirDataModel> getModel() {
        return airBox.getModel();
    }

    @Override
    public String getResult() {
        return airBox.getResult();
    }
}
