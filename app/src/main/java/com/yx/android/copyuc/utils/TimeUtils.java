package com.yx.android.copyuc.utils;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtils {

    public static final String MM_dd_HH_mm = "MM-dd HH:mm";

    public static final String yyyy_MM_dd = "yyyy-MM-dd";

    public static final String yyyy_MM_dd_HH_mm = "yyyy-MM-dd HH:mm";

    public static final String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";

    public static final String hh_mm_ss = "hh:mm:ss";

    public static final String HH_mm_ss = "HH:mm:ss";

    public static final String weekDay = "EEEE";

    /**
     * @param date   要转换的时间
     * @param format 要转换的格式
     * @return 返回转换后的时间, 如果转换失败返回空字符串.
     * @Description: 根据指定的字格式将时间转换为字符串
     */
    @SuppressLint("SimpleDateFormat")
    public static String parseTime(Date date, String format) {
        try {
            return new SimpleDateFormat(format).format(date);
        } catch (Exception e) {
            return "";
        }
    }

    public static String parseTime(long time, String format) {
        try {

            return new SimpleDateFormat(format).format(time);
        } catch (Exception e) {
            return "";
        }
    }

    public static String parseStringTime(String time, String format) {
        try {
            return new SimpleDateFormat(format).format(String2Date(time));
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * @param date
     * @param format
     * @return String
     * @Title: getStringDate
     * @Description: 将长时间格式字符串转换为时间
     */
    @SuppressLint("SimpleDateFormat")
    public static String getStringDate(Long date, String format) {
        try {
            return new SimpleDateFormat(format).format(date);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * @return 时间戳
     * @Description: 获取当前系统的时间戳, 单位为毫秒.
     */
    public static long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

    /**
     * @return String 返回时间字符串，格式为HH:mm:ss
     * @Title: getCurrentTime
     * @Description: 获取当前时间
     */
    public static String getCurrentTime() {
        return parseTime(new Date(getCurrentTimeMillis()), HH_mm_ss);
    }

    public static String getCurrentDate() {
        return parseTime(new Date(getCurrentTimeMillis()), yyyy_MM_dd);
    }

    public static String getYesterdayDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        String yesterday = new SimpleDateFormat(yyyy_MM_dd).format(cal.getTime());
        return yesterday;
    }

    /**
     * @param num
     * @return
     * @Description: 将日期字符串转成日期对象 如“2014-12-13”转成正常的日期
     */
    public static Date String2Date(String num) {
        DateFormat df = DateFormat.getDateInstance();
        df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date date = df.parse(num);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    public static Date String2Date(String num, String format) {
        DateFormat df = DateFormat.getDateInstance();
        df = new SimpleDateFormat(format);
        try {
            Date date = df.parse(num);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    /**
     * 获取两个日期的时间差
     */
    public static long getTimeInterval(String data, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        long interval = 0;
        try {
            Date currentTime = dateFormat.parse(dateFormat.format(new Date()));// 获取现在的时间
            Date beginTime = dateFormat.parse(data);
            interval = beginTime.getTime() - currentTime.getTime();// 时间差 单位毫秒
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return interval;
    }

    public static long getTimeInMills(String num) {
        return String2Date(num).getTime();
    }

    /**
     * 判断日期
     */
    public static String formatDateTime(String time) {
        Date date = String2Date(time);
        Calendar current = Calendar.getInstance();

        Calendar today = Calendar.getInstance();    //今天

        today.set(Calendar.YEAR, current.get(Calendar.YEAR));
        today.set(Calendar.MONTH, current.get(Calendar.MONTH));
        today.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH));
        //  Calendar.HOUR——12小时制的小时数 Calendar.HOUR_OF_DAY——24小时制的小时数
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);

        Calendar yesterday = Calendar.getInstance();    //昨天

        yesterday.set(Calendar.YEAR, current.get(Calendar.YEAR));
        yesterday.set(Calendar.MONTH, current.get(Calendar.MONTH));
        yesterday.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH) - 1);
        yesterday.set(Calendar.HOUR_OF_DAY, 0);
        yesterday.set(Calendar.MINUTE, 0);
        yesterday.set(Calendar.SECOND, 0);

        current.setTime(date);

        if (current.after(today)) {
            return "今天 " + time.split(" ")[1];
        } else if (current.before(today) && current.after(yesterday)) {
            return "昨天 " + time.split(" ")[1];
        } else {
//            int index = time.indexOf("-") + 1;
//            return time.substring(index, time.length());
            return parseStringTime(time, weekDay);
        }
    }
}
