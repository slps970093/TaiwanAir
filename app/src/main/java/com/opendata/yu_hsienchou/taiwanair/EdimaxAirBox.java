package com.opendata.yu_hsienchou.taiwanair;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 空氣盒子  API
 * @// TODO: 2018/11/14  使用 LASS-開源公益環境感測網路
 * @see https://paper.dropbox.com/doc/LASS-README--AR2OhqY~_TTQzFJ2fMa7a34EAg-2c1MBX2SHK8eyLwDj1vfB
 * @author  Yu-Hsien Chou ( 小周)
 */
public class EdimaxAirBox implements APIDataWizard {
    private String url = "https://pm25.lass-net.org/data/last-all-airbox.json";
    private String result;
    private int ResponceCode;
    public EdimaxAirBox() throws Exception{
        this.connectAPI();
    }

    /**
     * 連線到 LASS API
     *
     * @throws Exception
     */
    private void connectAPI() throws Exception{
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        ResponceCode = response.code();
        result = response.body().string();
    }

    /**
     * 取得資料模型 （API文件如下）
     * @see https://paper.dropbox.com/doc/LASS-Data-specification--AR045yt_eqTTKHcMR5tVJ4ZVAg-86K8vkbGWyCG7aMiP1odg
     * @see https://paper.dropbox.com/doc/LASS-Data-Platform--AR3zWxS6FcUJSa1aVjnj3T~RAg-6kAh1xTE9kVQthu7KnAW2
     * @return
     */
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
