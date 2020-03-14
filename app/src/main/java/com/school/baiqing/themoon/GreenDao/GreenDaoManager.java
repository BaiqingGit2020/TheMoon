package com.school.baiqing.themoon.GreenDao;


import com.school.baiqing.themoon.GreenDao.gen.DaoMaster;
import com.school.baiqing.themoon.GreenDao.gen.DaoSession;
import com.school.baiqing.themoon.GreenDao.util.MySQLiteOpenHelper;
import com.school.baiqing.themoon.MyApplication;

/**
 * Created by zhao on 2017/3/15.
 */

public class GreenDaoManager {
    private static GreenDaoManager instance;
    private static DaoMaster daoMaster;
    private static MySQLiteOpenHelper mySQLiteOpenHelper;

    public static GreenDaoManager getInstance() {
        if (instance == null) {
            instance = new GreenDaoManager();
        }
        return instance;
    }

    public GreenDaoManager(){
        mySQLiteOpenHelper = new MySQLiteOpenHelper(MyApplication.getmContext(), "read" , null);
        daoMaster = new DaoMaster(mySQLiteOpenHelper.getWritableDatabase());
    }



    public DaoSession getSession(){
       return daoMaster.newSession();
    }

}
