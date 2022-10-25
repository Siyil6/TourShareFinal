package com.example.tourshare;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tourshare.adapter.MyFragmentAdapter;
import com.example.tourshare.fragment.F1;
import com.example.tourshare.fragment.F2;
import com.example.tourshare.fragment.F3;
import com.example.tourshare.fragment.F4;
import com.example.tourshare.wiget.NoScrollViewPager;
import com.google.android.material.tabs.TabLayout;
import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportActivity;

public class MainActivity extends SupportActivity {

    @BindView(R.id.bottom_tab_layout) TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    NoScrollViewPager viewPager;
    public static final int []mTabRes = new int[]{R.drawable.selector_tab_logo1,R.drawable.selector_tab_logo1,R.drawable.selector_tab_logo1,
            R.drawable.selector_tab_logo1};
    public static final String []mTabTitle = new String[]{"Home","Explore","Compass","Me"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }
    protected void initView() {
        ImmersionBar.with(this).init();
        ArrayList<Fragment> fragments = new ArrayList<>();
        // filling
        fragments.add(new F1());
        fragments.add(new F2());
        fragments.add(new F3());
        fragments.add(new F4());
        // Create ViewPager adapter
        MyFragmentAdapter myPagerAdapter = new MyFragmentAdapter(getSupportFragmentManager());
        myPagerAdapter.setFragments(fragments);
        // Set the adapter for ViewPager
        viewPager.setAdapter(myPagerAdapter);
        // Use TabLayout to associate with ViewPager
        mTabLayout.setupWithViewPager(viewPager);
        // TabLayout indicator (remember to manually create 4 Fragments, pay attention to whether it is a Fragment under the app package or a Fragment under the V4 package)
//        mTabLayout.setTabTextColors(ColorStateList);
        mTabLayout.getTabAt(0).setText(mTabTitle[0]).setIcon(mTabRes[0]);
        mTabLayout.getTabAt(1).setText(mTabTitle[1]).setIcon(mTabRes[1]);
        mTabLayout.getTabAt(2).setText(mTabTitle[2]).setIcon(mTabRes[2]);
        mTabLayout.getTabAt(3).setText(mTabTitle[3]).setIcon(mTabRes[3]);
        for (int i = 0; i < 4; i++) {
            setTabItem(i);
        }
    }

    public void setTabItem(int position) {
        View view = LayoutInflater.from(this).inflate(R.layout.home_tab_content, null);
        TextView textView = (TextView) view.findViewById(R.id.tab_content_text);
        textView.setText(mTabTitle[position]);
        ImageView imageView=(ImageView)view.findViewById(R.id.tab_content_image);
        imageView.setImageResource(mTabRes[position]);
        mTabLayout.getTabAt(position).setCustomView(view);

    }
}