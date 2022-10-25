package com.example.tourshare.wiget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;


public class NoScrollViewPager extends ViewPager {
    private boolean isScroll;
    public NoScrollViewPager(Context context, AttributeSet attrs){
        super(context, attrs);
    }
    public NoScrollViewPager(Context context) {
        super(context);
    }
    /**
     * 1.dispatchTouchEvent is generally not processed
     *  if the default return value is modified, the child cannot receive the event
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);   // return true;不行
    }
    /**
     * Whether to intercept
     * Intercept: will go to your own onTouchEvent method
     * Do not intercept: events are passed to children
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // return false; // feasible, do not intercept events,
        // return true;//No, the child cannot handle the event
        //return super.onInterceptTouchEvent(ev);//No, there will be slight movements
        if (isScroll){
            return super.onInterceptTouchEvent(ev);
        }else{
            return false;
        }
    }
    /**
     * Whether to consume events
     * Consumption: the event ends
     * Do not consume: pass to the parent control
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //return false;// Feasible, no consumption, passed to the parent control
        //return true;// Feasible, consume, intercept events
        //super.onTouchEvent(ev); //No,
        //Although intercepted in onInterceptTouchEvent,
        //But if the child controls in the viewpage are not viewgroups, this method will still be called.
        if (isScroll){
            return super.onTouchEvent(ev);
        }else {
            return true;// Feasible, consume, intercept events
        }
    }
    public void setScroll(boolean scroll) {
        isScroll = scroll;
    }
}

