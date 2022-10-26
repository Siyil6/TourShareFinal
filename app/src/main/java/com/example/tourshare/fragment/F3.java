package com.example.tourshare.fragment;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tourshare.R;
import com.example.tourshare.base.BaseFragment;

import butterknife.BindView;


public class F3 extends BaseFragment implements SensorEventListener {
    @BindView(R.id.imageview)ImageView imageview;
    @BindView(R.id.nickname) TextView tv_1;
    private SensorManager sensorManager;
    private float lastRotateDegree;
    private String mOrientaionText[] = new String[]{"N", "NE", "E", "SE", "S", "SW", "W", "NW"};
    public static F3 newInstance() {
        F3 fragment = new F3();
        return fragment;
    }
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.f3;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        sensorManager = (SensorManager) _mActivity.getSystemService(Context.SENSOR_SERVICE);
        //accelerometer sensor
        Sensor magneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        //Geomagnetic sensor
        Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sensorManager.registerListener(this, magneticSensor, SensorManager.SENSOR_DELAY_GAME);

        sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_GAME);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }

    float[] accelerometerValues = new float[3];

    float[] magneticValues = new float[3];

    @Override
    public void onSensorChanged(SensorEvent event) {
        // Determine whether the current is an acceleration sensor or a geomagnetic sensor
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            //Assignment calls clone method
            accelerometerValues = event.values.clone();
        } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            //Assignment calls clone method
            magneticValues = event.values.clone();
        }
        float[] R = new float[9];
        float[] values = new float[3];
        SensorManager.getRotationMatrix(R,null,accelerometerValues,magneticValues);
        sensorManager.getOrientation(R, values);

        Log.d("Main","values[0] :"+Math.toDegrees(values[0]));
        //The value range of values[0] is -180 to 180 degrees.
        //+-180 means due south, 0 degrees means due north, -90 means due west, +90 means due east

        //Invert the calculated rotation angle to rotate the compass background image
        float rotateDegree = -(float) Math.toDegrees(values[0]);
        if(rotateDegree<0){
            rotateDegree = 360+rotateDegree;
        }
        if (rotateDegree>360){
            rotateDegree -= 360;
        }
        tv_1.setText(mOrientaionText[((int) (rotateDegree+22.5f)%360)/45]+"-"+((int) rotateDegree));

        if (Math.abs(rotateDegree - lastRotateDegree) > 1) {
            RotateAnimation animation = new RotateAnimation(lastRotateDegree,rotateDegree, Animation.RELATIVE_TO_SELF,0.5f,
                    Animation.RELATIVE_TO_SELF,0.5f);
            animation.setFillAfter(true);
            imageview.startAnimation(animation); // Animated Rotation Sensor
            lastRotateDegree = rotateDegree;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
