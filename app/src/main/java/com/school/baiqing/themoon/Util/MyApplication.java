package com.school.baiqing.themoon.Util;

import android.app.Application;
import android.os.Handler;

import java.util.concurrent.ExecutorService;

public class MyApplication extends Application {
    private static Handler handler = new Handler();
    private static MyApplication application;
    private ExecutorService mFixedThreadPool;

    public static void runOnUiThread(Runnable runnable) {
        handler.post(runnable);
    }
}
