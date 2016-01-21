package com.yx.android.copyuc.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

/**   
 * @ClassName:  SPUtil   
 * @Description:SharedPreferences   
 * @author: Haoxl  
 * @date:   2015-3-18 下午3:36:14   
 *      
 */ 
public class SPUtil {
	
	private static SharedPreferences sp = UIUtils.getContext().getSharedPreferences("config", Context.MODE_APPEND);
	
	/**   
	 * @Title: putBoolean   
	 * @Description: 存储布尔值   
	 * @param key 键
	 * @param value  值     
	 */
	public static void putBoolean(String key, Boolean value) {
		sp.edit().putBoolean(key, value).commit();
	}

	/**   
	 * @Title: getBoolean   
	 * @Description: 获取布尔类型的值   
	 * @param key 键
	 * @param defValue 默认值
	 * @return boolean  键所对应的值，没有则返回默认值
	 */
	public static boolean getBoolean(String key, Boolean defValue) {
		return sp.getBoolean(key, defValue);
	}

	/**   
	 * @Title: putString   
	 * @Description: 存储String类型的值   
	 * @param key 键
	 * @param value 值   
	 */
	public static void putString(String key, String value) {
		if (key == null) {
			return;
		}
		sp.edit().putString(key, value).commit();
	}

	/**   
	 * @Title: getString   
	 * @Description: 获取String类型的值   
	 * @param key 键
	 * @param defValue 默认值
	 * @return String 键所对应的值，没有则返回默认值
	 */
	public static String getString(String key, String defValue) {
		return sp.getString(key, defValue);
	}

	/**   
	 * @Title: putInt   
	 * @Description: 存储int类型的值   
	 * @param key 键
	 * @param value 值
	 */
	public static void putInt(String key, int value) {
		sp.edit().putInt(key, value).commit();
	}

	/**   
	 * @Title: getInt   
	 * @Description: 获取int类型的值   
	 * @param key 键
	 * @param defValue 默认值
	 * @return int 键所对应的值，没有则返回默认值
	 */
	public static int getInt(String key, int defValue) {
		return sp.getInt(key, defValue);
	}

	/**   
	 * @Title: getAll   
	 * @Description: 获取所有的存储对象   
	 * @return Map<String,?>
	 */
	public static Map<String, ?> getAll() {
		return sp.getAll();
	}
}
