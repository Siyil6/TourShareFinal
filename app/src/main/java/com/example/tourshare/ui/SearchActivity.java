package com.example.tourshare.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.tourshare.R;
import com.example.tourshare.adapter.Adapter2;
import com.example.tourshare.adapter.Adapter4;
import com.example.tourshare.base.BaseActivity;
import com.example.tourshare.base.MToastUtils;
import com.example.tourshare.bean.Command;
import com.example.tourshare.bean.SqliteUtils;
import com.example.tourshare.bean.User;
import com.example.tourshare.utils.PreferencesUtils;
import com.google.android.material.tabs.TabLayout;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;


public class SearchActivity extends BaseActivity {
    @BindView(R.id.tab) TabLayout tab;
    @BindView(R.id.re) RecyclerView re;
    @BindView(R.id.re_2) RecyclerView re2;
    @BindView(R.id.edt_search) AppCompatEditText edt_search;
    private Adapter2 adapter;
    private Adapter4 adapter4;
    private int type=1;
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        tab.addTab(tab.newTab().setText("Post"));
        tab.addTab(tab.newTab().setText("User"));
        adapter = new Adapter2(R.layout.adapter_2);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        re.setLayoutManager(lm);
        re.setAdapter(adapter);

        adapter4 = new Adapter4(R.layout.adapter_5);
        LinearLayoutManager lm2 = new LinearLayoutManager(this);
        re2.setLayoutManager(lm2);
        re2.setAdapter(adapter4);

        List<Command> mList = LitePal.findAll(Command.class);
        adapter.setNewData(mList);

        adapter4.setNewData(LitePal.findAll(User.class));
        adapter4.setClickUserInfo(new Adapter4.ClickUserInfo() {
            @Override
            public void onclik(User item) {
                Intent intent  = new Intent(SearchActivity.this, UserInfoActivity.class);
                intent.putExtra("user_id",String.valueOf(item.getId()));
                startActivity(intent);
            }
        });
        edt_search.setOnEditorActionListener((v, actionId, event) -> {

            if(!TextUtils.isEmpty(getText(edt_search))){
                SqliteUtils.insertSearchHis(Long.valueOf(PreferencesUtils.getString(this,"id")),getText(edt_search));
            }
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if(!TextUtils.isEmpty(getText(edt_search))){

                    if ( type==1){
                        adapter.setNewData(LitePal.where("title like ?","%" + getText(edt_search) + "%").find(Command.class));
                    }else {
                        adapter4.setNewData(LitePal.where("name like ?","%" + getText(edt_search) + "%").find(User.class));
                    }
                    return true;
                }else {
                    if ( type==1){
                        adapter.setNewData(LitePal.findAll(Command.class));
                    }else {
                        adapter4.setNewData(LitePal.findAll(User.class));
                    }
                }
            }
            return false;
        });
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle b = new Bundle();
                b.putSerializable("data",((Command)adapter.getData().get(position)));
                startToActivity(PostContentActivity.class,b);
            }
        });
        tab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        type=1;
                        re.setVisibility(View.VISIBLE);
                        re2.setVisibility(View.GONE);
                        break;
                    case 1:
                        type=2;
                        re2.setVisibility(View.VISIBLE);
                        re.setVisibility(View.GONE);
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
    }
}
