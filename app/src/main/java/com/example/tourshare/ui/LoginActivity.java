package com.example.tourshare.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatEditText;

import com.example.tourshare.MainActivity;
import com.example.tourshare.R;
import com.example.tourshare.base.BaseActivity;
import com.example.tourshare.base.MToastUtils;
import com.example.tourshare.bean.User;
import com.example.tourshare.utils.PreferencesUtils;
import com.google.firebase.auth.FirebaseAuth;

import org.litepal.LitePal;

import butterknife.BindView;
import butterknife.OnClick;


public class LoginActivity extends BaseActivity {
    @BindView(R.id.email) AppCompatEditText emailEdit;
    @BindView(R.id.password) AppCompatEditText passwordEdit;
    @BindView(R.id.remember) AppCompatCheckBox rememberPwd;
    private FirebaseAuth mAuth;
    private String tag = "login activity";
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_login;
    }
    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        passwordEdit.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
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
        if (TextUtils.isEmpty(getText(emailEdit))){
            MToastUtils.ShortToast("please input name");
            return;
        }
        if (TextUtils.isEmpty(getText(passwordEdit))){
            MToastUtils.ShortToast("please input Email address");
            return;
        }
        ProgressDialog pd = new ProgressDialog(LoginActivity.this);
        pd.setTitle("loading");
        pd.setCancelable(false);
        pd.create();
        pd.show();
        mAuth.signInWithEmailAndPassword(getText(emailEdit),getText(passwordEdit))
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(tag, "signInWithEmail:success");
                        //FirebaseUser user = mAuth.getCurrentUser();
                        pd.dismiss();
                        writeToLocal();

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(tag, "signInWithEmail:failure", task.getException());
                        MToastUtils.ShortToast("Authentication failed.");
                        pd.dismiss();
                    }
                });
    }

    private void writeToLocal() {
        // search for user
        User user = LitePal.where("email=? and pwd=?", getText(emailEdit),
                getText(passwordEdit)).findFirst(User.class);
        if (user!=null){
            PreferencesUtils.putString(LoginActivity.this,"id",user.getId()+"");
            PreferencesUtils.putString(LoginActivity.this,"name",user.getName());
            PreferencesUtils.putString(LoginActivity.this,"email",user.getEmail());
            PreferencesUtils.putString(LoginActivity.this,"des",user.getDes());
            PreferencesUtils.putString(LoginActivity.this,"nickname",user.getNickname());
            PreferencesUtils.putBoolean(LoginActivity.this,"login",true);
            PreferencesUtils.putBoolean(LoginActivity.this,"rememberPwd",
                    rememberPwd.isChecked());
        }else {
            //MToastUtils.ShortToast("The account or password is incorrect");
            // for user who not register on this device
            User u = new User("anonymous user",getText(emailEdit),getText(passwordEdit));
            u.save();

        }
        startToActivityThenKill(MainActivity.class);
    }

}
