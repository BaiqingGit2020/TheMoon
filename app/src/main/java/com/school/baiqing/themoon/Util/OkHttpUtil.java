package com.school.baiqing.themoon.Util;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpUtil {
    //登录
    public String loginWithOkHttp(String address,String account,String password){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("username",account)
                .add("password",password)
                .build();
        Request request = new Request.Builder()
                .url(address)
                .post(body)
                .build();
        try(Response response = client.newCall(request).execute()){
            if(response.isSuccessful()){
                return response.body().toString();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return " ";
    }
    //注册
    static void registerWithOkHttp(String address,String account,String password,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("registerAccount",account)
                .add("registerPassword",password)
                .build();
        Request request = new Request.Builder()
                .url(address)
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }
}
