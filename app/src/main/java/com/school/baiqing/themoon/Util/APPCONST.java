package com.school.baiqing.themoon.Util;
import android.os.Environment;

import com.school.baiqing.themoon.R;
public class APPCONST {
    public static String publicKey;//服务端公钥
    public static String privateKey;//app私钥
    public final static String s = "11940364935628058505";



    public static final String ALARM_SCHEDULE_MSG = "alarm_schedule_msg";

    public static final String FILE_DIR = "MissZzzReader";
    public static final String TEM_FILE_DIR = Environment.getExternalStorageDirectory() + "/MissZzzReader/tem/";
    public static final String UPDATE_APK_FILE_DIR = "MissZzzReader/apk/";
    public static long exitTime;
    public static final int exitConfirmTime = 2000;

    public static final String BOOK = "book";
    public static final String FONT = "font";




    public static final String FILE_NAME_SETTING = "setting";
    public static final String FILE_NAME_UPDATE_INFO = "updateInfo";

    public static final int REQUEST_FONT = 1001;
}
