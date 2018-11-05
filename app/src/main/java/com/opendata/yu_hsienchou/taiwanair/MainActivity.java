package com.opendata.yu_hsienchou.taiwanair;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainActivity extends AppCompatActivity {
    private LocationManager mLocationManger;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivityPermissionsDispatcher.GPSLocationWithPermissionCheck(this);
        mLocationManger = (LocationManager) getSystemService(Context.LOCATION_SERVICE); //取得目前的位置
        AirBoxAsyncTask airBoxAsyncTask = new AirBoxAsyncTask();

        try{
            airBoxAsyncTask.execute();
            // 取得空氣資料
            ArrayList<AirDataModel> airModel = airBoxAsyncTask.getModel();


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
}
