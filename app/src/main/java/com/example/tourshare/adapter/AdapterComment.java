package com.example.tourshare.adapter;

import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.tourshare.R;
import com.example.tourshare.bean.CommentBean;

public class AdapterComment extends BaseQuickAdapter<CommentBean, BaseViewHolder> {
    public AdapterComment(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, CommentBean item) {
        Glide.with(mContext).asBitmap()
                .load(item.getIcon())
                .apply(new RequestOptions().circleCropTransform())
                .into((ImageView) helper.getView(R.id.iv_1));
        helper.setText(R.id.tv_1,item.getName())
                .setText(R.id.tv_2,item.getAdd_time())
                .setText(R.id.tv_3,item.getTitle());
    }
}
