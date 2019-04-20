package com.huaweisoft.retrofitdemo.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

    /**
     * 获取当前时间字符串
     * @return
     */
    public static final  String getCurrentTimeStr() {
        long currentTime = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd hh:mm:ss");
        String timeStr = sdf.format(new Date(currentTime));
        return timeStr;
    }

}
