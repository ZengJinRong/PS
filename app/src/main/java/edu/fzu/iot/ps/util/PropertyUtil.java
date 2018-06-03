package edu.fzu.iot.ps.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

/**
 * 图像属性调节工具类
 */
public class PropertyUtil {
    private static ColorMatrix mHueMatrix = new ColorMatrix();
    private static ColorMatrix mSaturationMatrix = new ColorMatrix();
    private static ColorMatrix mIntensityMatrix = new ColorMatrix();
    private static ColorMatrix mAllMatrix = new ColorMatrix();

    public static final int HUE = 0;
    public static final int SATURATION = 1;
    public static final int INTENSITY = 2;

    /**
     * 图像属性调节
     *
     * @param bitmapBefore 待处理的Bitmap对象
     * @param value        调节的数值
     * @param type         调节的属性类型
     */
    public static Bitmap process(Bitmap bitmapBefore, float value, int type) {
        // 创建一个相同尺寸的可变的位图区,用于绘制调色后的图片
        Bitmap bitmapAfter = Bitmap.createBitmap(
                bitmapBefore.getWidth(), bitmapBefore.getHeight(),
                Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmapAfter); // 得到画笔对象
        Paint paint = new Paint(); // 新建paint
        paint.setAntiAlias(true); // 设置抗锯齿,也即是边缘做平滑处理

        //判断要调节的属性类型
        switch (type) {
            case HUE:
                //调节色相
                setHue(value);
                break;
            case SATURATION:
                //调节饱和度
                setSaturation(value);
                break;
            case INTENSITY:
                //调节亮度
                setIntensity(value);
                break;
        }

        mAllMatrix.reset();
        //叠加色彩矩阵
        mAllMatrix.postConcat(mHueMatrix);
        mAllMatrix.postConcat(mSaturationMatrix);
        mAllMatrix.postConcat(mIntensityMatrix);

        //应用色彩矩阵
        paint.setColorFilter(new ColorMatrixColorFilter(mAllMatrix));

        //绘制目标Bitmap
        canvas.drawBitmap(bitmapBefore, 0, 0, paint);
        return bitmapAfter;
    }

    /**
     * 调节图像色相
     *
     * @param hue 色轮旋转的角度,正值表示顺时针旋转，负值表示逆时针旋转
     */
    private static void setHue(float hue) {
        mIntensityMatrix.reset(); // 设为默认值
        mIntensityMatrix.setRotate(0, hue); // 控制让红色区在色轮上旋转的角度
        mIntensityMatrix.setRotate(1, hue); // 控制让绿红色区在色轮上旋转的角度
        mIntensityMatrix.setRotate(2, hue); // 控制让蓝色区在色轮上旋转的角度
    }

    /**
     * 调节图像饱和度
     *
     * @param saturation 目标饱和度
     */
    private static void setSaturation(float saturation) {
        mSaturationMatrix.reset();
        mSaturationMatrix.setSaturation(saturation);
    }

    /**
     * 调节图像亮度
     *
     * @param intensity 目标亮度
     */
    private static void setIntensity(float intensity) {
        mHueMatrix.reset();
        // 红、绿、蓝三分量按相同的比例,最后一个参数1表示透明度不做变化
        mHueMatrix.setScale(intensity, intensity, intensity, 1);
    }
}


