package com.example.tourshare.base;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;


public abstract class BaseFragment extends SupportFragment {

    protected Bundle bundle;
    protected Dialog mMaterialDialog = null;
    private Unbinder unbinder;
    protected boolean isVisable;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getArguments();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        if (getContentViewLayoutID() != 0) {
            view = inflater.inflate(getContentViewLayoutID(), container, false);
        } else {
            throw new IllegalArgumentException("You must return a right contentView layout resource Id");
        }
        if(bindEventBus()){
            EventBus.getDefault().register(this);
        }
        unbinder = ButterKnife.bind(this, view);
        initListView(view);
        initView(savedInstanceState);

        return view;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initData();
        initListData();
    }

    public void initView(Bundle savedInstanceState){

    }

    public void initData(){

    }

    protected void initListData(){

    }

    public void initListView(View root){}

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        isVisable = true;
    }


    public String getText(View view){
        if(view instanceof TextView) {
            return ((TextView) view).getText().toString().trim();
        }else if(view instanceof EditText){
            return ((EditText) view).getText().toString().trim();
        }
        return "";
    }

    // onDestroy
    @Override
    public void onDestroy() {
        super.onDestroy();
        mMaterialDialog = null;
        unbinder.unbind();
        if(bindEventBus()){
            EventBus.getDefault().unregister(this);
        }
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

    // 布局Layout
    protected abstract int getContentViewLayoutID();

    /**
     * startActivity
     *
     * @param clazz
     */
    public void startToActivity(Class<?> clazz) {
        Intent intent = new Intent(getActivity(), clazz);
        startActivity(intent);
    }

    /**
     * startActivity with bundle
     *
     * @param clazz
     * @param bundle
     */
    public void startToActivity(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(getActivity(), clazz);
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
        Intent intent = new Intent(getActivity(), clazz);
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
        Intent intent = new Intent(getActivity(), clazz);
        startActivityForResult(intent, requestCode);
    }

    /**
     * startActivity then finish
     *
     * @param clazz
     */
    protected void startToActivityThenKill(Class<?> clazz) {
        Intent intent = new Intent(getActivity(), clazz);
        startActivity(intent);
        getActivity().finish();
    }

    /**
     * startActivity with bundle then finish
     *
     * @param clazz
     * @param bundle
     */
    protected void startToActivityThenKill(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(getActivity(), clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        getActivity().finish();
    }

    /**
     * startActivityForResult
     *
     * @param clazz
     * @param requestCode
     */
    protected void startToActivityForResult(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(getActivity(), clazz);
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
        Intent intent = new Intent(getActivity(), clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

}