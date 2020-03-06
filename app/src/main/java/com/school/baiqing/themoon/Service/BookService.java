package com.school.baiqing.themoon.Service;

import com.school.baiqing.themoon.GreenDao.entity.Book;
import com.school.baiqing.themoon.GreenDao.util.DaoUtils;
import com.school.baiqing.themoon.GreenDao.util.DaoUtilsStore;

import java.util.List;

public class BookService {
    private DaoUtils<Book> bookDaoUtils;
    private DaoUtilsStore UtilStore;

    public BookService(){
        UtilStore = DaoUtilsStore.getInstance();
        bookDaoUtils = UtilStore.getBookDaoUtils();
    }
    public List<Book> getShelfBook(){

        return bookDaoUtils.queryAll();
    }
    public void AddBook(Book book){
        bookDaoUtils.insert(book);
    }
    public void AddBookList(List<Book> books){
        bookDaoUtils.insertMulti(books);
    }
    public void DeleteBook(Book book){
        bookDaoUtils.delete(book);
    }
}
