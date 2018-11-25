package com.opendata.yu_hsienchou.taiwanair;

/**
 * 空氣資料模型
 */
public class AirDataModel {
    private String device_id;
    private String SiteName;
    private double gpsLatitude;
    private double gpsLongitude;
    private int pm25Value;
    public AirDataModel(String SiteName, Double gpsLatitude,Double gpsLongitude , int PM25Value,String device_id){
        this.SiteName = SiteName;
        this.gpsLatitude = gpsLatitude;
        this.gpsLongitude = gpsLongitude;
        this.pm25Value = PM25Value;
        this.device_id = device_id;
    }

    public double getGpsLatitude() {
        return gpsLatitude;
    }

    public double getGpsLongitude() {
        return gpsLongitude;
    }

    public int getPm25Value() {
        return pm25Value;
    }

    public String getSiteName() {
        return SiteName;
    }

    public String getDeviceID() {
        return device_id;
    }

    public void setDeviceID(String device_id) {
        this.device_id = device_id;
    }

    public void setGpsLatitude(double gpsLatitude) {
        this.gpsLatitude = gpsLatitude;
    }

    public void setGpsLongitude(double gpsLongitude) {
        this.gpsLongitude = gpsLongitude;
    }

    public void setPm25Value(int pm25Value) {
        this.pm25Value = pm25Value;
    }

    public void setSiteName(String siteName) {
        SiteName = siteName;
    }
}
