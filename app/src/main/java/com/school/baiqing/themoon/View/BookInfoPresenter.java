package com.school.baiqing.themoon.View;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.RequestOptions;
import com.school.baiqing.themoon.BookInfoActivity;
import com.school.baiqing.themoon.GreenDao.entity.Book;
import com.school.baiqing.themoon.GreenDao.entity.Chapter;
import com.school.baiqing.themoon.GreenDao.service.BookService;
import com.school.baiqing.themoon.GreenDao.service.ChapterService;
import com.school.baiqing.themoon.R;
import com.school.baiqing.themoon.ReadActivity;
import com.school.baiqing.themoon.Util.APPCONST;
import com.school.baiqing.themoon.Util.BlurUtil;
import com.school.baiqing.themoon.Util.StringHelper;
import com.school.baiqing.themoon.Util.TextHelper;
import com.school.baiqing.themoon.Web.CommonApi;
import com.school.baiqing.themoon.Web.ResultCallback;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.LogRecord;

/**
 * Created by zhao on 2017/7/27.
 */

public class BookInfoPresenter implements BasePresenter {

    private BookInfoActivity mBookInfoActivity;
    private Book mBook;
    private List<Chapter> mChapters = new ArrayList<>();
    private BookService mBookService;
    private ChapterService mChapterService;
    private BookInfoChapterTitleAdapter mChapterTitleAdapter;

    public BookInfoPresenter(BookInfoActivity bookInfoActivity){
        mBookInfoActivity  = bookInfoActivity;
        mBookService = new BookService();
        mChapterService = new ChapterService();
    }
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    initAdapter();
                    mBookInfoActivity.getBookinfoLoading().setVisibility(View.GONE);
                    break;
            }
        }
    };

    @Override
    public void start() {
        mBook = (Book) mBookInfoActivity.getIntent().getSerializableExtra(APPCONST.BOOK);
        init();
        getData();
    }

    private void init(){
        mBookInfoActivity.getTvBookAuthor().setText(mBook.getAuthor());
        mBookInfoActivity.getTvBookDesc().setText(mBook.getDesc());
        mBookInfoActivity.getTvBookType().setText(mBook.getType());
        mBookInfoActivity.getTvBookName().setText(mBook.getName());
        if (isBookCollected()){
            mBookInfoActivity.getBtnAddBookcase().setText("不追了");
        }else {
            mBookInfoActivity.getBtnAddBookcase().setText("加入书架");
        }
        mBookInfoActivity.getLlTitleBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBookInfoActivity.finish();
            }
        });
        mBookInfoActivity.getBtnAddBookcase().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (StringHelper.isEmpty(mBook.getId())){
                    mBookService.addBook(mBook);
                    TextHelper.showText("成功加入书架");
                    mBookInfoActivity.getBtnAddBookcase().setText("不追了");
                }else {
                    mBookService.deleteBookById(mBook.getId());
                    TextHelper.showText("成功移除书籍");
                    mBookInfoActivity.getBtnAddBookcase().setText("加入书架");
                }

            }
        });
        mBookInfoActivity.getBtnReadBook().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mBookInfoActivity, ReadActivity.class);
                intent.putExtra(APPCONST.BOOK,mBook);
                mBookInfoActivity.startActivity(intent);

            }
        });
        Glide.with(mBookInfoActivity)
                .load(mBook.getImgUrl())
                .into(mBookInfoActivity.getIvBookImg());
        Glide.with(mBookInfoActivity)
                .load(mBook.getImgUrl())
                .apply(new RequestOptions()
                        .placeholder(R.mipmap.cover_place_holder)
                        .error(R.mipmap.cover_error)
                        .transform(new BitmapTransformation() {
                            @Override
                            protected Bitmap transform(@NonNull BitmapPool pool,
                                                       @NonNull Bitmap toTransform, int outWidth, int outHeight) {
                                // 对得到的 Bitmap 进行虚化处理
                                return BlurUtil.blurBitmap(mBookInfoActivity,
                                        toTransform, 5, 8);
                            }

                            @Override
                            public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

                            }
                        }))
                .into(mBookInfoActivity.getImCoverBg());
    }

    private boolean isBookCollected(){
        Book book = mBookService.findBookByAuthorAndName(mBook.getName(),mBook.getAuthor());
        if (book == null){
            return false;
        }else {
            mBook.setId(book.getId());
            return true;
        }
    }
    /**
     * 章节数据网络同步
     */
    private void getData() {

        CommonApi.getBookChapters(mBook.getChapterUrl(), new ResultCallback() {
            @Override
            public void onFinish(Object o, int code) {
                mChapters = (List<Chapter>) o;
                mBook.setChapterTotalNum(mChapters.size());
                if (!StringHelper.isEmpty(mBook.getId())) {
                    mHandler.sendMessage(mHandler.obtainMessage(1));
                }
                if (mChapters.size() == 0) {
                    TextHelper.showLongText("该书查询不到任何章节");
                }
                Log.e("themoonHTTP",mBook.getChapterUrl());
                Log.e("themoonHTTP",mChapters.get(0).getTitle());
                mHandler.sendMessage(mHandler.obtainMessage(1));
            }
            @Override
            public void onError(Exception e) {
                mChapters = (List<Chapter>) mChapterService.findBookAllChapterByBookId(mBook.getId());
            }
        });
    }

    public void initAdapter(){
        mChapterTitleAdapter = new BookInfoChapterTitleAdapter(mBookInfoActivity, mChapters);
        mBookInfoActivity.getLvBookInfoChapterList().setAdapter(mChapterTitleAdapter);
    }
}
