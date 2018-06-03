package edu.fzu.iot.ps.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * 自定义ImageView类，对其进行功能拓展
 */
public class MyImageView extends ImageView {
    private boolean isWrap = false; //图像扭曲操作使能标志

    public MyImageView(Context context) {
        super(context);
        setFocusable(true);
    }

    public MyImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setFocusable(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isWrap) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    //当触点落下时
                case MotionEvent.ACTION_MOVE:
                    //当触点移动时

                    //触点横纵坐标与ImageView长宽的比值，用于计算触点在图像中的位置
                    float cx = event.getX() / getWidth();
                    float cy = event.getY() / getHeight();

                    //对图像指定位置进行扭曲操作
                    this.setImageBitmap(WarpUtil.process(getBitmap(), cx, cy));
                    break;
                case MotionEvent.ACTION_UP:
                    //当触点抬起时
                    break;
            }
        }
        return true;
    }

    public void setIsWrap(boolean isWrap) {
        this.isWrap = isWrap;
    }

    public Bitmap getBitmap() {
        return ((BitmapDrawable) getDrawable()).getBitmap();
    }
}
