package com.school.baiqing.themoon.Util;

import android.text.Html;
import android.util.Log;

import com.school.baiqing.themoon.GreenDao.entity.Book;
import com.school.baiqing.themoon.GreenDao.entity.Chapter;
import com.school.baiqing.themoon.View.Library.DiscoveryNovelData;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 天籁小说网html解析工具
 * Created by zhao on 2017/7/24.
 */

public class TianLaiReadUtil {


    /**
     * 从html中获取章节正文
     *
     * @param html
     * @return
     */
    public static String getContentFormHtml(String html) {

        Pattern pattern = Pattern.compile("<div id=\"content\">[\\s\\S]*?</div>");
        Matcher matcher = pattern.matcher(html);
        if (matcher.find()) {
            String content = Html.fromHtml(matcher.group(0)).toString();
            char c = 160;
            String spaec = "" + c;
            content = content.replace(spaec, "  ");
            content=content.replaceAll("[<br>]{0,}","").replaceAll("(?m)^\\s*$(\\n|\\r\\n)", "   ");//去掉空行
            return content;
        } else {
            return "";
        }
    }

    /**
     * 从html中获取章节列表
     *
     * @param html
     * @return
     */
    public static ArrayList<Chapter> getChaptersFromHtml(String html) {
        ArrayList<Chapter> chapters = new ArrayList<>();
        Pattern pattern = Pattern.compile("<dd><a href=\"([\\s\\S]*?)</a>");
//        Pattern pattern = Pattern.compile("<dd><a href=\"([\\s\\S]*?)\"");
        Matcher matcher = pattern.matcher(html);
        String lastTile = null;
        int i = 0;
        while (matcher.find()) {
            String content = matcher.group();
            String title = content.substring(content.indexOf("\">") + 2, content.lastIndexOf("<"));
            if (!StringHelper.isEmpty(lastTile) && title.equals(lastTile)) {
                continue;
            }
            Chapter chapter = new Chapter();
            chapter.setNumber(i++);
            chapter.setTitle(title);
            chapter.setUrl(content.substring(content.indexOf("\"") + 1, content.lastIndexOf("l\"") + 1));
            chapters.add(chapter);
            lastTile = title;
        }
        return chapters;
    }

    /**
     * 从搜索html中得到书列表
     *
     * @param html
     * @return
     */
    public static ArrayList<Book> getBooksFromSearchHtml(String html) {
        ArrayList<Book> books = new ArrayList<>();
        Document doc = Jsoup.parse(html);
//        Element node = doc.getElementById("results");
//        for (Element div : node.children()) {
        Elements divs = doc.getElementsByClass("result-list");
        Element div = divs.get(0);
//        if (!StringHelper.isEmpty(div.className()) && div.className().equals("result-list")) {
        for (Element element : div.children()) {
            Book book = new Book();
            Element img = element.child(0).child(0).child(0);
            book.setImgUrl(img.attr("src"));
            Element title = element.getElementsByClass("result-item-title result-game-item-title").get(0);
            book.setName(title.child(0).attr("title"));
            book.setChapterUrl(title.child(0).attr("href"));
            Element desc = element.getElementsByClass("result-game-item-desc").get(0);
            book.setDesc(desc.text());
            Element info = element.getElementsByClass("result-game-item-info").get(0);
            for (Element element1 : info.children()) {
                String infoStr = element1.text();
                if (infoStr.contains("作者：")) {
                    book.setAuthor(infoStr.replace("作者：", "").replace(" ", ""));
                } else if (infoStr.contains("类型：")) {
                    book.setType(infoStr.replace("类型：", "").replace(" ", ""));
                } else if (infoStr.contains("更新时间：")) {
                    book.setUpdateDate(infoStr.replace("更新时间：", "").replace(" ", ""));
                } else {
                    Element newChapter = element1.child(1);
                    book.setNewestChapterUrl(newChapter.attr("href"));
                    book.setNewestChapterTitle(newChapter.text());
                }
            }
            book.setLocation(APPCONST.BookLocation_network);
            books.add(book);
        }

        return books;
    }
    public static List<DiscoveryNovelData> getCategory(){
        List<DiscoveryNovelData> discoveryNovelDataList = new ArrayList<>();

        Document doc;
        try {
            doc =Jsoup.connect("https://www.baiqing.work/novel/catgory.html").get();
            Element divs = doc.getElementsByClass("main").get(0);
            for(Element ll : divs.children()){
                List<String> novelNameList = new ArrayList<>();
                List<String> coverUrlList = new ArrayList<>();
                DiscoveryNovelData discoveryNovelData = new DiscoveryNovelData();
                Element items = ll.getElementsByClass("ll").get(0);
                discoveryNovelData.setListname(items.attr("title"));
                for(Element element : items.children()){
                    Element item = element.child(0).child(0).child(0);
                    novelNameList.add(item.attr("alt"));
                    Log.d("themoonRecomm",item.attr("alt"));
                    coverUrlList.add("https://www.23txt.com"+item.attr("src"));
                }
                discoveryNovelData.setNovelNameList(novelNameList);
                discoveryNovelData.setCoverUrlList(coverUrlList);
                discoveryNovelDataList.add(discoveryNovelData);
            }

        }catch (IOException e){e.printStackTrace();}
        return discoveryNovelDataList;
    }

}
