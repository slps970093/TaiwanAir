package com.opendata.yu_hsienchou.taiwanair.viewModel;

import com.opendata.yu_hsienchou.taiwanair.Model.DashBoardModel;

public class DashBoardViewModel {
    private String locationName;
    private Double pm25Value;

    public DashBoardViewModel(DashBoardModel dashBoardModel){
        this.locationName = dashBoardModel.locationName;
        this.pm25Value = dashBoardModel.pm25Value;
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
