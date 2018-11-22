package com.opendata.yu_hsienchou.taiwanair.Model;

import android.databinding.BaseObservable;

import com.opendata.yu_hsienchou.taiwanair.R;

public class DashBoardModel extends BaseObservable {
    public String locationName;
    public Double pm25Value;

    public DashBoardModel(String locationName, double pm25Value){
        this.locationName = locationName;
        this.pm25Value = pm25Value;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
        notifyPropertyChanged(R.id.location);
    }

    public Double getPm25Value() {
        return pm25Value;
    }

    public void setPm25Value(Double pm25Value) {
        this.pm25Value = pm25Value;
        notifyPropertyChanged(R.id.pm25value);
    }
}
