package com.opendata.yu_hsienchou.taiwanair.Model;

public class DashBoardModel {
    public String locationName;
    public Double pm25Value;

    public DashBoardModel(String locationName, double pm25Value){
        this.locationName = locationName;
        this.pm25Value = pm25Value;
    }
}
