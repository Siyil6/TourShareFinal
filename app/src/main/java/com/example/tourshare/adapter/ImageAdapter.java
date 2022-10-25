package com.example.tourshare.adapter;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.tourshare.R;
import com.example.tourshare.base.ReBaseRecyclerAdapter;
import com.example.tourshare.base.ReViewHolder;
import com.example.tourshare.bean.ImageBean;

import java.util.List;


public class ImageAdapter extends ReBaseRecyclerAdapter<ImageBean> {
    private Context mcontext;

    public ImageAdapter(Context context, List<ImageBean> list, int itemLayoutId) {
        super(context, list, itemLayoutId);
        this.mcontext = context;
    }

    @Override
    public void convert(ReViewHolder holder, ImageBean item, int position, boolean isScrolling) {
        Log.e("-------",item.getIcon());
        Glide.with(mcontext).load(item.getIcon()).into((ImageView) holder.getView(R.id.iv_item));
    }
}

