package com.example.tourshare.base;

import android.app.Application;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tourshare.R;



public class MToastUtils {
    static Application app;
    public static void init(Application app){
        MToastUtils.app = app;
    }

    /**
     * Long Toast
     */
    public static void LongToast(String msg){
        View view= LayoutInflater.from(app).inflate(R.layout.view_toast_custom,null);
        TextView tv_msg = (TextView) view.findViewById(R.id.tvToast);
        tv_msg.setText(msg);
        Toast toast = new Toast(app);
        toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 120);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
    }

    /**
     * Long Toast
     */
    public static void ShortToast(String msg){
        View view= LayoutInflater.from(app).inflate(R.layout.view_toast_custom,null);
        TextView tv_msg = (TextView) view.findViewById(R.id.tvToast);
        tv_msg.setText(msg);
        Toast toast = new Toast(app);
        toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 120);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }
}
