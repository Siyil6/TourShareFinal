package com.example.tourshare.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.tourshare.R;
import com.example.tourshare.adapter.AdapterComment;
import com.example.tourshare.base.BaseActivity;
import com.example.tourshare.bean.Command;
import com.example.tourshare.bean.CommentBean;
import com.example.tourshare.bean.LikeBean;
import com.example.tourshare.utils.CheckGetUtil;
import com.example.tourshare.utils.PreferencesUtils;

import org.litepal.LitePal;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class PostContentActivity extends BaseActivity {
    @BindView(R.id.re) RecyclerView re;
    @BindView(R.id.iv_1)ImageView iv_1;
    @BindView(R.id.iv_3)ImageView iv_3;
    @BindView(R.id.nickname) TextView tv_1;
    @BindView(R.id.tv_4) TextView tv_4;
    @BindView(R.id.iv_dz)ImageView iv_dz;
    @BindView(R.id.edt_pl) AppCompatEditText edt_pl;
    @BindView(R.id.tv_3)TextView tv_3;
    private AdapterComment adapterComment;
    private Command item;
    private String u_id="";
    private LikeBean like;
    private boolean isLike = false;
    private List<CommentBean> cList;
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_postcontent;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        item = (Command) bundle.getSerializable("data");
        u_id = PreferencesUtils.getString(this,"id");
        like = LitePal.where("u_id=? and c_id=?", u_id,item.getId()+"").findFirst(LikeBean.class);
        if (like==null){
            isLike = false;
            iv_dz.setBackgroundResource(R.mipmap.dz);
        }else {
            isLike = true;
            iv_dz.setBackgroundResource(R.mipmap.dz2);
        }
        if (TextUtils.isEmpty(item.getAddress())){
            tv_3.setText("unknown");
        }else {
            tv_3.setText(item.getAddress());
        }
        Glide.with(this).asBitmap()
                .load(item.getUser_icon())
                .apply(new RequestOptions().circleCropTransform())
                .into(iv_1);
        Glide.with(this).asBitmap()
                .load(item.getPath().split(",")[0])
                .into(iv_3);
        tv_1.setText(item.getUser_name());
        tv_4.setText(item.getTitle());
        adapterComment = new AdapterComment(R.layout.adapter_4);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        re.setLayoutManager(lm);
        re.setAdapter(adapterComment);
        cList = LitePal.where("c_id=?",item.getId()+"").find(CommentBean.class);
        adapterComment.setNewData(cList);
    }

    @Override
    public int setImmersionBarColor() {
        return R.color.white;
    }
    @OnClick({R.id.iv_dz,R.id.btn_pl})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_dz:
                if (isLike){
                    LitePal.delete(LikeBean.class,like.getId());
                    isLike = false;
                    iv_dz.setBackgroundResource(R.mipmap.dz);
                }else {
                    LikeBean l = new LikeBean(u_id,item.getId()+"");
                    l.save();
                    isLike = true;
                    iv_dz.setBackgroundResource(R.mipmap.dz2);
                }
                break;
            case R.id.btn_pl:
                CommentBean c = new CommentBean(u_id,PreferencesUtils.getString(PostContentActivity.this,"icon"),
                        PreferencesUtils.getString(PostContentActivity.this,"nickname"),getText(edt_pl),
                        CheckGetUtil.stampToDate(System.currentTimeMillis()),item.getId()+"");
                c.save();
                cList.add(c);
                adapterComment.setNewData(cList);
                edt_pl.setText("");
                break;
        }
    }
}
