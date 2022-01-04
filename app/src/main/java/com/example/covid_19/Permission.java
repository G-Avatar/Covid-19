package com.example.covid_19;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Permission {
    public void locationPermission(Activity obj){
        if (ContextCompat.checkSelfPermission(obj, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(obj, new String[] {Manifest.permission.ACCESS_FINE_LOCATION},1);
        }
    }

}
