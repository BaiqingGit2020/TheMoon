package com.school.baiqing.themoon.View.SearchBook;


import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.school.baiqing.themoon.BaseActivity;
import com.school.baiqing.themoon.R;
import com.school.baiqing.themoon.View.SearchBook.SearchBookPrensenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.gujun.android.taggroup.TagGroup;

public class SearchBookActivity extends BaseActivity {

    @BindView(R.id.ll_title_back)
    LinearLayout llTitleBack;
    @BindView(R.id.tv_title_text)
    TextView tvTitleText;
    @BindView(R.id.system_title)
    LinearLayout systemTitle;
    @BindView(R.id.et_search_key)
    EditText etSearchKey;
    @BindView(R.id.tv_search_conform)
    TextView tvSearchConform;

    @BindView(R.id.ll_refresh_suggest_books)
    LinearLayout llRefreshSuggestBooks;
    @BindView(R.id.lv_search_books_list)
    ListView lvSearchBooksList;
    @BindView(R.id.ll_suggest_books_view)
    LinearLayout llSuggestBooksView;
    @BindView(R.id.pb_loading)
    ProgressBar pbLoading;
    @BindView(R.id.lv_history_list)
    ListView lvHistoryList;
    @BindView(R.id.ll_clear_history)
    LinearLayout llClearHistory;
    @BindView(R.id.ll_history_view)
    LinearLayout llHistoryView;
    @BindView(R.id.tg_suggest_book)
    TagGroup tgSuggestBook;
    @BindView(R.id.ll_search_err)
    LinearLayout llSearchErr;
    @BindView(R.id.tv_search_err_massage)
    TextView tvSearchErrMsg;

    private SearchBookPrensenter mSearchBookPrensenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_book);
        ButterKnife.bind(this);
        setStatusBar(R.color.sys_line);
        mSearchBookPrensenter = new SearchBookPrensenter(this);
        mSearchBookPrensenter.start();
    }

    @Override
    public void onBackPressed() {
       if (!mSearchBookPrensenter.onBackPressed()){
           super.onBackPressed();
       }
    }

    public LinearLayout getLlTitleBack() {
        return llTitleBack;
    }

    public TextView getTvTitleText() {
        return tvTitleText;
    }

    public LinearLayout getSystemTitle() {
        return systemTitle;
    }

    public EditText getEtSearchKey() {
        return etSearchKey;
    }

    public TextView getTvSearchConform() {
        return tvSearchConform;
    }

    public TagGroup getTgSuggestBook() {
        return tgSuggestBook;
    }

    public LinearLayout getLlRefreshSuggestBooks() {
        return llRefreshSuggestBooks;
    }

    public ListView getLvSearchBooksList() {
        return lvSearchBooksList;
    }

    public LinearLayout getLlSuggestBooksView() {
        return llSuggestBooksView;
    }

    public ProgressBar getPbLoading() {
        return pbLoading;
    }

    public ListView getLvHistoryList() {
        return lvHistoryList;
    }

    public LinearLayout getLlClearHistory() {
        return llClearHistory;
    }

    public LinearLayout getLlHistoryView() {
        return llHistoryView;
    }

    public LinearLayout getLlSearchErr() {
        return llSearchErr;
    }

    public TextView getTvSearchErrMsg() {
        return tvSearchErrMsg;
    }
}
