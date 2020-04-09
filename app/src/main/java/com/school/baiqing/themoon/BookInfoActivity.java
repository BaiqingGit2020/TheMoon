package com.school.baiqing.themoon;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.school.baiqing.themoon.View.BookInfoPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;


public class BookInfoActivity extends BaseActivity {

    @BindView(R.id.ll_title_back)
    ImageView llTitleBack;
    @BindView(R.id.iv_novel_intro_top_image_bg)
    ImageView imCoverBg;
    @BindView(R.id.iv_book_img)
    ImageView ivBookImg;
    @BindView(R.id.tv_book_name)
    TextView tvBookName;
    @BindView(R.id.tv_book_author)
    TextView tvBookAuthor;
    @BindView(R.id.tv_book_type)
    TextView tvBookType;
    @BindView(R.id.tv_book_desc)
    TextView tvBookDesc;
    @BindView(R.id.lv_book_info_chapter_list)
    RecyclerView lvBookInfoChapterList;
    @BindView(R.id.chapter_load)
    SpinKitView bookinfoLoading;
    @BindView(R.id.btn_add_bookcase)
    Button btnAddBookcase;
    @BindView(R.id.btn_read_book)
    Button btnReadBook;

    private BookInfoPresenter mBookInfoPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);
        ButterKnife.bind(this);
        setStatusBar(R.color.sys_line);
        lvBookInfoChapterList.setLayoutManager(new LinearLayoutManager(this));
        mBookInfoPresenter = new BookInfoPresenter(this);
        bookinfoLoading.setVisibility(View.VISIBLE);
        mBookInfoPresenter.start();
    }

    public ImageView getLlTitleBack() {
        return llTitleBack;
    }



    public ImageView getIvBookImg() {
        return ivBookImg;
    }

    public TextView getTvBookName() {
        return tvBookName;
    }

    public TextView getTvBookAuthor() {
        return tvBookAuthor;
    }

    public TextView getTvBookType() {
        return tvBookType;
    }

    public TextView getTvBookDesc() {
        return tvBookDesc;
    }

    public Button getBtnAddBookcase() {
        return btnAddBookcase;
    }

    public Button getBtnReadBook() {
        return btnReadBook;
    }

    public ImageView getImCoverBg() {
        return imCoverBg;
    }

    public RecyclerView getLvBookInfoChapterList() {
        return lvBookInfoChapterList;
    }

    public SpinKitView getBookinfoLoading() {
        return bookinfoLoading;
    }
}
