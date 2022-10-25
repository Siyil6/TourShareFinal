package com.example.tourshare.adapter;

import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.tourshare.R;
import com.example.tourshare.bean.Command;


public class Adapter3 extends BaseQuickAdapter<String, BaseViewHolder> {
    public Adapter3(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {
        Glide.with(mContext).asBitmap()
                .load(item)
                .into((ImageView) helper.getView(R.id.iv_1));
    }
}
