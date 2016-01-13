package com.yx.android.copyuc.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.SmsManager;

import java.util.ArrayList;

/**
 * @ClassName:  TelephonyUtils
 * @Description: 拨打电话发送短信的工具类
 * @author: Haoxl
 * @date:   2015-3-20 下午4:35:58
 *
 */
public class TelephonyUtils {
	/**
	 * @Title: callPhone
	 * @Description: 直接拨打电话
	 * @param context 上下文对象
	 * @param phoneNum 电话号码
	 */
	public static void callPhone(Context context, String phoneNum) {
		if (phoneNum != null && phoneNum.trim().length() > 0) {
			Intent intent = new Intent(Intent.ACTION_CALL);
			Uri uri = Uri.parse("tel:" + phoneNum);
			intent.setData(uri);
			context.startActivity(intent);
		}
	}

	/**
	 * @Title: CallSysDial
	 * @Description:  跳转到拨号界面
	 * @param context
	 * @param phoneNum
	 */
	public static void CallSysDial(Context context, String phoneNum) {
		Intent intent = new Intent(Intent.ACTION_DIAL);
		Uri uri = Uri.parse("tel:" + phoneNum);
		intent.setData(uri);
		context.startActivity(intent);
	}

	/**
	 * @Title: sendMessage
	 * @Description: 跳转到系统的短信编辑界面
	 * @param context
	 * @param phoneNum
	 * @param content void
	 */
	public static void sendMessage(Context context, String phoneNum, String content) {
		Intent intent = new Intent(Intent.ACTION_SENDTO);
		Uri uri = Uri.parse("smsto:" + phoneNum);
		intent.setData(uri);
		intent.putExtra("sms_body", content);
		context.startActivity(intent);
	}

	/**
	 * @Title: sendHideMessage
	 * @Description: 直接发送短信，无界面
	 * @param context
	 * @param phoneNum
	 * @param content void
	 */
	public static void sendHideMessage(Context context, String phoneNum, String content) {
		if (phoneNum != null && phoneNum.trim().length() > 0) {
			SmsManager manager = SmsManager.getDefault();
			// 消息内容大于70就对消息进行拆分
			if (content.length() > 70) {
				ArrayList<String> arrayList = manager.divideMessage(content);
				for (String message : arrayList) {
					manager.sendTextMessage(phoneNum, null, message, null, null);
				}
			} else {
				manager.sendTextMessage(phoneNum, null, content, null, null);
			}
		}
	}

}
