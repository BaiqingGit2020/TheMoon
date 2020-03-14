package com.school.baiqing.themoon.Web;


import com.school.baiqing.themoon.Entity.JsonModel;

/**
 * Created by zhao on 2016/10/25.
 */

public interface JsonCallback {

    void onFinish(JsonModel jsonModel);

    void onError(Exception e);

}
