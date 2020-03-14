package com.school.baiqing.themoon.GreenDao.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.school.baiqing.themoon.GreenDao.gen.BookDao;
import com.school.baiqing.themoon.GreenDao.gen.ChapterDao;
import com.school.baiqing.themoon.GreenDao.gen.DaoMaster;
import com.school.baiqing.themoon.GreenDao.gen.SearchHistoryDao;

import org.greenrobot.greendao.database.Database;

/**
 * Created by zhao on 2017/3/15.
 */

public class MySQLiteOpenHelper extends DaoMaster.OpenHelper {

    private Context mContext;


    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
        mContext = context;
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        //加入你要新建的或者修改的表的信息
        GreenDaoUpgrade.getInstance().migrate(db, BookDao.class,ChapterDao.class,SearchHistoryDao.class);

    }



}
