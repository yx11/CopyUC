package com.lidroid.xutils.util;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

/**   
 * @ClassName:  ImageUtil   
 * @Description:处理图片的工具类   
 * @author: Haoxl  
 * @date:   2015-3-24 下午4:05:18   
 *      
 */ 
public class ImageUtil {

	private static Bitmap newbmp;

	/**   
	 * @Title: zoomBitmap   
	 * @Description: 按一定比例压缩图片
	 * @param bitmap
	 * @return Bitmap
	 */
	public static Bitmap zoomBitmap(Bitmap bitmap) {
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		Matrix matrix = new Matrix();
		if (w > h && w > 720) {
			float scaleWidth = ((float) 720 / w);
			matrix.postScale(scaleWidth, scaleWidth);
		} else if (h > w && h > 720) {
			float scaleHeight = ((float) 720 / h);
			matrix.postScale(scaleHeight, scaleHeight);
		}
		newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);

		return newbmp;
	}

	/**   
	 * @Title: getRoundedCornerBitmap   
	 * @Description: 获得圆角图片的方法   
	 * @param bitmap
	 * @param roundPx 一般设成14
	 * @return Bitmap
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {

		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}
	
	/**   
	 * @Title: getRoundBitmap   
	 * @Description: 获取圆形图片   
	 * @param bitmap
	 * @return Bitmap
	 */
	public static Bitmap getRoundBitmap(Bitmap bitmap) {  
        //圆形图片宽高  
        int width = bitmap.getWidth();  
        int height = bitmap.getHeight();  
        //正方形的边长  
        int r = 0;  
        //取最短边做边长  
        if(width > height) {  
            r = height;  
        } else {  
            r = width;  
        }  
        //构建一个bitmap  
        Bitmap backgroundBmp = Bitmap.createBitmap(r, r, Config.ARGB_8888);  
        //new一个Canvas，在backgroundBmp上画图  
        Canvas canvas = new Canvas(backgroundBmp);  
        Paint paint = new Paint();  
        //设置边缘光滑，去掉锯齿  
        paint.setAntiAlias(true);  
        //宽高相等，即正方形  
        RectF rect = new RectF(0, 0, r, r);  
        //通过制定的rect画一个圆角矩形，当圆角X轴方向的半径等于Y轴方向的半径时，  
        //且都等于r/2时，画出来的圆角矩形就是圆形  
        canvas.drawRoundRect(rect, r/2, r/2, paint);  
        //设置当两个图形相交时的模式，SRC_IN为取SRC图形相交的部分，多余的将被去掉  
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));  
        //canvas将bitmap画在backgroundBmp上  
        canvas.drawBitmap(bitmap, null, rect, paint);  
        //返回已经绘画好的backgroundBmp  
        return backgroundBmp;  
    }  
}
