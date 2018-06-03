package edu.fzu.iot.ps.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

/**
 * 动态权限获取工具
 * 用于Android6.0及以上版本的权限获取
 */
public class PermissionUtil {
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    /**
     * 检查应用程序是否有权限写入设备存储,如果应用程序没有权限，则提示用户授予权限
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        //检查是否已获取到权限
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            //如果还未获取到权限，则向用户发出授权请求
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }
}
