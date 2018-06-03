package edu.fzu.iot.ps;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.io.FileNotFoundException;

import edu.fzu.iot.ps.util.BlurUtil;
import edu.fzu.iot.ps.util.FilterUtil;
import edu.fzu.iot.ps.util.MosaicUtil;
import edu.fzu.iot.ps.util.MyImageView;
import edu.fzu.iot.ps.util.DiffuseUtil;
import edu.fzu.iot.ps.util.PermissionUtil;
import edu.fzu.iot.ps.util.SaveUtil;

/**
 * 该Activity用于功能测试
 */
public class TestActivity extends Activity {
    private Bitmap mBitmap;
    private MyImageView mImageView;

    //默认色彩矩阵参数
    private static float[] colorArray = {
            1, 0, 0, 0, 0,
            0, 1, 0, 0, 0,
            0, 0, 1, 0, 0,
            0, 0, 0, 1, 0
    };

    private int[] EditTextID = {R.id.Edit1, R.id.Edit2, R.id.Edit3, R.id.Edit4, R.id.Edit5,
            R.id.Edit6, R.id.Edit7, R.id.Edit8, R.id.Edit9, R.id.Edit10,
            R.id.Edit11, R.id.Edit12, R.id.Edit13, R.id.Edit14, R.id.Edit15,
            R.id.Edit16, R.id.Edit17, R.id.Edit18, R.id.Edit19, R.id.Edit20};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        //图像初始化
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test);
        mImageView = findViewById(R.id.image_view);
        mImageView.setImageBitmap(mBitmap);

        //权限申请
        PermissionUtil.verifyStoragePermissions(this);

        for (int i = 0; i < 20; i++) {
            EditText editText = findViewById(EditTextID[i]);
            editText.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        }

        //【滤镜处理】按键
        Button filterButton = findViewById(R.id.filter_button);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < 20; i++) {
                    EditText editText = findViewById(EditTextID[i]);
                    colorArray[i] = Float.valueOf(editText.getText().toString().trim());
                }
                mImageView.setImageBitmap(FilterUtil.process(mImageView.getBitmap(), colorArray));
            }
        });

        //【打开图片】按键
        Button openButton = findViewById(R.id.open_button);
        openButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
            }
        });

        //【保存】按键
        Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveUtil.save(mImageView.getBitmap());
            }
        });

        //【高斯模糊】按键
        Button blurButton = findViewById(R.id.blur_button);
        blurButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new BlurThread()).start();
            }
        });

        //【扩散】按键
        Button diffuseButton = findViewById(R.id.diffuse_button);
        diffuseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new DiffuseThread()).start();
            }
        });

        Button mosaicButton = findViewById(R.id.mosaic_button);
        mosaicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new MosaicThread()).start();
            }
        });

        //【扭曲】复选框
        CheckBox warpBox = findViewById(R.id.warp_box);
        warpBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mImageView.setIsWrap(b);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            ContentResolver cr = this.getContentResolver();
            try {
                mBitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                mImageView.setImageBitmap(mBitmap);
            } catch (FileNotFoundException e) {
                Log.e("Exception", e.getMessage(), e);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 图片模糊处理子线程
     */
    private class BlurThread implements Runnable {
        @Override
        public void run() {
            mBitmap = BlurUtil.process(mBitmap);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mImageView.setImageBitmap(mBitmap);
                }
            });
        }
    }

    /**
     * 图片扩散处理子线程
     */
    private class DiffuseThread implements Runnable {
        @Override
        public void run() {
            mBitmap = DiffuseUtil.process(mBitmap);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mImageView.setImageBitmap(mBitmap);
                }
            });
        }
    }

    /**
     * 图片马赛克处理子线程
     */
    private class MosaicThread implements Runnable {
        @Override
        public void run() {
            mBitmap = MosaicUtil.process(mBitmap);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mImageView.setImageBitmap(mBitmap);
                }
            });
        }
    }

}
