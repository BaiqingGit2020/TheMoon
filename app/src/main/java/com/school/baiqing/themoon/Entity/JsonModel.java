package com.school.baiqing.themoon.Entity;

import java.io.Serializable;

/**
 * Created by zhao on 2016/10/25.
 */

public class JsonModel implements Serializable {

    private static final long serialVersionUID = -7169864463597942730L;

    private int error;//错误码
    private boolean success;//请求是否成功
    private String result;//服务器返回的json数据存放与此


    public JsonModel() {

    }


    public int getError() {
        return this.error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String result) {
        this.result = result;
    }


    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }


    @Override
    public String toString() {
        return "JsonModel{" +
                "error=" + error +
                ", success=" + success +
                ", result='" + result + '\'' +
                '}';
    }
}
