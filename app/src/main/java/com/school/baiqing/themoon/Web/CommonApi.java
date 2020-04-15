package com.school.baiqing.themoon.Web;

import com.school.baiqing.themoon.Util.TianLaiReadUtil;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by zhao on 2017/7/24.
 */

public class CommonApi extends BaseApi{


    public static void RegisterToService(String id,String email,String password,final ResultCallback callback){
        Map<String,Object> register = new HashMap<>();
        register.put("username",id);
        register.put("email",email);
        register.put("password",password);
        postCommonReturnStringApi(URLCONST.method_RegisterToService, register, new ResultCallback() {
            @Override
            public void onFinish(Object o, int code) {
                callback.onFinish((String)o,code);
            }

            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }
        });

    }

    /**
     * 获取章节列表
     * @param url
     * @param callback
     */
    public static void getBookChapters(String url, final ResultCallback callback){

        getCommonReturnHtmlStringApi(url, null, "GBK", new ResultCallback() {
            @Override
            public void onFinish(Object o, int code) {
                callback.onFinish(TianLaiReadUtil.getChaptersFromHtml((String) o),0);
            }

            @Override
            public void onError(Exception e) {
                callback.onError(e);

            }
        });
    }

    /**
     * 获取章节正文
     * @param url
     * @param callback
     */
    public static void getChapterContent(String url, final ResultCallback callback){
        int tem = url.indexOf("\"");
        if (tem != -1){
            url = url.substring(0,tem);
        }
        getCommonReturnHtmlStringApi(URLCONST.nameSpace_tianlai + url, null, "GBK", new ResultCallback() {
            @Override
            public void onFinish(Object o, int code) {
                callback.onFinish(TianLaiReadUtil.getContentFormHtml((String)o),0);

            }

            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }
        });
    }

    /**
     * 搜索小说
     * @param key
     * @param callback
     */
    public static void search(String key, final ResultCallback callback){
        Map<String,Object> params = new HashMap<>();
        params.put("keyword", key);
        getCommonReturnHtmlStringApi(URLCONST.method_buxiu_search, params, "utf-8", new ResultCallback() {
            @Override
            public void onFinish(Object o, int code) {
                callback.onFinish(TianLaiReadUtil.getBooksFromSearchHtml((String)o),code);

            }

            @Override
            public void onError(Exception e) {
                callback.onError(e);

            }
        });
    }

    public static void getNewestAppVersion(final ResultCallback callback){
        getCommonReturnStringApi(URLCONST.method_getCurAppVersion,null,callback);
    }


}
