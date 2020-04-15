package com.school.baiqing.themoon.View.User;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gcssloop.encrypt.symmetric.DESUtil;
import com.github.ybq.android.spinkit.SpinKitView;
import com.school.baiqing.themoon.ActivityMain;
import com.school.baiqing.themoon.R;
import com.school.baiqing.themoon.Util.APPCONST;
import com.school.baiqing.themoon.Util.IdentifyingCode;
import com.school.baiqing.themoon.Util.TextHelper;
import com.school.baiqing.themoon.Web.CommonApi;
import com.school.baiqing.themoon.Web.ResultCallback;

import java.io.IOException;

import javax.crypto.Cipher;

import group.pals.android.lib.ui.filechooser.utils.TextUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class ActivityRegister extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "Registered";
    //注册信息控件
    private TextView user_id,user_email,user_password1,user_password2,identifycode;
    //注册信息
    private String id,name,password1,password2,result,real_code,user_code,DESPassword;
    private TextView register;
    private SpinKitView loading;
    private SharedPreferences.Editor editor;
    //图片验证码
    private ImageView identifyingCode;
    private Handler mhandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 100:
                    TextHelper.showLongText("服务器错误");
                    break;
                case 101:
                    TextHelper.showLongText("用户已存在");
                    break;
                case 102:
                    TextHelper.showLongText("数据库错误");
                    break;
                case 0:
                    setLoginState();
                    startActivity(new Intent(ActivityRegister.this,ActivityMain.class));
                    ActivityRegister.this.finish();
                    break;
            }
            loading.setVisibility(View.INVISIBLE);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //初始化控件
        loading = findViewById(R.id.skv_register_loading);
        user_id = findViewById(R.id.user_id_register);
        user_email = findViewById(R.id.user_name_register);
        user_password1 = findViewById(R.id.user_password_register1);
        user_password2 = findViewById(R.id.user_password_register2);
        register = findViewById(R.id.register);
        identifycode = findViewById(R.id.identifyingcode_edittext);

        register.setOnClickListener(this);
        //这里是初始化并设置监听，记得要保存对的验证码，用来验证后面用户输入的对不对
        identifyingCode=(ImageView)findViewById(R.id.identifyingcode_image);
        identifyingCode.setOnClickListener(this);
        //忽略大小写
        identifyingCode.setImageBitmap(IdentifyingCode.getInstance().createBitmap());
        real_code=IdentifyingCode.getInstance().getCode().toLowerCase();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register:
                //读取输入信息
                id = user_id.getText().toString();
                name = user_email.getText().toString();
                password1 = user_password1.getText().toString();
                password2 = user_password2.getText().toString();
                user_code = identifycode.getText().toString().toLowerCase();
                Log.i(TAG,id+"+"+password1);
                //检查注册条件
                if(check()){
                    DESPassword=DESUtil.des(password1,APPCONST.DESKey,Cipher.ENCRYPT_MODE);
                    Register();
                }
                break;
            case R.id.identifyingcode_image:
                identifyingCode.setImageBitmap(IdentifyingCode.getInstance().createBitmap());
                real_code=IdentifyingCode.getInstance().getCode().toLowerCase();
                break;
        }
    }

    /**
     * 注册网络请求
     */
    public void Register(){
        loading.setVisibility(View.VISIBLE);
        CommonApi.RegisterToService(id, name, DESPassword, new ResultCallback() {
            @Override
            public void onFinish(Object o, int code) {
//                TextHelper.showLongText((String) o);
                try {
                    Thread.sleep(2000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                mhandler.sendMessage(mhandler.obtainMessage(code));
            }

            @Override
            public void onError(Exception e) {
                TextHelper.showLongText( e.toString());
                loading.setVisibility(View.INVISIBLE);
            }
        });
    }

    /**
     * 本地检查输入内容
     * @return
     */
    private boolean check(){
        if(password1==null||name==null){
            Toast.makeText(ActivityRegister.this,"用户名或密码不能为空",Toast.LENGTH_LONG).show();
            return false;
        }
        if(!name.contains("@")){
            TextHelper.showLongText("邮箱格式不正确");
        }
        if(!user_code.equals(real_code)){
            Toast.makeText(ActivityRegister.this,"验证码不正确",Toast.LENGTH_LONG).show();
            return false;
        }
        if(password1.length()<5)
        {
            Toast.makeText(ActivityRegister.this,"密码不能少于5位",Toast.LENGTH_LONG).show();
            return false;
        }
        if(!password1.equals(password2)){
            Toast.makeText(ActivityRegister.this,"两次输入的密码不一致",Toast.LENGTH_LONG).show();
            return false;
        }
        Log.i(TAG,"可以注册");
        return true;
    }

    public void setLoginState() {
        editor = getSharedPreferences("LoginMessage",MODE_PRIVATE).edit();
        editor.putString("UserName",id);
        editor.putString("PassWord",DESPassword);
        editor.putBoolean("isLunched",true);
        editor.apply();
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }
}
