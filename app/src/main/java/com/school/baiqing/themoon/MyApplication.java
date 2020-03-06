package com.school.baiqing.themoon;

import android.app.Application;

import com.school.baiqing.themoon.GreenDao.DaoManager;

public class MyApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        initGreenDao();
    }

    private void initGreenDao()
    {
        DaoManager mManager = DaoManager.getInstance();
        mManager.init(this);
    }
}



