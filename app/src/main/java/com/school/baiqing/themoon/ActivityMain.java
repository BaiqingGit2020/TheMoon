package com.school.baiqing.themoon;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ActivityMain extends AppCompatActivity implements View.OnClickListener{
    private TextView topBar;
    private TextView tabShelf;
    private TextView tabLibrary;
    private TextView tabUser;

    private FrameLayout ly_content;

    private ShelfFragment shelfFragment ;
    private UserFragment userFragment;
    private LibraryFragment libraryFragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        bindView();

        tabShelf.callOnClick();
    }

    //UI组件初始化与事件绑定
    private void bindView() {
        topBar = (TextView)this.findViewById(R.id.txt_top);
        tabShelf = (TextView)this.findViewById(R.id.txt_shelf);
        tabLibrary = (TextView)this.findViewById(R.id.txt_library);
        tabUser = (TextView)this.findViewById(R.id.txt_user);
        ly_content = (FrameLayout) findViewById(R.id.fragment_container);

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
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
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
                    userFragment = UserFragment.newInstance("第三个Fragment");
                    transaction.add(R.id.fragment_container,userFragment);
                }else{
                    transaction.show(userFragment);
                }
                break;


        }

        transaction.commit();
    }
}

