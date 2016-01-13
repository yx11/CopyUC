package com.yx.android.copyuc.utils;

import java.io.Closeable;
import java.io.IOException;

public class IOUtils {
	/**   
	 * @Title: close   
	 * @Description: 关闭流   
	 * @param io
	 * @return boolean
	 */
	public static boolean close(Closeable io) {
		if (io != null) {
			try {
				io.close();
			} catch (IOException e) {
			}
		}
		return true;
	}
}
