package com.school.baiqing.themoon.Util;

import android.util.Log;

import com.school.baiqing.themoon.GreenDao.entity.Book;
import com.school.baiqing.themoon.View.DiscoveryNovelData;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DaoCaoRenReadUtil {
    public static List<Book> getRecommend(){
        List<Book> books = new ArrayList<>();
        Document doc;
        try {
            doc =Jsoup.connect("https://www.daocaorenshuwu.com/").get();
            Element divs = doc.getElementsByClass("row").get(1);
            for(Element element : divs.children()){
                Book book = new Book();
                Element title = element.child(0).child(0).child(0);
                book.setName(title.attr("title"));
                book.setImgUrl(title.attr("abs:src"));
                Element Auther = element.child(0).child(1).child(0).child(1);
                book.setAuthor(Auther.text());
                Element desc = element.child(0).child(1).child(1);
                book.setDesc(desc.text());
                books.add(book);
//                Log.d("themoonRecomm",book.getName());
            }

        }catch (IOException e){e.printStackTrace();}
        return books;
    }

}
