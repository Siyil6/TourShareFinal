package com.example.tourshare.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.tourshare.R;
import com.example.tourshare.adapter.Adapter2;
import com.example.tourshare.base.BaseActivity;
import com.example.tourshare.bean.Command;
import com.example.tourshare.bean.LikeBean;
import com.example.tourshare.bean.SqliteUtils;
import com.example.tourshare.bean.User;
import com.example.tourshare.utils.PreferencesUtils;
import com.google.android.material.tabs.TabLayout;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class UserInfoActivity extends BaseActivity {
    @BindView(R.id.iv_1)
    ImageView iv_1;
    @BindView(R.id.tv_1)
    TextView tv_1;
    @BindView(R.id.re_2)
    RecyclerView re2;
    @BindView(R.id.tab)
    TabLayout tab;

    private Adapter2 adapter2;
    private long user_id;


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.f4;
    }

    @Override
    protected void initData() {
        user_id  =  Long.valueOf(getIntent().getStringExtra("user_id"));
        Log.d("TAG", "initData: user_id "+user_id);
        initView();
        initUserInfo();

    }

    public void initUserInfo() {
        User user = SqliteUtils.selectUserById(user_id).get(0);
        if (!TextUtils.isEmpty(user.getIcon())) {
            Glide.with(this).asBitmap()
                    .load(user.getIcon())
                    .apply(new RequestOptions().placeholder(R.mipmap.png_head)
                            .circleCropTransform())
                    .into(iv_1);
        } else {
            Glide.with(this).asBitmap()
                    .load(R.mipmap.png_head)
                    .into(iv_1);
        }
//        if (!TextUtils.isEmpty(PreferencesUtils.getString(_mActivity,"nick"))){
//            tv_1.setText(PreferencesUtils.getString(_mActivity,"nick"));
//        }

        List<Command> mList = LitePal.where("user_id=?", String.valueOf(user_id))
                .find(Command.class);
                adapter2.setNewData(mList);


    }


    public void initView() {

        tab.addTab(tab.newTab().setText("Post"));
        tab.addTab(tab.newTab().setText("Liked"));
        adapter2 = new Adapter2(R.layout.adapter_2);
        LinearLayoutManager lm2 = new LinearLayoutManager(this);
        re2.setLayoutManager(lm2);
        re2.setAdapter(adapter2);
        tab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        List<Command> mList = LitePal.where("user_id=?", String.valueOf(user_id))
                                .find(Command.class);
                        adapter2.setNewData(mList);
                        break;
                    case 1:
                        List<Command> mList2 = new ArrayList<>();
                        List<LikeBean> likeList = LitePal.where("u_id=?", String.valueOf(user_id)).find(LikeBean.class);
                        for (LikeBean l : likeList) {
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
//                Bundle b = new Bundle();
//                b.putSerializable("data",((Command)adapter.getData().get(position)));
//                startToActivity(PostContentActivity.class,b);
            }
        });
    }


}