package com.example.tourshare.fragment;

import static android.content.Context.SENSOR_SERVICE;
import static android.content.Context.VIBRATOR_SERVICE;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourshare.R;
import com.example.tourshare.adapter.SearchHisAdapter;
import com.example.tourshare.base.BaseFragment;
import com.example.tourshare.bean.SearchHis;
import com.example.tourshare.bean.SqliteUtils;
import com.example.tourshare.ui.SearchActivity;
import com.example.tourshare.ui.SharkActivity;
import com.example.tourshare.utils.PreferencesUtils;

import java.util.List;

import butterknife.BindView;


public class F2 extends BaseFragment {
    @BindView(R.id.iv_shake) ImageView ivShake;
    @BindView(R.id.edt_search) AppCompatEditText edt_search;
    @BindView(R.id.rv_his)  RecyclerView rv_his;

    private static final String TAG = "=========";
    private static final int SENSOR_SHAKE = 10;
    private SensorManager sensorManager;
    private Vibrator vibrator;
    private boolean isShaking = false;
    public static F2 newInstance() {
        F2 fragment = new F2();
        return fragment;
    }
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.f2;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(RecyclerView.HORIZONTAL);
        rv_his.setLayoutManager(manager);

        sensorManager = (SensorManager) _mActivity.getSystemService(SENSOR_SERVICE);
        vibrator = (Vibrator) _mActivity.getSystemService(VIBRATOR_SERVICE);
        edt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startToActivity(SearchActivity.class);
            }
        });
    }

    /**
     * 重力感应监听
     */
    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            // sensor info
            float[] values = event.values;
            float x = values[0]; // x gravity，right is positive
            float y = values[1]; // y gravity，front is positive
            float z = values[2]; // z gravity，up is positive
            Log.i(TAG, "Gravitational acceleration in the x-axis direction" + x + "；Gravitational acceleration in the y-axis direction" + y + "；Gravitational acceleration in the z-axis direction" + z);
            // Gravitational acceleration 40 shake
            int medumValue = 19;
            if (Math.abs(x) > medumValue || Math.abs(y) > medumValue || Math.abs(z) > medumValue) {
                vibrator.vibrate(200);
                Message msg = new Message();
                msg.what = SENSOR_SHAKE;
                handler.sendMessage(msg);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    /**
     * action performed
     */

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SENSOR_SHAKE:

                    if (!isShaking) {
                        startAnim();
                        isShaking = true;
                    }
                    break;
            }
        }
    };


    private void startAnim() {
        AnimationSet animup = new AnimationSet(true);
        TranslateAnimation animation0 = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0f,
                Animation.RELATIVE_TO_SELF, -0.5f,Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,0.f);
        animation0.setDuration(300);
        TranslateAnimation animation1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF,-0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f,Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,0.f);
        animation1.setDuration(300);
        animation1.setStartOffset(300);
        TranslateAnimation animation2 = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF, 0f,Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,0.f);
        animation2.setDuration(300);
        animation2.setStartOffset(600);
        animup.addAnimation(animation0);
        animup.addAnimation(animation1);
        animup.addAnimation(animation2);
        animup.setRepeatCount(Animation.RESTART);
        ivShake.startAnimation(animup);
        ivShake.getAnimation().setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }
            @Override
            public void onAnimationEnd(Animation animation) {
                isShaking = false;
                startToActivity(SharkActivity.class);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        if (sensorManager != null) {
            // The first parameter is the Listener, the second parameter is the resulting sensor type, and the third parameter value is the frequency at which sensor information is obtained
            sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        }
        initDrawRecyclerView();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (sensorManager != null) {
            // off listener
            sensorManager.unregisterListener(sensorEventListener);
        }
    }



    private    void  initDrawRecyclerView(){


        List<SearchHis> searchHisList  = SqliteUtils.selectSearchHisByUser(Long.valueOf(PreferencesUtils.getString(getActivity(),"id")));
        SearchHisAdapter searchHisAdapter  = new SearchHisAdapter(getActivity(),searchHisList,R.layout.search_his_item);
        rv_his.setAdapter(searchHisAdapter);
        searchHisAdapter.setClickCallBack(new SearchHisAdapter.ClickCallBack() {
            @Override
            public void onClick(int position, SearchHis searchHis) {

            }
        });



    }
}
