package com.school.baiqing.themoon.View.SearchBook;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;

import com.school.baiqing.themoon.GreenDao.entity.Book;
import com.school.baiqing.themoon.GreenDao.entity.SearchHistory;
import com.school.baiqing.themoon.GreenDao.service.SearchHistoryService;
import com.school.baiqing.themoon.R;
import com.school.baiqing.themoon.Util.APPCONST;
import com.school.baiqing.themoon.Util.StringHelper;
import com.school.baiqing.themoon.View.BasePresenter;
import com.school.baiqing.themoon.Web.CommonApi;
import com.school.baiqing.themoon.Web.ResultCallback;

import com.school.baiqing.themoon.View.BookInfo.BookInfoActivity;

import java.util.ArrayList;

import me.gujun.android.taggroup.TagGroup;

/**
 * Created by zhao on 2017/7/26.
 */

public class SearchBookPrensenter implements BasePresenter {

    private SearchBookActivity mSearchBookActivity;
    private SearchBookAdapter mSearchBookAdapter;
    private String searchKey ;//搜索关键字
    private ArrayList<Book> mBooks = new ArrayList<>();
    private ArrayList<SearchHistory> mSearchHistories = new ArrayList<>();
    private ArrayList<String> mSuggestions = new ArrayList<>();

    private SearchHistoryService mSearchHistoryService;

    private SearchHistoryAdapter mSearchHistoryAdapter;



    private int inputConfirm = 0;//搜索输入确认
    private int confirmTime = 1000;//搜索输入确认时间（毫秒）

    private static String[] suggestion = {"不朽凡人", "圣墟", "我是至尊" ,"龙王传说", "太古神王", "一念永恒", "雪鹰领主", "大主宰"};


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    search();
                    break;
                case 2:
                    initSearchList();
                    break;
                case 3:
                    mSearchBookActivity.getLvSearchBooksList().setAdapter(null);
                    mSearchBookActivity.getPbLoading().setVisibility(View.GONE);
                    mSearchBookActivity.getLlSearchErr().setVisibility(View.VISIBLE);
                    break;
                case 4:
                    mSearchBookActivity.getLvSearchBooksList().setAdapter(null);
                    mSearchBookActivity.getPbLoading().setVisibility(View.GONE);
                    mSearchBookActivity.getTvSearchErrMsg().setText("当前没有书源呀，期待下一个版本吧");
                    mSearchBookActivity.getLlSearchErr().setVisibility(View.VISIBLE);
                    break;
            }
        }
    };

    public SearchBookPrensenter(SearchBookActivity searchBookActivity) {
        mSearchBookActivity = searchBookActivity;
        mSearchHistoryService = new SearchHistoryService();
        for (int i = 0; i < suggestion.length; i++) {
            mSuggestions.add(suggestion[i]);
        }
    }

    @Override
    public void start() {
        searchKey = mSearchBookActivity.getIntent().getStringExtra(APPCONST.Search);
        mSearchBookActivity.getTvTitleText().setText("搜索");
        mSearchBookActivity.getLlSearchErr().setVisibility(View.GONE);
        mSearchBookActivity.getLlTitleBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSearchBookActivity.finish();
            }
        });

        mSearchBookActivity.getEtSearchKey().addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(final Editable editable) {
                searchKey = editable.toString();
                if (StringHelper.isEmpty(searchKey)) {
                    search();
                }

            }

        });

        mSearchBookActivity.getLvSearchBooksList().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(mSearchBookActivity, BookInfoActivity.class);
                intent.putExtra(APPCONST.BOOK, mBooks.get(i));
                mSearchBookActivity.startActivity(intent);
            }
        });
        mSearchBookActivity.getTvSearchConform().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
            }
        });
        mSearchBookActivity.getTgSuggestBook().setOnTagClickListener(new TagGroup.OnTagClickListener() {
            @Override
            public void onTagClick(String tag) {
                mSearchBookActivity.getEtSearchKey().setText(tag);
                search();
            }
        });
        mSearchBookActivity.getLvHistoryList().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mSearchBookActivity.getEtSearchKey().setText(mSearchHistories.get(position).getContent());
                search();
            }
        });
        mSearchBookActivity.getLlClearHistory().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearchHistoryService.clearHistory();
                initHistoryList();
            }
        });
        initSuggestionBook();
        initHistoryList();
        if(!searchKey.equals(APPCONST.Default)){
            mSearchBookActivity.getEtSearchKey().setText(searchKey);
            search();
        }
    }


    /**
     * 初始化建议书目
     */
    private void initSuggestionBook() {

        mSearchBookActivity.getTgSuggestBook().setTags(suggestion);
    }

    /**
     * 初始化历史列表
     */
    private void initHistoryList() {
        mSearchHistories = mSearchHistoryService.findAllSearchHistory();
        if (mSearchHistories == null || mSearchHistories.size() == 0) {
            mSearchBookActivity.getLlHistoryView().setVisibility(View.GONE);
        } else {
            mSearchHistoryAdapter = new SearchHistoryAdapter(mSearchBookActivity, R.layout.listview_search_history_item, mSearchHistories);
            mSearchBookActivity.getLvHistoryList().setAdapter(mSearchHistoryAdapter);
            mSearchBookActivity.getLlHistoryView().setVisibility(View.VISIBLE);
        }
    }

    /**
     * 初始化搜索列表
     */
    private void initSearchList() {
        mSearchBookAdapter = new SearchBookAdapter(mSearchBookActivity, R.layout.listview_search_book_item, mBooks);
        mSearchBookActivity.getLvSearchBooksList().setAdapter(mSearchBookAdapter);
        mSearchBookActivity.getLvSearchBooksList().setVisibility(View.VISIBLE);
        mSearchBookActivity.getLlSuggestBooksView().setVisibility(View.GONE);
        mSearchBookActivity.getLlHistoryView().setVisibility(View.GONE);
        mSearchBookActivity.getPbLoading().setVisibility(View.GONE);

    }

    /**
     * 获取搜索数据
     */
    private void getData() {
        mBooks.clear();
        CommonApi.search(searchKey, new ResultCallback() {

            @Override
            public void onFinish(Object o, int code) {
                mBooks = (ArrayList<Book>) o;
                if(mBooks.isEmpty())mHandler.sendMessage(mHandler.obtainMessage(4));
                else mHandler.sendMessage(mHandler.obtainMessage(2));
            }

            @Override
            public void onError(Exception e) {
                mHandler.sendMessage(mHandler.obtainMessage(3));
            }
        });

    }

    /**
     * 搜索
     */
    private void search() {
        mSearchBookActivity.getPbLoading().setVisibility(View.VISIBLE);
        mSearchBookActivity.getLlSearchErr().setVisibility(View.GONE);
        if (StringHelper.isEmpty(searchKey)) {
            mSearchBookActivity.getPbLoading().setVisibility(View.GONE);
            mSearchBookActivity.getLvSearchBooksList().setVisibility(View.GONE);
            mSearchBookActivity.getLlSuggestBooksView().setVisibility(View.VISIBLE);
            initHistoryList();
            mSearchBookActivity.getLvSearchBooksList().setAdapter(null);
        } else {
            mSearchBookActivity.getLvSearchBooksList().setVisibility(View.VISIBLE);
            mSearchBookActivity.getLlSuggestBooksView().setVisibility(View.GONE);
            mSearchBookActivity.getLlHistoryView().setVisibility(View.GONE);
            getData();
            mSearchHistoryService.addOrUpadteHistory(searchKey);
        }
    }

    public boolean onBackPressed() {
        if (StringHelper.isEmpty(searchKey)) {
            return false;
        } else {
            mSearchBookActivity.getEtSearchKey().setText("");
            return true;
        }
    }


}

