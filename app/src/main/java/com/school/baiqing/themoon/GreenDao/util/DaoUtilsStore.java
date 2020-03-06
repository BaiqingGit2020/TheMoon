package com.school.baiqing.themoon.GreenDao.util;

import com.school.baiqing.themoon.GreenDao.DaoManager;
import com.school.baiqing.themoon.GreenDao.entity.Book;
import com.school.baiqing.themoon.GreenDao.gen.BookDao;

/**
 * 存放DaoUtils
 */
public class DaoUtilsStore
{
    private volatile static DaoUtilsStore instance = new DaoUtilsStore();
    private DaoUtils<Book> bookDaoUtils;

    public static DaoUtilsStore getInstance()
    {
        return instance;
    }

    private DaoUtilsStore()
    {
        DaoManager mManager = DaoManager.getInstance();
        BookDao _MeiziDao = mManager.getDaoSession().getBookDao();
        bookDaoUtils = new DaoUtils(Book.class, _MeiziDao);
    }

    public DaoUtils<Book> getBookDaoUtils()
    {
        return bookDaoUtils;
    }
}
