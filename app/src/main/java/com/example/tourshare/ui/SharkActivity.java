package com.example.tourshare.ui;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.tourshare.R;
import com.example.tourshare.adapter.Adapter2;
import com.example.tourshare.base.BaseActivity;
import com.example.tourshare.bean.Command;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;


public class SharkActivity extends BaseActivity {
    @BindView(R.id.re)
    RecyclerView re;
    private Adapter2 adapter;
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_shark;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        adapter = new Adapter2(R.layout.adapter_2);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        re.setLayoutManager(lm);
        re.setAdapter(adapter);
        List<Command> mList = LitePal.order("random()").limit(3).find(Command.class);
        adapter.setNewData(mList);
        adapter.setOnItemClickListener((adapter, view, position) -> {
            Bundle b = new Bundle();
            b.putSerializable("data",((Command)adapter.getData().get(position)));
            startToActivity(PostContentActivity.class,b);
        });
    }
}
