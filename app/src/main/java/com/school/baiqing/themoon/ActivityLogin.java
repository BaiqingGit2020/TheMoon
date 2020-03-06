package com.school.baiqing.themoon;

import com.school.baiqing.themoon.Entity.User;
import com.school.baiqing.themoon.Util.OkHttpUtil;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class ActivityLogin extends AppCompatActivity implements View.OnClickListener {
    private TextView register;

    private String username,password,result;
    private User person = new User();
    private TextView view_username,view_password;
    private TextView view_login,view_signup;
    private SharedPreferences.Editor editor;
    private SharedPreferences pref;
    private static final String TAG = "LoginHttp";
    private static final String LOGIN_SUCCESS = "LoginSuccess";
    private static final String LOGIN_FAIL= "LoginFail";
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.obj=="success"){
                Toast.makeText(ActivityLogin.this,"loginsuccess",Toast.LENGTH_LONG);
            }
            else if(msg.obj=="no User"){

            }
            else {

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

        editor = getSharedPreferences("LoginMessage",MODE_PRIVATE).edit();
        pref = getSharedPreferences("LoginMessage",MODE_PRIVATE);
        if(pref.getBoolean("isLunched",false))
        {
            username = pref.getString("UserName","123");
            password = pref.getString("PassWord","123");
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
                username = view_username.getText().toString();
                password = view_password.getText().toString();
                Log.i(TAG,"get Text successfuly");
                try {
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
                .addFormDataPart("password",password)
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
                Log.i(TAG,request.toString());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                result=response.body().string();
                Log.i(TAG,result);
                if(result.equals("success"))
                {
                    Log.i(TAG,"login successfully");
                    //检查登录状态
                    if(view_rememberpassword.isChecked()){
                        editor.putString("UserName",username);
                        editor.putString("PassWord",password);
                        editor.putBoolean("isLunched",true);
                    }
                    else editor.putString("UserName",username);
                    //提交保存到本地
                    editor.apply();
                    Log.i(TAG,"login successfully");
                    Intent intent = new Intent(ActivityLogin.this,ActivityMain.class);
                    startActivity(intent);
                    ActivityLogin.this.finish();
                }
                else if(result.equals("wrong"))
                {
                    Looper.prepare();
                    Toast.makeText(ActivityLogin.this,"用户名或密码错误",Toast.LENGTH_LONG).show();
                    Log.i(TAG,"login fail"+result);
                    Looper.loop();
                }
            }
        });
    }
}
