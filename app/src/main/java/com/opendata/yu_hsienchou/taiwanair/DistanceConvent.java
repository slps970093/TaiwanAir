package com.opendata.yu_hsienchou.taiwanair;

public class DistanceConvent {
    private double distance;

    //帶入使用者及景點店家經緯度可計算出距離
    public void setDistance(double longitude1, double latitude1, double longitude2, double latitude2)
    {
        double radLatitude1 = latitude1 * Math.PI / 180;
        double radLatitude2 = latitude2 * Math.PI / 180;
        double l = radLatitude1 - radLatitude2;
        double p = longitude1 * Math.PI / 180 - longitude2 * Math.PI / 180;
        double distance = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(l / 2), 2)
                + Math.cos(radLatitude1) * Math.cos(radLatitude2)
                * Math.pow(Math.sin(p / 2), 2)));
        distance = distance * 6378137.0;
        distance = Math.round(distance * 10000) / 10000;
        this.distance = distance;
    }
    public double getKm(){
        return this.distance / 1000;
    }

}
