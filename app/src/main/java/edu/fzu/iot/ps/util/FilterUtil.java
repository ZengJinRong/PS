package edu.fzu.iot.ps.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

/**
 * 色彩滤镜处理工具
 */
public class FilterUtil {
    /**
     * 图像色彩滤镜处理
     * 实现原理：通过颜色矩阵ColorMatrix实现
     *
     * @param bitmapBefore 待处理的Bitmap对象
     * @param colorArray   色彩矩阵参数
     * @return 处理结果
     */
    public static Bitmap process(Bitmap bitmapBefore, float[] colorArray) {

        Bitmap bitmapAfter = Bitmap.createBitmap(
                bitmapBefore.getWidth(), bitmapBefore.getHeight(),
                Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmapAfter);
        Paint paint = new Paint();
        paint.setAntiAlias(true);   //设置抗锯齿,即边缘平滑处理

        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.set(colorArray);    //设置色彩矩阵参数

        //应用色彩矩阵
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));

        //绘制目标Bitmap
        canvas.drawBitmap(bitmapBefore, 0, 0, paint);

        return bitmapAfter;
    }
}
