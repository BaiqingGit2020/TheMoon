package com.school.baiqing.themoon.Util;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import java.util.concurrent.ExecutorService;

public class MyApplication extends Application {
    private static Handler handler = new Handler();
    private static MyApplication application;
    private ExecutorService mFixedThreadPool;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        // 为应用设置异常处理
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init();

        context = getApplicationContext();

    }

    public static Context getContext() {
        return context;
    }
    public static void runOnUiThread(Runnable runnable) {
        handler.post(runnable);
    }
}
