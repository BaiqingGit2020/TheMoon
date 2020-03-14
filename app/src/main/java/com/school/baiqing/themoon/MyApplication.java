package com.school.baiqing.themoon;

import android.app.Application;
import android.content.Context;
import android.os.Handler;


import org.geometerplus.android.fbreader.FBReaderApplication;
import org.geometerplus.android.fbreader.api.FBReaderIntents;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyApplication extends Application
{
    static {
        //这里需要自己设置自己 build.gradle 里的  applicationId 到DEFAULT_PACKAGE字段
        FBReaderIntents.DEFAULT_PACKAGE = "com.school.baiqing.themoon";
    }
    private static Handler handler = new Handler();
    private static MyApplication application;

    private ExecutorService mFixedThreadPool;
    @Override
    public void onCreate()
    {
        super.onCreate();
        application = this;
        FBReaderApplication.init(this);
        mFixedThreadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());//初始化线程池

    }
    /**
     * 主线程执行
     *
     * @param runnable
     */
    public static void runOnUiThread(Runnable runnable) {
        handler.post(runnable);
    }

    public static MyApplication getApplication() {
        return application;
    }

    public static Context getmContext() {
        return application;
    }

    public void newThread(Runnable runnable) {

        try {
            mFixedThreadPool.execute(runnable);
        } catch (Exception e) {
            e.printStackTrace();
            mFixedThreadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());//初始化线程池
            mFixedThreadPool.execute(runnable);
        }
    }
    public void shutdownThreadPool(){
        mFixedThreadPool.shutdownNow();
    }

}



