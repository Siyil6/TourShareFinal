package com.example.tourshare.ui;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.appcompat.widget.AppCompatEditText;

import com.example.tourshare.R;
import com.example.tourshare.base.BaseActivity;
import com.example.tourshare.base.MToastUtils;
import com.example.tourshare.bean.User;

import org.litepal.LitePal;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;


public class RegisterActivity extends BaseActivity {
    @BindView(R.id.usernameEd) AppCompatEditText usernameEdit;
    @BindView(R.id.emailEd) AppCompatEditText emailEdit;
    @BindView(R.id.passwordEd) AppCompatEditText passwordEdit;
    @BindView(R.id.passwordConfirmEd) AppCompatEditText passwordConfirmEdit;
    @BindView(R.id.policyAgree) CheckBox policyAgree;
    private boolean isCk = false;
    private final String tag = "Register activity";
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        Log.i(tag,"register page launched");
        // monitoring policy agree states
        policyAgree.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b){
                isCk = true;
            }else {
                isCk = false;
            }
        });
        emailEdit.addTextChangedListener(new afterTextChangedWatcher(25,emailEdit));
        passwordEdit.addTextChangedListener(new afterTextChangedWatcher(12,emailEdit));
        passwordConfirmEdit.addTextChangedListener(new afterTextChangedWatcher(12,emailEdit));
    }

    @OnClick({R.id.register})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.register:
                if (TextUtils.isEmpty(getText(usernameEdit))){
                    MToastUtils.ShortToast("please input name");
                    return;
                }
                if (TextUtils.isEmpty(getText(emailEdit))){
                    MToastUtils.ShortToast("please input Email address");
                    return;
                }
                if (!isEmailValid(getText(emailEdit))) {
                    MToastUtils.ShortToast("Invalid Email address");
                    return;
                }
                if (!isPasswordValid(getText(passwordEdit))) {
                    Log.i(tag,getText(passwordEdit));
                    MToastUtils.ShortToast("Password must between 8-12 character," +
                            "\ncontain upper and lower case and number. ");
                    return;
                }
                if (TextUtils.isEmpty(getText(passwordEdit)) ||
                        TextUtils.isEmpty(getText(passwordConfirmEdit))){
                    MToastUtils.ShortToast("please input Password");
                    return;
                }
                if (!TextUtils.equals(getText(passwordEdit),getText(passwordConfirmEdit))){
                    MToastUtils.ShortToast("The passwords are different");
                    return;
                }
                if (!isCk){
                    MToastUtils.ShortToast("Please Agree to policy!");
                    return;
                }
                User user = LitePal.where("name=? and pwd=?", getText(usernameEdit),
                        getText(emailEdit)).findFirst(User.class);
                if (user!=null){
                    MToastUtils.ShortToast("The account already exists");
                    return;
                }
                User u = new User(getText(usernameEdit),getText(emailEdit),getText(passwordEdit));
                u.save();
                MToastUtils.ShortToast("register success");
                finish();
                break;
        }
    }

    private class afterTextChangedWatcher implements TextWatcher {
        private final int maxLength;
        private final EditText currentEdit;
        private String text;

        public afterTextChangedWatcher(int maxLength, EditText currentEdit) {
            this.maxLength = maxLength;
            this.currentEdit = currentEdit;

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            //hide keyboard after maximum length has been reached
            if (s.toString().length() == maxLength) {
                Log.i(tag,currentEdit.getText().toString());
                // this object is able to manipulate system components
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                // two parameters, the window component that receive input, and a flag.
                imm.hideSoftInputFromWindow(currentEdit.getWindowToken(),
                        0);
            }
        }
    }
    private boolean isPasswordValid(String pwd) {
        if (TextUtils.isEmpty(pwd))
            return false;
        String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,12}$";
        return pwd.matches(regex);
    }
    private boolean isEmailValid(String email) {
        if (TextUtils.isEmpty(email))
            return false;
        String regex = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
        return email.matches(regex);
    }
}
