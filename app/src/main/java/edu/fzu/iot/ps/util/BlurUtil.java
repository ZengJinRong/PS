package edu.fzu.iot.ps.util;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * 图像模糊处理工具
 */
public class BlurUtil {
    // 高斯矩阵
    private static int[] gauss = new int[]{
            1, 2, 1,
            2, 4, 2,
            1, 2, 1};

    /**
     * 图像模糊处理
     * 实现原理：循环遍历各像素点，计算当前像素点颜色值与周围像素点颜色值的加权平均，并将计算结果作为当前像
     * 素点经处理后的颜色值。加权平均系数采用高斯矩阵。
     *
     * @param bitmapBefore 待处理的Bitmap对象
     * @return 处理结果
     */
    public static Bitmap process(Bitmap bitmapBefore) {

        int width = bitmapBefore.getWidth();
        int height = bitmapBefore.getHeight();
        Bitmap bitmapAfter = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

        //像素点原始颜色值
        int pixColor;

        int delta = 16;     //加权系数总和，值越小图片会越亮，越大则越暗

        int[] pixels = new int[width * height];     //用于保存所有像素点的颜色值

        //获取各像素点颜色值
        bitmapBefore.getPixels(pixels, 0, width, 0, 0, width, height);

        //根据图片宽高循环遍历像素点
        for (int i = 1; i < height - 1; i++) {
            for (int k = 1; k < width - 1; k++) {

                int idx = 0;     //当前像素点对应加权系数在高斯矩阵中的索引

                //初始化目标颜色值的RGB分量
                int newR = 0;
                int newG = 0;
                int newB = 0;

                //循环遍历当前位置周围的像素点
                for (int m = -1; m <= 1; m++) {
                    for (int n = -1; n <= 1; n++) {
                        pixColor = pixels[(i + m) * width + k + n];

                        //将原始颜色值分解为RGB分量
                        int pixR = Color.red(pixColor);
                        int pixG = Color.green(pixColor);
                        int pixB = Color.blue(pixColor);

                        //求加权和
                        newR = newR + (pixR * gauss[idx]);
                        newG = newG + (pixG * gauss[idx]);
                        newB = newB + (pixB * gauss[idx]);
                        idx++;
                    }
                }

                //求加权平均
                newR /= delta;
                newG /= delta;
                newB /= delta;

                //保证数字范围[0-255]
                newR = Math.min(255, Math.max(0, newR));
                newG = Math.min(255, Math.max(0, newG));
                newB = Math.min(255, Math.max(0, newB));

                pixels[i * width + k] = Color.argb(255, newR, newG, newB);
            }
        }
        bitmapAfter.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmapAfter;
    }
}
