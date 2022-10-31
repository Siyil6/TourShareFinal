package com.example.tourshare.ui;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourshare.R;
import com.example.tourshare.adapter.ImageAdapter;
import com.example.tourshare.base.BaseActivity;
import com.example.tourshare.base.MToastUtils;
import com.example.tourshare.bean.Command;
import com.example.tourshare.bean.ImageBean;
import com.example.tourshare.utils.CheckGetUtil;
import com.example.tourshare.utils.GPSUtils;
import com.example.tourshare.utils.PreferencesUtils;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class NewPostActivity extends BaseActivity {
    @BindView(R.id.iv_sub_title) TextView iv_sub_title;
    @BindView(R.id.usernameEd) AppCompatEditText edt_1;
    @BindView(R.id.re_1) RecyclerView re_1;
    @BindView(R.id.tv_l)TextView tv_l;
    @BindView(R.id.sw) Switch sw;
    private List<ImageBean> mList;
    private ImageAdapter adapter;
    private GPSUtils gpsUtils;
    private Location mLocation;
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_newpost;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        gpsUtils = new GPSUtils(this);//Initialize GPS
        new Thread(new Runnable() {
            @Override
            public void run() {
                mLocation = gpsUtils.getLocation();//Get location information
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateView(mLocation);
                    }
                });
            }
        }).start();
        iv_sub_title.setText("Send Post");
        iv_sub_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(getText(edt_1))){
                    MToastUtils.ShortToast("content can not be empty");
                    return;
                }
                if (mList.size()==0){
                    MToastUtils.ShortToast("please choose image");
                    return;
                }
                StringBuffer sb = new StringBuffer();
                for (ImageBean i:mList){
                    sb.append(i.getIcon()+",");
                }
                Command c = new Command(PreferencesUtils.getString(NewPostActivity.this,"id"),
                        TextUtils.isEmpty(PreferencesUtils.getString(NewPostActivity.this,"nick")) ? PreferencesUtils.getString(NewPostActivity.this,"name") : PreferencesUtils.getString(NewPostActivity.this,"nick"),
                        PreferencesUtils.getString(NewPostActivity.this,"icon"),getText(edt_1), CheckGetUtil.stampToDate(System.currentTimeMillis())
                        ,sb.toString(),getText(tv_l));
                c.save();
                finish();
            }
        });
        mList = new ArrayList<>();
        GridLayoutManager gm = new GridLayoutManager(this,3);
        adapter = new ImageAdapter(this,mList,R.layout.image_item);
        re_1.setLayoutManager(gm);
        re_1.setAdapter(adapter);

        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!b){
                    tv_l.setText("null");
                }
            }
        });
    }
    private void doTake() {
        Matisse.from(this)
                .choose(MimeType.ofImage(), false)
                .theme(com.zhihu.matisse.R.style.Matisse_Zhihu)
                .countable(true)
                .capture(true)
                .captureStrategy(new CaptureStrategy(
                        true,
                        "com.example.tourshare.fileprovider",
                        ""/*"abc"*/))
                .maxSelectable(6)
                .gridExpectedSize(getResources().getDimensionPixelSize(com.zhihu.matisse.R.dimen.album_item_height))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .setOnSelectedListener((uriList, pathList) -> {
                    Log.e("onSelected", "onSelected: pathList=" + pathList);
                })
                .showSingleMediaType(true)//true表示不能同时显示图片和视频
                .originalEnable(true)
                .maxOriginalSize(10)
                .autoHideToolbarOnSingleTap(true)
                .setOnCheckedListener(isChecked -> {
                    Log.e("isChecked", "onCheck: isChecked=" + isChecked);
                })
                .forResult(2);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 2){
            if(resultCode == RESULT_OK){
                mList.clear();
                for (int i =0;i<Matisse.obtainPathResult(data).size();i++){
                    mList.add(new ImageBean(Matisse.obtainPathResult(data).get(i)));
                }
                adapter.notifyDataSetChanged();
            }
        }
    }
    private void updateView(Location location) {
        mLocation = location;
        if (location != null) {
            tv_l.setText(GPSUtils.getLocalCity());
        } else {
            tv_l.setText("unknow");
        }
    }
    @OnClick({R.id.iv_add})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_add:
                doTake();
                break;
        }
    }
    @Override
    public int setImmersionBarColor() {
        return R.color.white;
    }
}
