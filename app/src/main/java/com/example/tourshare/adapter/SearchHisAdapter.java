package com.example.tourshare.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourshare.R;
import com.example.tourshare.bean.SearchHis;

import java.util.List;

public class SearchHisAdapter extends RecyclerView.Adapter<SearchHisAdapter.ViewHolder> {
    private static final String TAG = SearchHisAdapter.class.getSimpleName();
    private Context context;
    private List<SearchHis> searchHisList;
    private int layoutId;
    private ClickCallBack clickCallBack;

    public interface ClickCallBack {
        void onClick(int position, SearchHis searchHis);


    }

    public SearchHisAdapter(Context context, List<SearchHis> searchHisList, int layoutId) {
        this.context = context;
        this.searchHisList = searchHisList;
        this.layoutId = layoutId;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_item;


        public ViewHolder(View view) {                       //参数view为RecycleView子项的最外层布局，可以从findviewbyid获取布局中的TextView实例
            super(view);
            tv_item = (TextView) view.findViewById(R.id.tv_item);

        }
    }


    @Override       //用于创建ViewHolder实例
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);//将布局加载进来传入构造函数，创建ViewHolder实例
        ViewHolder holder = new ViewHolder(view);
        return holder;                                  //将ViewHolder实例返回
    }

    @Override       //用于对RecyclerView子项的数据进行赋值，在每个子项被滚动到屏幕内时执行
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        SearchHis searchHis = searchHisList.get(position);
        holder.tv_item.setText(searchHis.getSearch_content());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickCallBack != null) {
                    clickCallBack.onClick(position, searchHis);
                }
            }
        });

    }


    @Override
    public int getItemCount() {         //告诉RecyclerView有多少子项，直接返回数据源长度
        return searchHisList.size();
    }

    public void setClickCallBack(ClickCallBack clickCallBack) {
        this.clickCallBack = clickCallBack;
    }
}