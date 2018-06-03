package edu.fzu.iot.ps.util;

import android.graphics.Bitmap;

/**
 * 图片马赛克处理工具
 */
public class MosaicUtil {

    /**
     * 马赛克处理
     * 实现原理：将像素点按坐标位置划分区域，同一区域内所有像素点的颜色值以区域中心像素点为准
     *
     * @param bitmapBefore 待处理的Bitmap对象
     * @return 处理结果
     */
    public static Bitmap process(Bitmap bitmapBefore) {
        Bitmap bitmapAfter = Bitmap.createBitmap(bitmapBefore.getWidth(),
                bitmapBefore.getHeight(), Bitmap.Config.RGB_565);

        int width = bitmapBefore.getWidth();
        int height = bitmapBefore.getHeight();

        int[] pixels = new int[width * height];     //用于保存所有像素点的颜色值

        //获取各像素点颜色值
        bitmapBefore.getPixels(pixels, 0, width, 0, 0, width, height);

        int range = 20; //马赛克一格区域的大小

        //根据图片宽高循环遍历像素点
        for (int y = 1; y < height - range + 1; y += range) {
            for (int x = 1; x < width - range + 1; x += range) {

                //循环遍历当前区域所有像素点
                for (int m = 0; m < range; m++) {
                    for (int n = 0; n < range; n++) {
                        pixels[(y + m) * width + x + n] = pixels[y * width + x];
                    }
                }

            }
        }
        bitmapAfter.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmapAfter;
    }
}
