package com.opendata.yu_hsienchou.taiwanair.viewModel;

import android.databinding.BaseObservable;

import com.opendata.yu_hsienchou.taiwanair.Model.DashBoardModel;
import com.opendata.yu_hsienchou.taiwanair.R;

public class DashBoardViewModel  {
    private String locationName;
    private Double pm25Value;
    private String category;

    public DashBoardViewModel(DashBoardModel dashBoardModel){
        this.locationName = dashBoardModel.locationName;
        this.pm25Value = dashBoardModel.pm25Value;
        this.category = dashBoardModel.category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLocationName() {

        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Double getPm25Value() {
        return pm25Value;
    }

    public void setPm25Value(Double pm25Value) {
        this.pm25Value = pm25Value;
    }
}
