package edu.fzu.iot.ps;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.SeekBar;

import edu.fzu.iot.ps.adapter.OnSeekBarChangeAdapter;
import edu.fzu.iot.ps.util.PropertyUtil;
import edu.fzu.iot.ps.util.MyImageView;

/**
 * 通过滑动条调节图像属性，临时功能测试用，该界面默认不显示
 */
public class PropertyActivity extends Activity {
    private Bitmap mBitmap;
    private MyImageView mImageView;

    /**
     * SeekBar的中间值
     */
    private static final int MIDDLE_VALUE = 127;

    /**
     * SeekBar的最大值
     */
    private static final int MAX_VALUE = 255;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property);

        //图像初始化
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test);
        mImageView = findViewById(R.id.image_view);
        mImageView.setImageBitmap(mBitmap);

        //滑动条初始化
        SeekBar mHueBar = findViewById(R.id.hue_bar);
        SeekBar mSaturationBar = findViewById(R.id.saturation_bar);
        SeekBar mIntensityBar = findViewById(R.id.intensity_bar);

        mHueBar.setMax(MAX_VALUE);
        mSaturationBar.setMax(MAX_VALUE);
        mIntensityBar.setMax(MAX_VALUE);

        mHueBar.setProgress(MIDDLE_VALUE);
        mSaturationBar.setProgress(MIDDLE_VALUE);
        mIntensityBar.setProgress(MIDDLE_VALUE);

        mHueBar.setOnSeekBarChangeListener(new HueBarListener());
        mSaturationBar.setOnSeekBarChangeListener(new SaturationBarListener());
        mIntensityBar.setOnSeekBarChangeListener(new IntensityBarListener());
    }

    private class HueBarListener extends OnSeekBarChangeAdapter {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            float hue = (progress - MIDDLE_VALUE) * 1.0F / MIDDLE_VALUE * 180;
            mImageView.setImageBitmap(PropertyUtil.process(mBitmap, hue, PropertyUtil.HUE));
        }
    }

    private class SaturationBarListener extends OnSeekBarChangeAdapter {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            float saturation = progress * 1.0F / MIDDLE_VALUE;
            mImageView.setImageBitmap(PropertyUtil.process(mBitmap, saturation, PropertyUtil.SATURATION));
        }
    }

    private class IntensityBarListener extends OnSeekBarChangeAdapter {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            float intensity = progress * 1.0F / MIDDLE_VALUE;
            mImageView.setImageBitmap(PropertyUtil.process(mBitmap, intensity, PropertyUtil.INTENSITY));
        }
    }

}
