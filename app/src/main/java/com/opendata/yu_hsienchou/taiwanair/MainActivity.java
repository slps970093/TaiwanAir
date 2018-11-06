package com.opendata.yu_hsienchou.taiwanair;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainActivity extends AppCompatActivity implements LocationListener,GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener {
    private LocationRequest locationRequest;
    private Location currentLocation;
    private GoogleApiClient googleApiClient;
    private FusedLocationProviderClient fusedLocationProviderClient;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivityPermissionsDispatcher.GPSLocationWithPermissionCheck(this);
        AirBoxAsyncTask airBoxAsyncTask = new AirBoxAsyncTask();
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        try{
            airBoxAsyncTask.execute();
            // 取得空氣資料
            if(airBoxAsyncTask.getStatusCode() == 200){
                ArrayList<AirDataModel> airModel = airBoxAsyncTask.getModel();

                locationRequest = new LocationRequest()
                        .setInterval(1000)  // 設定讀取位置資訊的間隔時間為一秒（1000ms）
                        .setFastestInterval(1000)   // 設定讀取位置資訊最快的間隔時間為一秒（1000ms）
                        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);   // 設定優先讀取高精確度的位置資訊（GPS）
                fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
                fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if(location != null){
                            Toast.makeText(MainActivity.this,"gps"+location.getLongitude(),Toast.LENGTH_LONG).show();
                        }
                    }
                });


            }
        }catch (Exception e){
            e.printStackTrace();
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
     * 定位狀態改變
     * @param provider
     * @param status
     * @param extras
     */
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    /**
     * 當GPS或網路定位功能開啟
     * @param provider
     */
    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(MainActivity.this,"開始定位！",Toast.LENGTH_SHORT).show();
    }

    /**
     * 當GPS或網路定位功能關閉時
     * @param provider
     */
    @Override
    public void onProviderDisabled(String provider) {

    }

    /**
     * 當地點改變
     * @param location
     */
    @Override
    public void onLocationChanged(Location location) {
        currentLocation = location;
        if(currentLocation != null){
            Toast.makeText(MainActivity.this,"gps"+currentLocation.getLongitude(),Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        LocationServices.getFusedLocationProviderClient(this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
