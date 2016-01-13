package com.yx.android.copyuc.utils;

import android.annotation.SuppressLint;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @ClassName: SignUtils
 * @Description: 获取签名的工具类
 * @author: Haoxl
 * @date: 2015-3-30 下午3:05:58
 */
public class SignUtils {
    /**
     * @param url  访问的URL地址
     * @param time 当前时间
     * @return String
     * @Title: getSign
     * @Description: 获取签名, 先经url转码，再通过md5转码
     */
    public static String getSign(String url, String time) {
        String password = "";
//        String password = SPUtil.getString(Constants.ACCOUNTID + Constants.Preferences.PWD, "");
        TLog.d("sign", "sign===" + password + ", " + url + ", " + time);
        return md5Encode(password + url + time);
    }

    /**
     * @param source 待加密字符串
     * @return String 加密后的字符串
     * @Title: md5Encode
     * @Description: md5加密
     */
    @SuppressLint("DefaultLocale")
    public static String md5Encode(String source) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(source.getBytes("UTF-8"));
            byte b[] = md.digest();

            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }

            return buf.toString().toUpperCase();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
