package com.example.tourshare.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.tourshare.R;
import com.example.tourshare.bean.Command;
import com.example.tourshare.bean.CommentBean;
import com.example.tourshare.bean.LikeBean;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Adapter2 extends BaseQuickAdapter<Command, BaseViewHolder> {
    public Adapter2(int layoutResId) {
        super(layoutResId);
    }
    private ClickUserInfo clickUserInfo  ;
    public   interface   ClickUserInfo{
        void     onclik(Command item);
    }


    @Override
    protected void convert(@NonNull BaseViewHolder helper, Command item) {
        Adapter3 adapter = new Adapter3(R.layout.adapter_3);
        RecyclerView re = helper.getView(R.id.re_item);
        GridLayoutManager lm1 = new GridLayoutManager(mContext,3);
        re.setLayoutManager(lm1);
        re.setAdapter(adapter);
        List<String> mList = new ArrayList<>();
        String[] array = item.getPath().split(",");
        for (String string:array){
            mList.add(string);
        }
        adapter.setNewData(mList);
        int res = LitePal.where("c_id=?",item.getId()+"").count(LikeBean.class);
        int res2 = LitePal.where("c_id=?",item.getId()+"").count(CommentBean.class);
        helper.setText(R.id.tv_b,res2+"")
                .setText(R.id.tv_c,res+"")
                .setText(R.id.tv_a,(new Random().nextInt(30)+50)+"");
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .circleCropTransform();
        Glide.with(mContext).asBitmap()
                .load(item.getUser_icon())
                .apply(options)
                .into((ImageView) helper.getView(R.id.iv_1));
        helper.setText(R.id.nickname,item.getUser_name())
                .setText(R.id.tv_2,item.getAddress())
                .setText(R.id.tv_3,item.getTitle());

        helper.getView(R.id.iv_1).setOnClickListener(view -> {
            if (clickUserInfo  !=null){
                clickUserInfo.onclik(item);

            }
        });
        helper.getView(R.id.nickname).setOnClickListener(view -> {
            if (clickUserInfo  !=null){
                clickUserInfo.onclik(item);

            }
        });
    }

    public void setClickUserInfo(ClickUserInfo clickUserInfo) {
        this.clickUserInfo = clickUserInfo;
    }
}
