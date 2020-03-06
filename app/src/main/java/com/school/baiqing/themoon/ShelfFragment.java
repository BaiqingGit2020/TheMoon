package com.school.baiqing.themoon;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.school.baiqing.themoon.GreenDao.entity.Book;
import com.school.baiqing.themoon.GreenDao.util.DaoUtils;
import com.school.baiqing.themoon.GreenDao.util.DaoUtilsStore;
import com.school.baiqing.themoon.Service.BookService;
import com.school.baiqing.themoon.Util.VibratorUtil;
import com.school.baiqing.themoon.View.BookcaseDragAdapter;
import com.school.baiqing.themoon.View.DragSortGridView;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class ShelfFragment extends Fragment implements View.OnClickListener{
    private String context;
    private ActivityMain mActivityMain;
    private RefreshLayout refreshLayout;
    private LinearLayout linearLayout;
    private DragSortGridView mDragSortGridView;

    private BookcaseDragAdapter mBookcaseAdapter;
    private List<Book> mBooks = new ArrayList<>();//书目数组
    private Book book_long = new Book(null,"龙王传说","",""," ",
            " ","","","","","","",1,1,1,1,1);
    private Book book_wang = new Book(null,"龙王传说","",""," ",
            " ","","","","","","",1,1,1,1,1);
    private BookService mBookService = new BookService();
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    mBookcaseAdapter.notifyDataSetChanged();
                    refreshLayout.finishRefresh();
                    break;
                case 2:
                    refreshLayout.finishRefresh();
                    break;

            }
        }
    };

    private static final String BUNDLE_CONTEXT= "context";

    public static ShelfFragment newInstance(String context){
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_CONTEXT,context);

        ShelfFragment shelffragment = new ShelfFragment();
        shelffragment.setArguments(bundle);
        return shelffragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shelf,container,false);
        mActivityMain = (ActivityMain)getActivity();
        linearLayout = view.findViewById(R.id.ll_no_data_tips);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        mDragSortGridView = view.findViewById(R.id.gv_book);
        linearLayout.setOnClickListener(this);

        setDragSortGridView();
        setRefreshLayout();
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        getData();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onClick(View v) {
        mActivityMain.getTabLibrary().callOnClick();
    }

    public void setRefreshLayout() {
        refreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });
    }

    public void setDragSortGridView(){

        mDragSortGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (!mBookcaseAdapter.ismEditState()) {
                    refreshLayout.setEnableRefresh(true);
                    mBookcaseAdapter.setmEditState(true);
                    mDragSortGridView.setDragModel(DragSortGridView.DRAG_BY_LONG_CLICK);
                    mBookcaseAdapter.notifyDataSetChanged();
                    mActivityMain.getTopBar().setVisibility(View.GONE);
                    mActivityMain.getEditbookcase().setVisibility(View.VISIBLE);
                    VibratorUtil.Vibrate(getActivity(),100);
//                    mBookcaseFragment.getGvBook().setOnItemClickListener(null);
                }
                return true;
            }
        });
        mActivityMain.getEditFinish().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivityMain.getTopBar().setVisibility(View.VISIBLE);
                mActivityMain.getEditbookcase().setVisibility(View.GONE);
//                mBookcaseFragment.getSrlContent().setEnableRefresh(true);
                mDragSortGridView.setDragModel(DragSortGridView.DRAG_BY_LONG_CLICK);
                mBookcaseAdapter.setmEditState(false);
                mBookcaseAdapter.notifyDataSetChanged();
                if(mBookcaseAdapter.getCount()==0){
                    linearLayout.setVisibility(View.VISIBLE);
                    mDragSortGridView.setVisibility(View.GONE);
                }
            }
        });
    }


    public void getData() {
        init();
        initNoReadNum();
    }
    private void init() {
        initBook();
        if (mBooks == null || mBooks.size() == 0) {
            mDragSortGridView.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
        } else {
            if(mBookcaseAdapter == null) {
                mBookcaseAdapter = new BookcaseDragAdapter(this.getActivity(), R.layout.gridview_book_item, mBooks, false);
                mDragSortGridView.setDragModel(-1);
//                mDragSortGridView.setTouchClashparent(mActivityMain.getVpContent());
       /*     mBookcaseFragment.getGvBook().setDragModel(DragSortGridView.DRAG_BY_LONG_CLICK);
            ((MainActivity) (mBookcaseFragment.getActivity())).setViewPagerScroll(false);*/
                mDragSortGridView.setAdapter(mBookcaseAdapter);
            }else {
                mBookcaseAdapter.notifyDataSetChanged();
            }
            linearLayout.setVisibility(View.GONE);
            mDragSortGridView.setVisibility(View.VISIBLE);
        }
    }
    private void initNoReadNum() {

    }
    private void initBook() {
        mBooks.clear();
        mBooks.addAll(mBookService.getShelfBook());

    }
}


