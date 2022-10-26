package com.example.tourshare.ui;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;

import com.bumptech.glide.Glide;
import com.example.tourshare.MainActivity;
import com.example.tourshare.R;
import com.example.tourshare.base.BaseActivity;
import com.example.tourshare.bean.SqliteUtils;
import com.example.tourshare.utils.PreferencesUtils;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;


import butterknife.BindView;
import butterknife.OnClick;


public class SettingActivity extends BaseActivity {
    @BindView(R.id.iv_updatehead) ImageView iv_updatehead;
    @BindView(R.id.tv_user_nickname)
    TextView tv_user_nickname;
    @BindView(R.id.tv_user_des)
    TextView tv_user_des;
    @BindView(R.id.tv_des)
    TextView tv_des;
    private String path="",nickname = "";
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setTVTitle("user Setting",0,0);
        nickname = TextUtils.isEmpty(PreferencesUtils.getString(this,"nick"))
                ?"":PreferencesUtils.getString(this,"nick");
        tv_user_nickname.setText(nickname);
        Glide.with(this).load(PreferencesUtils.getString(this,"icon")).
                placeholder(R.mipmap.png_head).into(iv_updatehead);
    }

    @Override
    protected void initData() {
        super.initData();
        if (!TextUtils.isEmpty(PreferencesUtils.getString(this,"des"))){
            tv_user_des.setText(PreferencesUtils.getString(this,"des"));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                doTake();
            }else{
                Toast.makeText(SettingActivity.this,"Camera permission not granted!",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void doTake() {
        Matisse.from(this)
                .choose(MimeType.ofImage(), false)
                .theme(com.zhihu.matisse.R.style.Matisse_Zhihu)
                .countable(true)
                .capture(true)
                .captureStrategy(new CaptureStrategy(
                        true,
                        "com.example.tourshare",
                        ""/*"abc"*/))
                .maxSelectable(1)
                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.dp_72))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .setOnSelectedListener((uriList, pathList) -> {
                    Log.e("onSelected", "onSelected: pathList=" + pathList);
                })
                .showSingleMediaType(true)//true Indicates that pictures and videos cannot be displayed at the same time
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
                path = Matisse.obtainPathResult(data).get(0);
                Glide.with(SettingActivity.this).load(path).into(iv_updatehead);
                PreferencesUtils.putString(SettingActivity.this,"icon",path);
            }
        }
    }

    @OnClick({R.id.iv_updatehead,R.id.re_user_nickname,R.id.re_user_des,R.id.log_out})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.iv_updatehead:
                if(ContextCompat.checkSelfPermission(SettingActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                    doTake();
                }else{
                    ActivityCompat.requestPermissions(SettingActivity.this,new String[]{Manifest.permission.CAMERA},1);
                }
                break;
            case R.id.re_user_nickname:
                AlertDialog.Builder b = new AlertDialog.Builder(SettingActivity.this);
                b.setTitle("Please Enter your nickname");
                EditText edt = new EditText(SettingActivity.this);
                b.setView(edt);
                b.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                b.setPositiveButton("Are you sure to make change", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        nickname = getText(edt);
                        tv_user_nickname.setText(nickname);
                        PreferencesUtils.putString(SettingActivity.this,"nick",nickname);
                        SqliteUtils.updateCommand(Long.valueOf(PreferencesUtils.getString(SettingActivity.this,"id")),nickname);
                    }
                });
                b.create();
                b.show();
                break;

            case R.id.re_user_des:
                AlertDialog.Builder c = new AlertDialog.Builder(SettingActivity.this);
                c.setTitle("please enter your description");
                EditText edt1 = new EditText(SettingActivity.this);
                c.setView(edt1);
                c.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                c.setPositiveButton("Are you sure to change?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       String  des = getText(edt1);
                        tv_user_des.setText(des);
                        PreferencesUtils.putString(SettingActivity.this,"des",des);
                        SqliteUtils.updateUserDes(Long.valueOf(PreferencesUtils.getString(SettingActivity.this,"id")),des);
                    }
                });
                c.create();
                c.show();
                break;
            case R.id.log_out:
                startToActivity(LoginActivity.class);
                break;
        }
    }

    @Override
    public int setImmersionBarColor() {
        return R.color.white;
    }


}
