package com.example.tourshare.base;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.example.tourshare.R;
import com.gyf.immersionbar.ImmersionBar;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportActivity;



public abstract class BaseActivity extends SupportActivity {
    protected TextView tvTitle;
    protected Bundle bundle;
    private Dialog dialog =null;
    private TextView tv_title;
    private LinearLayout lin_back;
    protected Toolbar mToolbar;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getIntent().getExtras();
        if (setImmersionBarColor() != 0 && setImmersionBarColor() != 2) {
            ImmersionBar.with(this).statusBarColor(setImmersionBarColor())
                    .statusBarDarkFont(true).keyboardEnable(true).fitsSystemWindows(true).init();
        }else if (setImmersionBarColor()==2){

        }else {
            ImmersionBar.with(this).statusBarColor(R.color.white)
                    .statusBarDarkFont(true).fitsSystemWindows(true).init();
        }
        if (getContentViewLayoutID() != 0) {
            setContentView(getContentViewLayoutID());
        }
        tv_title= findViewById(R.id.tv_title);
        lin_back = findViewById(R.id.lin_back);

        if (null != lin_back) {
            lin_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
        //BaseAppManager.getInstance().addActivity(this);
        if(bindEventBus()){
            EventBus.getDefault().register(this);
        }
        ButterKnife.bind(this);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        initView(savedInstanceState);
        initData();
        initListData();
    }

    protected void initView(Bundle savedInstanceState){

    };

    protected void initData(){
    }

    protected void initListData(){

    }
    protected LinearLayout getLin_back(){
        return lin_back;
    }

    // 菜单返回键
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // onDestroy
    @Override
    protected void onDestroy() {
        super.onDestroy();
//        hideLoadingDialog();
//        dialog = null;
        if(bindEventBus()){
            EventBus.getDefault().unregister(this);
        }
        //BaseAppManager.getInstance().removeActivity(this);
    }

    // 是否启用EventBus
    protected boolean bindEventBus(){
        return false;
    }

    protected View getLoadView(){
        return null;
    }

    // 空数据View
    public void setEmptyLoading(){
    }

    // 布局view
    protected abstract int getContentViewLayoutID();

    // 布局view
    public int setImmersionBarColor() {
        return 0;
    }

    /**
     * startActivity
     *
     * @param clazz
     */
    public void startToActivity(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    /**
     * startActivity with bundle
     *
     * @param clazz
     * @param bundle
     */
    public void startToActivity(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * startActivity with bundle
     *
     * @param clazz
     * @param bundle
     */
    public void startToActivity(Class<?> clazz, Bundle bundle, int requestCode) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * startActivity with bundle
     *
     * @param clazz
     */
    public void startToActivity(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(this, clazz);
        startActivityForResult(intent, requestCode);
    }

    /**
     * startActivity then finish
     *
     * @param clazz
     */
    protected void startToActivityThenKill(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        finish();
    }

    /**
     * startActivity with bundle then finish
     *
     * @param clazz
     * @param bundle
     */
    protected void startToActivityThenKill(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        finish();
    }

    /**
     * startActivityForResult
     *
     * @param clazz
     * @param requestCode
     */
    protected void startToActivityForResult(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(this, clazz);
        startActivityForResult(intent, requestCode);
    }

    /**
     * startActivityForResult with bundle
     *
     * @param clazz
     * @param requestCode
     * @param bundle
     */
    protected void startToActivityForResult(Class<?> clazz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }


    public String getText(View view){
        if(view instanceof TextView) {
            return ((TextView) view).getText().toString().trim();
        }else if(view instanceof EditText){
            return ((EditText) view).getText().toString().trim();
        }
        return "";
    }

    protected void setTVTitle(String title,int src,int color){
        if (color != 0){
            tv_title.setTextColor(color);
        }
        tv_title.setText(title);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_OK){
            return;
        }
        onActivityResulted(requestCode, resultCode, data);
    }

    protected void onActivityResulted(int requestCode, int resultCode, Intent data){}

    protected void onNetReload(View v) {

    }
    protected void onBack(View v) {
        finish();
    }
}
