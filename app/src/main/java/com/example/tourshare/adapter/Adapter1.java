package com.example.tourshare.adapter;

import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.tourshare.R;
import com.example.tourshare.bean.Command;


public class Adapter1 extends BaseQuickAdapter<Command, BaseViewHolder> {
    public Adapter1(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Command item) {
        helper.setText(R.id.tv_2,item.getUser_name());
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .circleCropTransform();
        Glide.with(mContext).asBitmap()
                .load(item.getUser_icon())
                .apply(options)
                .into((ImageView) helper.getView(R.id.iv_2));
        Glide.with(mContext).asBitmap()
                .load(item.getPath().split(",")[0])
                .into((ImageView) helper.getView(R.id.iv_1));
    }
}
