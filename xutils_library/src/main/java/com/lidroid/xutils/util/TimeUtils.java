package com.lidroid.xutils.util;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {

	public static final String MM_dd_HH_mm = "MM-dd HH:mm";

	public static final String YYYY_MM_DD = "yyyy-MM-dd";

	public static final String yyyy_MM_dd_HH_mm = "yyyy-MM-dd HH:mm";

	public static final String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";

	public static final String hh_mm_ss = "hh:mm:ss";

	public static final String HH_mm_ss = "HH:mm:ss";

	/**
	 * 
	 * @Description: 根据指定的字格式将时间转换为字符串
	 * 
	 * @param date
	 *            要转换的时间
	 * @param format
	 *            要转换的格式
	 * @return 返回转换后的时间, 如果转换失败返回空字符串.
	 */
	@SuppressLint("SimpleDateFormat")
	public static String parseTime(Date date, String format) {
		try {
			return new SimpleDateFormat(format).format(date);
		} catch (Exception e) {
			return "";
		}
	}
	
	/**   
	 * @Title: getStringDate   
	 * @Description: 将长时间格式字符串转换为时间
	 * @param date
	 * @param format
	 * @return String
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getStringDate(Long date, String format) 
	{
		try {
			return new SimpleDateFormat(format).format(date);
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 
	 * @Description: 获取当前系统的时间戳, 单位为毫秒.
	 * 
	 * @return 时间戳
	 */
	public static long getCurrentTimeMillis() {
		return System.currentTimeMillis();
	}

	/**
	 * @Title: getCurrentTime
	 * @Description: 获取当前时间
	 * @return String 返回时间字符串，格式为HH:mm:ss
	 */
	public static String getCurrentTime() {
		return parseTime(new Date(getCurrentTimeMillis()), HH_mm_ss);
	}

	/**
	 * 
	 * @Description: 将日期字符串转成日期对象 如“2014-12-13”转成正常的日期
	 * 
	 * @param num
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static Date String2Date(String num) {
		DateFormat df = DateFormat.getDateInstance();
		df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date = df.parse(num);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new Date();
	}

}
