package edu.fzu.iot.ps.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;

//TODO：整理代码，完善注释

/**
 * 图像扭曲处理工具
 */
public class WarpUtil {
    //定义两个常量用来表示该图片横向 纵向上 都被划分为20格
    private static final int WIDTH = 40;
    private static final int HEIGHT = 40;

    //记录该图片上 顶点的总数
    private static final int COUNT = (WIDTH + 1) * (HEIGHT + 1);

    //定义一个数组保存 Bitmap上的21* 21 个点的坐标
    private static final float[] verts = new float[COUNT * 2];

    //// 定义一个数组，记录Bitmap上的21 * 21个点经过扭曲后的座标
    //对图片扭曲的关键就是修改数组元素的值
    private static final float[] orig = new float[COUNT * 2];

    /**
     * 图像扭曲处理
     * 实现原理：//TODO:添加实现原理描述
     */
    public static Bitmap process(Bitmap bitmapBefore, float cx, float cy) {
        Bitmap bitmapAfter = Bitmap.createBitmap(bitmapBefore.getWidth(),
                bitmapBefore.getHeight(), Bitmap.Config.RGB_565);

        Canvas canvas = new Canvas(bitmapAfter);

        //获取图片的宽度和高度
        float bitmapWidth = bitmapBefore.getWidth();
        float bitmapHeight = bitmapBefore.getHeight();

        int index = 0;

        for (int y = 0; y <= HEIGHT; y++) {
            float fy = (bitmapHeight * y) / HEIGHT;

            for (int x = 0; x <= WIDTH; x++) {
                float fx = (bitmapWidth * x) / WIDTH;
                //初始化数组 verts orig . //两个数组均匀的保存了21*21个的 x,y 坐标
                orig[index * 2] = fx;
                verts[index * 2] = fx;
                orig[index * 2 + 1] = fy;
                verts[index * 2 + 1] = fy;
                index++;
            }
        }

        cx = cx * bitmapWidth;
        cy = cy * bitmapHeight;

        warp(cx, cy);

        //对Bitmap 按 verts 数组进行扭曲
        //从第一个点(由第5个点参数0控制) 开始扭曲
        canvas.drawBitmapMesh(bitmapBefore, WIDTH, HEIGHT, verts, 0, null, 0, null);

        return bitmapAfter;
    }

    private static void warp(float cx, float cy) {
        for (int i = 0; i < COUNT * 2; i += 2) {
            float dx = cx - orig[i];
            float dy = cy - orig[i + 1];

            float dd = dx * dx + dy * dy;

            //计算每个坐标点与当前点的(cx,cy)之间的距离
            float d = (float) Math.sqrt(dd);

            //计算扭曲度 距离当前点(cx,cy)越远,扭曲度越小
            float pull = 80000 / (dd * d);

            if (pull >= 1) {
                verts[i] = cx;
                verts[i + 1] = cy;

            } else {
                //控制各定点向触摸事件发生点偏移
                verts[i] = orig[i] + dx * pull;
                verts[i + 1] = orig[i + 1] + dy * pull;
            }
        }
    }
}
