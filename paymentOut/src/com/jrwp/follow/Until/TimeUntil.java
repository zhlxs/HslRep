package com.jrwp.follow.Until;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUntil {
    public static Long getMin(Date now, Date tokeDate) {
        long minute = 0;
        try {
            Calendar dateOne = Calendar.getInstance(), dateTwo = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateOne.setTime(now);    //设置为当前系统时间
            dateTwo.setTime(tokeDate);
            long timeOne = dateOne.getTimeInMillis();
            long timeTwo = dateTwo.getTimeInMillis();
            minute = ( timeOne - timeTwo ) / ( 1000 * 60 );//转化minute
            System.out.println("相隔" + minute + "分钟");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return minute;
    }
}
