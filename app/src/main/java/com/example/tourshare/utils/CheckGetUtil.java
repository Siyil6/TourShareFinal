package com.example.tourshare.utils;

import java.text.SimpleDateFormat;
import java.util.Date;


public class CheckGetUtil {
    public static String configUrl = "http://192.168.102.116:9005/";
    //Get the position where the interference line is drawn
    public static int[] getLine(int height, int width)
    {
        int [] tempCheckNum = {0, 0 ,0, 0, 0};
        for(int i = 0; i < 4; i+=2)
        {
            tempCheckNum[i] = (int) (Math.random() * width);
            tempCheckNum[i + 1] = (int) (Math.random() * height);
        }
        return tempCheckNum;
    }

    //Get the location of the distraction point
    public static int[] getPoint(int height, int width)
    {
        int [] tempCheckNum = {0, 0, 0, 0, 0};
        tempCheckNum[0] = (int) (Math.random() * width);
        tempCheckNum[1] = (int) (Math.random() * height);
        return tempCheckNum;
    }

    public static String stampToDate(long s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //If it is of long type, you don't need to write this step
        Date date = new Date(s);
        res = simpleDateFormat.format(date);
        return res;
    }
}
