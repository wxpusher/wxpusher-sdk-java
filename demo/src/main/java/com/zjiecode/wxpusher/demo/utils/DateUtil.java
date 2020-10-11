package com.zjiecode.wxpusher.demo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具
 */
public class DateUtil {
    /**
     * 获取当前时间
     */
    public static String getNowDateTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date());
    }

    /**
     * 获取当前日志
     *
     * @return
     */
    public static String getNewDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(new Date());
    }

    /**
     * 把时间转成 方便阅读的
     *
     * @param time 时间戳 ms
     */
    public static String getDateTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date(time));
    }

    /**
     * 获取当前的秒级时间戳
     */
    public static String getNowSecondStamp() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }

    /**
     * 把毫秒转成方便阅读的时长
     */
    public static String formatTimeLong(Long time) {
        long day = time / 86400000;
        time = time % 86400000;
        long hours = time / 3600000;
        time = time % 3600000;
        long minutes = time / 60000;
        time = time % 60000;
        long second = time / 1000;
        long milliseconds = time % 1000;
        StringBuilder result = new StringBuilder();
        if (day > 0) {
            result.append(day).append("天");
        }
        if (hours > 0) {
            result.append(hours).append("小时");
        }
        if (minutes > 0) {
            result.append(minutes).append("分钟");
        }
        if (second > 0) {
            result.append(second).append("秒");
        }
        if (milliseconds > 0) {
            result.append(second).append("毫秒");
        }
        return result.toString();
    }
}
