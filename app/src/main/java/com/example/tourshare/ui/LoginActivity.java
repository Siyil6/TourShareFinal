package com.example.tourshare.ui;

import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;

import androidx.annotation.CheckResult;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatEditText;

import com.example.tourshare.MainActivity;
import com.example.tourshare.R;
import com.example.tourshare.base.BaseActivity;
import com.example.tourshare.base.MToastUtils;
import com.example.tourshare.bean.User;
import com.example.tourshare.utils.PreferencesUtils;

import org.litepal.LitePal;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;


public class LoginActivity extends BaseActivity {
    @BindView(R.id.username) AppCompatEditText usernameEdit;
    @BindView(R.id.password) AppCompatEditText passwordEdit;
    @BindView(R.id.remember) AppCompatCheckBox rememberPwd;
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_login;
    }

    @OnClick({R.id.sign_in, R.id.register})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.sign_in:
                signInCheck();
                break;
            case R.id.register:
                startToActivity(RegisterActivity.class);
        }
    }

    private void signInCheck() {
        if (TextUtils.isEmpty(getText(usernameEdit))){
            MToastUtils.ShortToast("please input name");
            return;
        }
        if (TextUtils.isEmpty(getText(passwordEdit))){
            MToastUtils.ShortToast("please input Email address");
            return;
        }
        // search for user
        User user = LitePal.where("name=? and pwd=?", getText(usernameEdit),
                getText(passwordEdit)).findFirst(User.class);
        if (user!=null){
            PreferencesUtils.putString(LoginActivity.this,"id",user.getId()+"");
            PreferencesUtils.putString(LoginActivity.this,"name",user.getName());
            PreferencesUtils.putString(LoginActivity.this,"email",user.getEmail());
            PreferencesUtils.putBoolean(LoginActivity.this,"login",true);
            PreferencesUtils.putBoolean(LoginActivity.this,"rememberPwd",
                    rememberPwd.isChecked());
            startToActivity(MainActivity.class);
        }else {
            MToastUtils.ShortToast("The account or password is incorrect");
        }
    }

}
