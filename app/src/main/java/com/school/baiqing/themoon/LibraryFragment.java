package com.school.baiqing.themoon;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.school.baiqing.themoon.Util.FileUtil;

import java.io.File;

public class LibraryFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "themoon library";
    private String context;
    private LinearLayout addlocalbook;


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

        addlocalbook = view.findViewById(R.id.add_local_book);
        addlocalbook.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_local_book:
                // 导入本机小说
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");      // 最近文件（任意类型）
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, 1);
                break;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) { // 选择了才继续
            Uri uri = data.getData();
            String filePath = FileUtil.uri2FilePath(getActivity(), uri);
            File file = new File(filePath);
            String fileName = file.getName();
            Log.d(TAG, "onActivityResult: fileLen = " + file.length());
            String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);  // 后缀名
            if (suffix.equals("txt")) {
//                if (mDbManager.isExistInBookshelfNovel(filePath)) {
//                    showShortToast("该小说已导入");
//                    return;
//                }
                if (FileUtil.getFileSize(file) > 100) {
                    Toast.makeText(getActivity(),"文件过大",Toast.LENGTH_SHORT).show();
                    return;
                }
                // TODO: 2020/3/4
                // 将该小说的数据存入数据库

                // 更新列表
            }
            else if (suffix.equals("epub")) {
//                if (mDbManager.isExistInBookshelfNovel(filePath)) {
//                    showShortToast("该小说已导入");
//                    return;
//                }
                if (FileUtil.getFileSize(file) > 100) {
                    Toast.makeText(getActivity(),"文件过大",Toast.LENGTH_SHORT).show();
                    return;
                }
                // 在子线程中解压该 epub 文件
//                mLoadingRv.setVisibility(View.VISIBLE);
//                mPresenter.unZipEpub(filePath);
            }
            else {
                Toast.makeText(getActivity(),"不支持该类型",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
