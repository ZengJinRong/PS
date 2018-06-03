package edu.fzu.iot.ps.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

import edu.fzu.iot.ps.MyApplication;

/**
 * 图片保存工具
 */
public class SaveUtil {

    /**
     * 保存图片到本地相册
     *
     * @param bitmap 要保持到本地的Bitmap
     */
    public static void save(Bitmap bitmap) {
        Context context = MyApplication.getContext();

        try {
            String path = MediaStore.Images.Media.insertImage(
                    context.getContentResolver(),
                    bitmap,
                    "title",
                    "description"
            );

            //通知系统相册更新
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse(path)));

            Toast.makeText(context, "保存成功", Toast.LENGTH_SHORT).show();
        } catch (RuntimeException e) {
            Toast.makeText(context, "保存失败:" + e.toString(), Toast.LENGTH_LONG).show();
        }
    }

}
