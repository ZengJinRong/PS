package edu.fzu.iot.ps;

import android.app.Application;
import android.content.Context;

/**
 * 用于保存全局变量
 */
public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
