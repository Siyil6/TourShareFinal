package com.example.tourshare.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.tourshare.R;
import com.example.tourshare.adapter.Adapter1;
import com.example.tourshare.adapter.Adapter2;
import com.example.tourshare.base.BaseFragment;
import com.example.tourshare.bean.Command;
import com.example.tourshare.ui.NewPostActivity;
import com.example.tourshare.ui.PostContentActivity;
import com.example.tourshare.ui.UserInfoActivity;
import com.google.android.material.tabs.TabLayout;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;


public class F1 extends BaseFragment {
    @BindView(R.id.re_1) RecyclerView re1;
    @BindView(R.id.re_2) RecyclerView re2;
    @BindView(R.id.tab) TabLayout tab;
    private Adapter1 adapter1;
    private Adapter2 adapter2;
    public static F1 newInstance() {
        F1 fragment = new F1();
        return fragment;
    }
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.f1;
    }

    @Override
    public void onResume() {
        super.onResume();
        List<Command> mList = LitePal.order("pl desc").find(Command.class);
        List<Command> mList1 = new ArrayList<>();
        if (mList.size()>0){
            for (int i=0;i<1;i++){
                mList1.add(mList.get(new Random().nextInt(mList.size())));
            }
            adapter1.setNewData(mList1);
        }
        adapter2.setNewData(mList);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        tab.addTab(tab.newTab().setText("Hottest"));
        tab.addTab(tab.newTab().setText("Newest"));
        adapter1 = new Adapter1(R.layout.adapter_1);
        LinearLayoutManager lm1 = new LinearLayoutManager(_mActivity);
        lm1.setOrientation(LinearLayoutManager.HORIZONTAL);
        re1.setLayoutManager(lm1);
        re1.setAdapter(adapter1);

        adapter2 = new Adapter2(R.layout.adapter_2);
        LinearLayoutManager lm2 = new LinearLayoutManager(_mActivity);
        re2.setLayoutManager(lm2);
        re2.setAdapter(adapter2);

        adapter2.setOnItemClickListener((adapter, view, position) -> {
            Bundle b = new Bundle();
            b.putSerializable("data",((Command)adapter.getData().get(position)));
            startToActivity(PostContentActivity.class,b);
        });
        adapter2.setClickUserInfo(item -> {
            Log.d("TAG", "onclick: "+item);
            Intent   intent  = new Intent(getActivity(), UserInfoActivity.class);
            intent.putExtra("user_id",item.getUser_id());
            startActivity(intent);
        });

        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        List<Command> mLista = LitePal.order("pl desc").find(Command.class);
                        List<Command> mList1a = new ArrayList<>();
                        for (int i=0;i<1;i++){
                            mList1a.add(mLista.get(new Random().nextInt(mLista.size())));
                        }
                        adapter1.setNewData(mList1a);
                        adapter2.setNewData(mLista);
                        break;
                    case 1:
                        List<Command> mList = LitePal.order("add_time desc").find(Command.class);
                        List<Command> mList1 = new ArrayList<>();
                        for (int i=0;i<1;i++){
                            mList1.add(mList.get(new Random().nextInt(mList.size())));
                        }
                        adapter1.setNewData(mList1);
                        adapter2.setNewData(mList);
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
    @OnClick({R.id.fla})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.fla:
                startToActivity(NewPostActivity.class);
                break;
        }
    }
}
