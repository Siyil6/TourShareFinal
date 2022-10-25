package com.example.tourshare;

import android.annotation.SuppressLint;
import android.app.Activity;

import com.example.tourshare.base.MToastUtils;

import org.litepal.LitePalApplication;



public class MApplication extends LitePalApplication {
    @SuppressLint("MissingPermission")
    @Override
    public void onCreate() {
        super.onCreate();
        MToastUtils.init(this);
    }

}
