package com.example.tourshare.ui;

import android.view.View;

import com.example.tourshare.R;
import com.example.tourshare.base.BaseActivity;

import butterknife.OnClick;


public class WelcomeActivity extends BaseActivity {
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_welcome;
    }
    @OnClick({R.id.btn_1,R.id.btn_2})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_1:
                startToActivity(LoginActivity.class);
                break;
            case R.id.btn_2:
                startToActivity(RegisterActivity.class);
                break;
        }
    }
}
