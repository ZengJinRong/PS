package edu.fzu.iot.ps.util;

import android.graphics.Bitmap;

import java.util.Random;

/**
 * 图像扩散处理工具
 */
public class DiffuseUtil {
    /**
     * 图像扩散处理
     * 实现原理：遍历像素点，使各像素点在指定范围内偏移随机距离
     */
    public static Bitmap process(Bitmap bitmapBefore) {
        Bitmap bitmapAfter = Bitmap.createBitmap(bitmapBefore.getWidth(),
                bitmapBefore.getHeight(), Bitmap.Config.RGB_565);

        int width = bitmapBefore.getWidth();
        int height = bitmapBefore.getHeight();

        Random random = new Random();

        int range = 20; //像素点扩散的范围

        //根据图像长宽遍历像素点
        for (int x = 1; x < width - 1; x++) {
            for (int y = 1; y < height - 1; y++) {

                //像素点在原图像中对应坐标
                int xFrom;
                int yFrom;

                //使像素点坐标在扩散范围内随机扩散
                do {
                    xFrom = x + random.nextInt(range);
                    yFrom = y + random.nextInt(range);
                } while (xFrom > width - 1 || yFrom > height - 1);

                //使原图像(xFrom,yFrom)坐标处像素对应到目标图像(x,y)坐标处
                int pixel = bitmapBefore.getPixel(xFrom, yFrom);
                bitmapAfter.setPixel(x, y, pixel);
            }
        }
        return bitmapAfter;
    }
}
