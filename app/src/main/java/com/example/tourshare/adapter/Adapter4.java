package com.example.tourshare.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.tourshare.R;
import com.example.tourshare.bean.Command;
import com.example.tourshare.bean.User;


public class Adapter4 extends BaseQuickAdapter<User, BaseViewHolder> {
    public Adapter4(int layoutResId) {
        super(layoutResId);
    }
    private ClickUserInfo clickUserInfo  ;
    public   interface   ClickUserInfo{
        void     onclik(User item);
    }


    @Override
    protected void convert(@NonNull BaseViewHolder helper, User item) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.png_head)
                .circleCropTransform();
        if (TextUtils.isEmpty(item.getIcon())){
            Glide.with(mContext).asBitmap()
                    .load(R.mipmap.png_head)
                    .apply(options)
                    .into((ImageView) helper.getView(R.id.iv_1));
        }else {
            Glide.with(mContext).asBitmap()
                    .load(item.getIcon())
                    .apply(options)
                    .into((ImageView) helper.getView(R.id.iv_1));
        }
        helper.setText(R.id.tv_1,item.getName())
                .setText(R.id.tv_3,item.getEmail());

        helper.getView(R.id.tv_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickUserInfo  !=null){
                    clickUserInfo.onclik(item);

                }
            }
        });
        helper.getView(R.id.tv_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickUserInfo  !=null){
                    clickUserInfo.onclik(item);

                }
            }
        });
    }

    public void setClickUserInfo(ClickUserInfo clickUserInfo) {
        this.clickUserInfo = clickUserInfo;
    }
}
