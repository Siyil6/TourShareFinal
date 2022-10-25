package com.example.tourshare.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import com.example.tourshare.MainActivity;
import com.example.tourshare.R;
import com.example.tourshare.base.BaseActivity;
import com.example.tourshare.utils.PreferencesUtils;
import com.tbruyelle.rxpermissions3.RxPermissions;


public class StartActivity extends BaseActivity {
   @Override
   protected int getContentViewLayoutID() {
      return R.layout.activity_start;
   }
   @Override
   protected void initView(Bundle savedInstanceState) {
      super.initView(savedInstanceState);
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
         initPermissions();
      }else{
         initLoad();
      }
   }


   private void initLoad(){
      if (PreferencesUtils.getBoolean(this,"login")){
         startActivity(new Intent(this, MainActivity.class));
      }else {
         startActivity(new Intent(this, WelcomeActivity.class));
      }
      finish();
   }
   @SuppressLint("AutoDispose")
   private void initPermissions() {
      RxPermissions rxPermissions = new RxPermissions(this);
      rxPermissions
              .requestEachCombined(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                      Manifest.permission.READ_EXTERNAL_STORAGE,
                      Manifest.permission.CAMERA,
                      Manifest.permission.ACCESS_FINE_LOCATION,
                      Manifest.permission.ACCESS_COARSE_LOCATION)
              .subscribe(permission -> {
                 if (permission.granted) {
                    initLoad();
                 } else if (permission.shouldShowRequestPermissionRationale) {
//                        Permissions();
                    initLoad();
                 } else {
                    finish();
                 }
              });
   }

}
