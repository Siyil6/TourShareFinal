package com.example.tourshare.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.appcompat.widget.AppCompatEditText;

import com.example.tourshare.R;
import com.example.tourshare.base.BaseActivity;
import com.example.tourshare.base.MToastUtils;
import com.example.tourshare.bean.User;

import org.litepal.LitePal;

import butterknife.BindView;
import butterknife.OnClick;


public class RegisterActivity extends BaseActivity {
    @BindView(R.id.edt_1) AppCompatEditText edt_1;
    @BindView(R.id.edt_2) AppCompatEditText edt_2;
    @BindView(R.id.edt_3) AppCompatEditText edt_3;
    @BindView(R.id.edt_4) AppCompatEditText edt_4;
    @BindView(R.id.ck) CheckBox ck;
    private boolean isCk = false;
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        ck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    isCk = true;
                }else {
                    isCk = false;
                }
            }
        });
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
                if (TextUtils.isEmpty(getText(edt_3)) || TextUtils.isEmpty(getText(edt_4))){
                    MToastUtils.ShortToast("please input Password");
                    return;
                }
                if (!TextUtils.equals(getText(edt_3),getText(edt_4))){
                    MToastUtils.ShortToast("The passwords are different");
                    return;
                }
                if (!isCk){
                    MToastUtils.ShortToast("Please Agree to policy!");
                    return;
                }
                User user = LitePal.where("name=? and pwd=?", getText(edt_1), getText(edt_2)).findFirst(User.class);
                if (user!=null){
                    MToastUtils.ShortToast("The account already exists");
                    return;
                }
                User u = new User(getText(edt_1),getText(edt_2),getText(edt_3));
                u.save();
                MToastUtils.ShortToast("register success");
                finish();
                break;
        }
    }
}
