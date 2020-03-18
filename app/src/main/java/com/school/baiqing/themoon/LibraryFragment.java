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
import com.school.baiqing.themoon.GreenDao.service.BookService;
import com.school.baiqing.themoon.Util.APPCONST;

import org.geometerplus.android.fbreader.FBReader;
import org.geometerplus.android.fbreader.library.LibraryActivity;
import org.geometerplus.fbreader.book.Book;
import org.geometerplus.fbreader.fbreader.ActionCode;
import org.geometerplus.zlibrary.core.application.ZLApplication;

import java.io.File;

public class LibraryFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "themoon library";
    private String context;
    private LinearLayout addlocalbook;
    private LinearLayout addnetworkbook;
    private FBReaderHelper fbReaderHelper;
    private ActivityMain mActivityMain;
    private com.school.baiqing.themoon.GreenDao.entity.Book tmBook = new com.school.baiqing.themoon.GreenDao.entity.Book();

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
        addnetworkbook = view.findViewById(R.id.add_network_book);
        addlocalbook.setOnClickListener(this);
        addnetworkbook.setOnClickListener(this);
        fbReaderHelper = mActivityMain.getFbReaderHelper();
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_local_book:
                startActivity(new Intent(getActivity(),LibraryActivity.class));
//                //必须确保activity有绑定服务才能通过jni获取书本信息
//                fbReaderHelper.bindToService(new Runnable() {
//                    @Override
//                    public void run() {
//                        Book book = fbReaderHelper.getCollection().getBookByFile(path3);
//                        try {
//                            Log.i("TheMoonLibrary",path3);
//                            FBReader.openBook(mActivityMain, book, null);
//                        }
//                        catch (Exception e){e.printStackTrace();}
//                        finally {
//
//                            BookService mBookService = new BookService();
//                            if(!mBookService.isExistByLocalPath(path3)){
//                                tmBook.setName(book.getTitle());
//                                Log.i("TheMoonLibrary","book is new");
//                                tmBook.setLocation(APPCONST.BookLocation_local);
//                                tmBook.setChapterUrl(path3);
//                                mBookService.addBook(tmBook);
//                            }
//                        }
//                    }
//                });
                break;
            case R.id.add_network_book:
                Intent intent = new Intent(mActivityMain, SearchBookActivity.class);
                mActivityMain.startActivity(intent);
                break;
        }
    }

}
