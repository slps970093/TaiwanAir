package com.opendata.yu_hsienchou.taiwanair;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 空氣盒子  API
 * @author  Yu-Hsien Chou ( 小周)
 */
public class EdimaxAirBox implements APIDataWizard {
    private String url = "https://pm25.lass-net.org/data/last-all-airbox.json";
    private String result;
    public EdimaxAirBox() throws Exception{
        this.connectAPI();
    }

    /**
     * 連線到 LASS API
     * @throws Exception
     */
    private void connectAPI() throws Exception{
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        result = response.body().string();
    }

    @Override
    public ArrayList<AirDataModel> getModel() {
        ArrayList<AirDataModel> arrayList = new ArrayList<AirDataModel>();
        try{
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("feeds");
            for(int n = 0; n <= jsonArray.length(); n++){
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(n) ;
                arrayList.add( new AirDataModel( jsonObject1.getString("SiteName"),jsonObject1.getDouble("gps_lat"), jsonObject1.getDouble("gps_lon") ,jsonObject1.getInt("s_d0")));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return arrayList;
    }

    @Override
    public String getResult() {
        return result;
    }
}
