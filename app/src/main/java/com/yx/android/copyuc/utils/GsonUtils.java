package com.yx.android.copyuc.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

/**   
 * @ClassName:  GsonUtils   
 * @Description:处理Json的工具类   
 * @author: yx
 * @date:   2015-3-20 下午4:22:50   
 *      
 */ 
public class GsonUtils {
	/**   
	 * @Title: toJson   
	 * @Description: 将javabean对象转成json字符串   
	 * @param object bean对象
	 * @return String json字符串
	 */
	public static String toJson(Object object) {
		Gson gson = new Gson();
		String jsonString = gson.toJson(object);
		return jsonString;
	}

	/**   
	 * @Title: changeJsonToBean   
	 * @Description: 将json字符串转成bean对象   
	 * @param jsonString 要转换的json字符串
	 * @param cls bean对象的class
	 * @return T bean对象
	 */
	public static <T> T changeJsonToBean(String jsonString, Class<T> cls) {
		Gson gson = new Gson();
		T t = gson.fromJson(jsonString, cls);
		return t;
	}

	/**   
	 * @Title: changeJsonToList   
	 * @Description: 将json串转成list集合   
	 * @param jsonString
	 * @param cls
	 * @return List<T>
	 */
	public static <T> List<T> changeJsonToList(String jsonString, Class<T> cls) {
		Gson gson = new Gson();
		List<T> list = gson.fromJson(jsonString, new TypeToken<List<T>>() {
		}.getType());
		return list;
	}


	public static <T> List<Map<String, T>> changeJsonToListMaps(
			String jsonString) {
		List<Map<String, T>> list = null;
		Gson gson = new Gson();
		list = gson.fromJson(jsonString, new TypeToken<List<Map<String, T>>>() {
		}.getType());
		return list;
	}

	public static <T> Map<String, T> changeJsonToMaps(String jsonString) {
		Map<String, T> map = null;
		Gson gson = new Gson();
		map = gson.fromJson(jsonString, new TypeToken<Map<String, T>>() {
		}.getType());
		return map;
	}

	
	
	
	
}
