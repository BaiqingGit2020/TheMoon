package com.school.baiqing.themoon.View.User;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.gcssloop.encrypt.symmetric.DESUtil;
import com.github.ybq.android.spinkit.SpinKitView;
import com.school.baiqing.themoon.ActivityMain;
import com.school.baiqing.themoon.Entity.User;
import com.school.baiqing.themoon.R;
import com.school.baiqing.themoon.Util.APPCONST;

import java.io.IOException;

import javax.crypto.Cipher;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class ActivityLogin extends AppCompatActivity implements View.OnClickListener {
    private static final int Login_success = 1 ;
    private static final int Login_failed = 2 ;
    private static final int Login_wrong = 3 ;
    private String username,password,result,DESPassword;
    private User person = new User();
    private TextView register,view_username,view_password;
    private TextView view_login,view_signup;
    private SpinKitView spinKitView;
    private SharedPreferences.Editor editor;
    private SharedPreferences pref;
    private static final String TAG = "LoginHttp";
    private static final String LOGIN_SUCCESS = "LoginSuccess";
    private static final String LOGIN_FAIL= "LoginFail";
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            spinKitView.setVisibility(View.GONE);
            if(msg.arg1==Login_success){
                Intent intent = new Intent(ActivityLogin.this,ActivityMain.class);
                startActivity(intent);
                ActivityLogin.this.finish();
            }
            else if(msg.arg1==Login_failed){
                Toast.makeText(ActivityLogin.this,"用户名或密码错误",Toast.LENGTH_LONG).show();
            }
            else if(msg.arg1==Login_wrong){
                Toast.makeText(ActivityLogin.this,"请检查网络连接",Toast.LENGTH_LONG).show();
            }
        }
    };
    private CheckBox view_rememberpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        register = findViewById(R.id.register);
        //初始化控件
        view_username = findViewById(R.id.user_id);
        view_password = findViewById(R.id.user_password);
        view_login = findViewById(R.id.button_login);
        view_rememberpassword = findViewById(R.id.remember_password);
        view_signup = findViewById(R.id.register);
        spinKitView = findViewById(R.id.spin_kit);

        editor = getSharedPreferences("LoginMessage",MODE_PRIVATE).edit();
        pref = getSharedPreferences("LoginMessage",MODE_PRIVATE);
        if(pref.getBoolean("isLunched",false))
        {
            username = pref.getString("UserName","");
            DESPassword = pref.getString("PassWord","");
            if(!DESPassword.equals(""))password=DESUtil.des(DESPassword,APPCONST.DESKey,Cipher.DECRYPT_MODE);
                else password = DESPassword;
            view_username.setText(username);
            view_password.setText(password);
            view_rememberpassword.setChecked(true);
        }
        Log.i(TAG,"bind view successfuly");
        view_login.setOnClickListener(this);
        view_rememberpassword.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //取得用户名和密码
            case R.id.button_login:
                Log.i(TAG,"button click");
                spinKitView.setVisibility(View.VISIBLE);
                username = view_username.getText().toString();
                password = view_password.getText().toString();
                Log.i(TAG,"get Text successfuly");
                try {
                    DESPassword=DESUtil.des(password,APPCONST.DESKey,Cipher.ENCRYPT_MODE);
                    login();
                }catch (Exception e){e.printStackTrace();}
                break;
            case R.id.register:
                startActivity(new Intent(this,ActivityRegister.class));
                break;
        }
    }

    /**
     * 登录功能
     * POST
     * okHTTP
     */
    private void login()throws Exception{
        //实例化okHttp
        OkHttpClient client = new OkHttpClient();
        //okHttp参数
        RequestBody requestBody = new MultipartBody.Builder()
                .setType((MultipartBody.FORM))
                .addFormDataPart("username",username)
                .addFormDataPart("password",DESPassword)
                .build();
        final Request request = new Request.Builder()
                .url(getString(R.string.https_host)+"/php/login.php")
                .post(requestBody)
                .build();
        Call call=client.newCall(request);
        Log.i(TAG,call.toString());
        //异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = Message.obtain();
                message.arg1=Login_wrong;
                handler.sendMessage(message);
                Log.i(TAG,request.toString());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                result=response.body().string();
                Message message = Message.obtain();
                Log.i(TAG,result);
                try {
                    if(result.equals("success"))
                    {
                        Log.i(TAG,"login successfully");
                        //检查登录状态
                        if(view_rememberpassword.isChecked()){
                            editor.putString("UserName",username);
                            editor.putString("PassWord",DESPassword);
                            editor.putBoolean("isLunched",true);
                        }
                        else editor.putString("UserName",username);
                        //提交保存到本地
                        editor.apply();
                        Log.i(TAG,"login successfully");
                        message.arg1=Login_success;
                        Thread.sleep(2800);
                    }
                    else if(result.equals("wrong"))
                    {
                        message.arg1=Login_failed;
                        Thread.sleep(1800);
                        Log.i(TAG,"login fail"+result);
                    }
                    else {
                        message.arg1=Login_wrong;
                        message.obj=result;
                        Thread.sleep(1500);
                        Log.i(TAG,"login fail"+result);
                    }
                }catch (InterruptedException e){e.printStackTrace();}
                handler.sendMessage(message);
            }
        });
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }
}
