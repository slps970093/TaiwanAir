package com.opendata.yu_hsienchou.taiwanair;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.opendata.yu_hsienchou.taiwanair.Model.DashBoardModel;
import com.opendata.yu_hsienchou.taiwanair.viewModel.DashBoardViewModel;
import com.opendata.yu_hsienchou.taiwanair.databinding.ActivityMainBinding;

import java.util.ArrayList;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainActivity extends AppCompatActivity {
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private Location currentLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private AirSQLiteModel airSQLiteModel;
    private double gps_lat,gps_log;
    private ActivityMainBinding activityMainBinding;
    private DashBoardViewModel dashBoardViewModel;
    private ArrayList<AirDataModel> airModel ;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        airSQLiteModel = new AirSQLiteModel(this);
        MainActivityPermissionsDispatcher.GPSLocationWithPermissionCheck(this);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        dashBoardViewModel = new DashBoardViewModel( new DashBoardModel("HHH",6.9));
        activityMainBinding.setAirData(dashBoardViewModel);
        try{
            // 取得空氣資料
            airModel = (ArrayList<AirDataModel>) new AirBoxAsyncTask().execute().get();
            // @todo 教學影片： https://www.youtube.com/watch?v=H18P38wn8Z4&fbclid=IwAR2jSKNinkv6Igd1fZQuMK2DfCUO5xJfYtB-HLNogiV2wGZpyejp5EEK1Kg
            setLocationRequest();
            getLocationInfo();
            showAirInfo();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void showAirInfo(){
        airSQLiteModel.insert_clean_all(airModel,gps_lat,gps_log);
        Cursor cursor = airSQLiteModel.getAdjacent();
        if ( cursor.moveToFirst() != false ){
            dashBoardViewModel.setLocationName(cursor.getString(cursor.getColumnIndex("name")));
            dashBoardViewModel.setPm25Value(cursor.getDouble(cursor.getColumnIndex("pm25")));
        }
    }

    /**
     * 取得GPS資訊
     */
    private void getLocationInfo(){
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ) {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if( location != null ){
                        gps_lat = location.getLatitude();
                        gps_log = location.getLongitude();
                        Toast.makeText(MainActivity.this,"Lat: "+location.getLatitude()+" Log: "+location.getLongitude(),Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
    @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    void GPSLocation() {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    void onGPSShowRationale(final PermissionRequest request) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage("你必須允許 "+getString(R.string.app_name)+"定位權限，否則無法正常運作，是否重新設定權限？")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .create()
                .show();
    }

    @OnPermissionDenied({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    void onGPSPermissionDenied() {
        Toast.makeText(this,"Permisson Denied",Toast.LENGTH_LONG).show();
    }

    @OnNeverAskAgain({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    void onGPSNeverAskAgain() {
    }

    /**
     * 設定 LocationRequest
     */
    private void setLocationRequest(){
        locationRequest = new LocationRequest()
                .setInterval(1000)  // 設定讀取位置資訊的間隔時間為一秒（1000ms）
                .setFastestInterval(1000)   // 設定讀取位置資訊最快的間隔時間為一秒（1000ms）
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY) ;  // 設定優先讀取高精確度的位置資訊（GPS）
    }
}
