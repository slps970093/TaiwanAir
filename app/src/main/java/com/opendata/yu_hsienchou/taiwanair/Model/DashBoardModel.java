package com.opendata.yu_hsienchou.taiwanair.Model;

import android.databinding.BaseObservable;

import com.opendata.yu_hsienchou.taiwanair.R;

public class DashBoardModel extends BaseObservable {
    public String locationName;
    public Double pm25Value;
    public String category;

    public DashBoardModel(String locationName, double pm25Value, String category){
        this.locationName = locationName;
        this.pm25Value = pm25Value;
        this.category = category;
    }

    public String getLocationName() {
        return locationName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
