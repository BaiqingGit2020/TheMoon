package com.school.baiqing.themoon;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fbreader.common.FBReaderHelper;

import org.geometerplus.android.fbreader.FBReader;
import org.geometerplus.fbreader.book.Book;

import java.io.File;

public class LibraryFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "themoon library";
    private String context;
    private LinearLayout addlocalbook;
    private FBReaderHelper fbReaderHelper;
    private ActivityMain mActivityMain;

    private String path3 = Environment.getExternalStorageDirectory() + "/Themoon/test.epub";

    private static final String BUNDLE_CONTEXT= "context";

    public static LibraryFragment newInstance(String context){
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_CONTEXT,context);

        LibraryFragment Libraryfragment = new LibraryFragment();
        Libraryfragment.setArguments(bundle);
        return Libraryfragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_library,container,false);
        Bundle bundle=getArguments();
        mActivityMain = (ActivityMain)getActivity();
        addlocalbook = view.findViewById(R.id.add_local_book);
        addlocalbook.setOnClickListener(this);

        fbReaderHelper = mActivityMain.getFbReaderHelper();
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_local_book:
                //必须确保activity有绑定服务才能通过jni获取书本信息
                fbReaderHelper.bindToService(new Runnable() {
                    @Override
                    public void run() {
                        Book book = fbReaderHelper.getCollection().getBookByFile(path3);
                        Log.i("test",path3);
                        FBReader.openBook(mActivityMain, book, null);
                    }
                });
                break;
        }
    }

}
