package com.example.tourshare.fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.tourshare.R;
import com.example.tourshare.adapter.Adapter2;
import com.example.tourshare.base.BaseFragment;
import com.example.tourshare.bean.Command;
import com.example.tourshare.bean.LikeBean;
import com.example.tourshare.ui.PostContentActivity;
import com.example.tourshare.ui.SettingActivity;
import com.example.tourshare.utils.PreferencesUtils;
import com.google.android.material.tabs.TabLayout;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;


public class F4 extends BaseFragment {
    @BindView(R.id.iv_1) ImageView iv_1;
    @BindView(R.id.tv_1) TextView tv_1;
    @BindView(R.id.tv_2) TextView tv_2;
    @BindView(R.id.re_2) RecyclerView re2;
    @BindView(R.id.tab) TabLayout tab;
    private Adapter2 adapter2;
    public static F4 newInstance() {
        F4 fragment = new F4();
        return fragment;
    }
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.f4;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(PreferencesUtils.getString(_mActivity,"icon"))){
            Glide.with(_mActivity).asBitmap()
                    .load(PreferencesUtils.getString(_mActivity,"icon"))
                    .apply(new RequestOptions() .placeholder(R.mipmap.png_head)
                            .circleCropTransform())
                    .into(iv_1);
        }else {
            Glide.with(_mActivity).asBitmap()
                    .load(R.mipmap.png_head)
                    .into(iv_1);
        }
        if (!TextUtils.isEmpty(PreferencesUtils.getString(_mActivity,"nick"))){
            tv_1.setText(PreferencesUtils.getString(_mActivity,"nick"));
        } if (!TextUtils.isEmpty(PreferencesUtils.getString(_mActivity,"des"))){
            tv_2.setText(PreferencesUtils.getString(_mActivity,"des"));
        }

        List<Command> mList = LitePal.where("user_id=?",PreferencesUtils.getString(_mActivity,"id"))
                .find(Command.class);
        adapter2.setNewData(mList);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        tab.addTab(tab.newTab().setText("My Post"));
        tab.addTab(tab.newTab().setText("Liked"));
        adapter2 = new Adapter2(R.layout.adapter_2);
        LinearLayoutManager lm2 = new LinearLayoutManager(_mActivity);
        re2.setLayoutManager(lm2);
        re2.setAdapter(adapter2);
        tab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        List<Command> mList = LitePal.where("user_id=?",PreferencesUtils.getString(_mActivity,"id"))
                                .find(Command.class);
                        adapter2.setNewData(mList);
                        break;
                    case 1:
                        List<Command> mList2 = new ArrayList<>();
                        List<LikeBean> likeList = LitePal.where("u_id=?", PreferencesUtils.getString(_mActivity, "id")).find(LikeBean.class);
                        for (LikeBean l :likeList){
                            Command c = LitePal.where("id=?", l.getC_id()).findFirst(Command.class);
                            mList2.add(c);
                        }
                        adapter2.setNewData(mList2);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        adapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle b = new Bundle();
                b.putSerializable("data",((Command)adapter.getData().get(position)));
                startToActivity(PostContentActivity.class,b);
            }
        });
    }
    @OnClick({R.id.iv_1})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_1:
                startToActivity(SettingActivity.class);
                break;
        }
    }
}
