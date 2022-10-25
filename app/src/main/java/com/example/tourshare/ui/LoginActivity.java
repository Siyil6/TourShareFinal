package com.example.tourshare.ui;

import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;

import com.example.tourshare.MainActivity;
import com.example.tourshare.R;
import com.example.tourshare.base.BaseActivity;
import com.example.tourshare.base.MToastUtils;
import com.example.tourshare.bean.User;
import com.example.tourshare.utils.PreferencesUtils;

import org.litepal.LitePal;

import butterknife.BindView;
import butterknife.OnClick;


public class LoginActivity extends BaseActivity {
    @BindView(R.id.edt_1) AppCompatEditText edt_1;
    @BindView(R.id.edt_2) AppCompatEditText edt_2;
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_login;
    }
    @OnClick({R.id.btn_1})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_1:
                if (TextUtils.isEmpty(getText(edt_1))){
                    MToastUtils.ShortToast("please input name");
                    return;
                }
                if (TextUtils.isEmpty(getText(edt_2))){
                    MToastUtils.ShortToast("please input Email address");
                    return;
                }
                User user = LitePal.where("name=? and pwd=?", getText(edt_1), getText(edt_2)).findFirst(User.class);
                if (user!=null){
                    PreferencesUtils.putString(LoginActivity.this,"id",user.getId()+"");
                    PreferencesUtils.putString(LoginActivity.this,"name",user.getName());
                    PreferencesUtils.putString(LoginActivity.this,"email",user.getEmail());
                    PreferencesUtils.putBoolean(LoginActivity.this,"login",true);
                    startToActivity(MainActivity.class);
                }else {
                    MToastUtils.ShortToast("The account or password is incorrect");
                }
                break;
        }
    }
}
