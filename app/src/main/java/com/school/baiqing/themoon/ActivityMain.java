package com.school.baiqing.themoon;

import android.Manifest;
import android.content.pm.PackageManager;
import android.icu.text.SimpleDateFormat;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fbreader.common.FBReaderHelper;
import com.school.baiqing.themoon.Util.APPCONST;

import org.geometerplus.android.fbreader.FBReaderApplication;

import java.sql.Date;

public class ActivityMain extends AppCompatActivity implements View.OnClickListener{
    private static final int REQUEST_CODE_SD = 1;

    private TextView topBar;
    private TextView tabShelf;
    private TextView tabLibrary;
    private TextView tabUser;
    private TextView tv_edit_finish;
    private RelativeLayout editbookcase;

    private FrameLayout ly_content;

    private ShelfFragment shelfFragment ;
    private UserFragment userFragment;
    private LibraryFragment libraryFragment;
    private FragmentTransaction transaction;

    private FBReaderHelper fbReaderHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        bindView();
        fbReaderHelper = new FBReaderHelper(this);

        tabShelf.callOnClick();
    }

    //UI组件初始化与事件绑定
    private void bindView() {
        topBar = (TextView)this.findViewById(R.id.txt_top);
        tabShelf = (TextView)this.findViewById(R.id.txt_shelf);
        tabLibrary = (TextView)this.findViewById(R.id.txt_library);
        tabUser = (TextView)this.findViewById(R.id.txt_user);
        ly_content = (FrameLayout) findViewById(R.id.fragment_container);
        tv_edit_finish = (TextView)findViewById(R.id.tv_edit_finish);
        editbookcase = (RelativeLayout)findViewById(R.id.rl_edit_titile);

        tabShelf.setOnClickListener(this);
        tabLibrary.setOnClickListener(this);
        tabUser.setOnClickListener(this);

    }

    //重置所有文本的选中状态
    public void selected(){
        tabShelf.setSelected(false);
        tabLibrary.setSelected(false);
        tabUser.setSelected(false);
    }

    //隐藏所有Fragment
    public void hideAllFragment(FragmentTransaction transaction){
        if(shelfFragment!=null){
            transaction.hide(shelfFragment);
        }
        if(userFragment!=null){
            transaction.hide(userFragment);
        }
        if(libraryFragment!=null){
            transaction.hide(libraryFragment);
        }
    }

    @Override
    public void onClick(View v) {
        transaction = getSupportFragmentManager().beginTransaction();
        hideAllFragment(transaction);
        switch(v.getId()){
            case R.id.txt_shelf:
                topBar.setText(this.getText(R.string.shelf_fragment));
                selected();
                tabShelf.setSelected(true);
                if(shelfFragment==null){
                    shelfFragment = ShelfFragment.newInstance("第一个Fragment");
                    transaction.add(R.id.fragment_container,shelfFragment);
                }else{
                    transaction.show(shelfFragment);
                }
                break;

            case R.id.txt_library:
                topBar.setText(this.getText(R.string.library_fragment));
                selected();
                tabLibrary.setSelected(true);
                if(libraryFragment==null){
                    libraryFragment = LibraryFragment.newInstance("第二个Fragment");
                    transaction.add(R.id.fragment_container,libraryFragment);
                }else{
                    transaction.show(libraryFragment);
                }
                break;

            case R.id.txt_user:
                topBar.setText(this.getText(R.string.user_fragment));
                selected();
                tabUser.setSelected(true);
                if(userFragment==null){

                    SimpleDateFormat dateFormat = new SimpleDateFormat("MM月dd日-HH时mm分");

                    Date date = new Date(System.currentTimeMillis());
                    userFragment = UserFragment.newInstance(dateFormat.format(date));
                    transaction.add(R.id.fragment_container,userFragment);
                }else{
                    transaction.show(userFragment);
                }
                break;


        }

        transaction.commit();
    }
    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - APPCONST.exitTime > APPCONST.exitConfirmTime) {
            Toast.makeText(this,"再按一次退出",Toast.LENGTH_SHORT).show();
            APPCONST.exitTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
    }

    public ShelfFragment getShelfFragment(){
        return this.shelfFragment;
    }
    public TextView getTopBar(){
        return this.topBar;
    }
    public TextView getEditFinish(){
        return this.tv_edit_finish;
    }
    public RelativeLayout getEditbookcase(){
        return this.editbookcase;
    }
    public TextView getTabLibrary(){
        return this.tabLibrary;
    }
    public FBReaderHelper getFbReaderHelper(){return fbReaderHelper;}
    /**
     * 检查权限
     */
    private void checkPermission() {
        //如果没有WRITE_EXTERNAL_STORAGE权限，则需要动态申请权限
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE_SD);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,@NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_SD:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    break;
                }
                // 用户不同意
                finish();
                break;
            default:
                break;
        }
    }
    @Override
    public void onResume(){
        super.onResume();
        fbReaderHelper.bindToService(null);
        checkPermission();
    }
    @Override
    protected void onPause() {
        //注销fbreader阅读服务绑定，service只允许绑定一个activity，所以为保证下一个activity能使用阅读服务，必须注销
        fbReaderHelper.unBind();
        super.onPause();
    }
}

