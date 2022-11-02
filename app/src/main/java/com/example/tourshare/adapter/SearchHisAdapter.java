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


        public ViewHolder(View view) {

            super(view);
            tv_item = (TextView) view.findViewById(R.id.tv_item);

        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);

        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
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