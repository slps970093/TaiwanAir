package com.opendata.yu_hsienchou.taiwanair;

import android.provider.Settings;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 單元測試 空氣盒子API
 */
public class AirBoxUnitTest {
    private static String url = "https://pm25.lass-net.org/data/last-all-airbox.json";
    private static String data;
    private ArrayList<AirDataModel> dataModels = new ArrayList<AirDataModel>();
    @BeforeClass
    public static void connectTest() throws Exception{
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        data = response.body().string();
    }

    /**
     * 塞入空氣資料模型
     */
    @Before
    public void putModel(){
        try{
            JSONObject jsonObject = new JSONObject(data);
            JSONArray jsonArray = jsonObject.getJSONArray("feeds");
            for(int n = 0; n <= jsonArray.length(); n++){
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(n) ;
                dataModels.add( new AirDataModel( jsonObject1.getString("SiteName"),jsonObject1.getDouble("gps_lat"), jsonObject1.getDouble("gps_lon") ,jsonObject1.getInt("s_d0")));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Test
    public void show(){
        System.out.print(dataModels.get(0).getSiteName());
    }

}
