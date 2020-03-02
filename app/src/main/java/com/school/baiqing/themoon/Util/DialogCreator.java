package com.school.baiqing.themoon.Util;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.ImageView;

public class DialogCreator {
    private static ImageView ivLastSelectd = null;
    /**
     * 创建一个普通对话框（包含确定、取消按键）
     *
     * @param context
     * @param title
     * @param mesage
     * @param cancelable       是否允许返回键取消
     * @param positiveListener 确定按键动作
     * @param negativeListener 取消按键动作
     * @return
     */
    public static AlertDialog createCommonDialog(Context context, String title, String mesage, boolean cancelable,
                                                 DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener negativeListener) {

        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(context);
//        normalDialog.setIcon(R.drawable.icon_dialog);
        normalDialog.setTitle(title);
        normalDialog.setCancelable(cancelable);
        normalDialog.setMessage(mesage);
        normalDialog.setPositiveButton("确定", positiveListener);
        normalDialog.setNegativeButton("取消", negativeListener);
        // 显示
        final AlertDialog alertDialog = normalDialog.create();
        MyApplication.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    alertDialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return alertDialog;

    }
}
