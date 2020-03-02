package com.school.baiqing.themoon.GreenDao;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.school.baiqing.themoon.GreenDao.gen.DaoMaster;
import com.school.baiqing.themoon.GreenDao.gen.DaoSession;

/**
 * GreenDaoHelper
 * Author: gjn.
 * Time: 2017/9/6.
 */

public class GreenDaoHelper {
    private static DaoMaster.DevOpenHelper devOpenHelper;
    private static SQLiteDatabase database;
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;

    /**
     * 初始化 建议放在Application中
     * @param context
     */
    public static void initDatabase(Context context){
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        devOpenHelper = new DaoMaster.DevOpenHelper(context,"Book_db",null);
        database = devOpenHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        daoMaster = new DaoMaster(database);
        daoSession = daoMaster.newSession();
    }

    public static SQLiteDatabase getDatabase() {
        return database;
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }
}
