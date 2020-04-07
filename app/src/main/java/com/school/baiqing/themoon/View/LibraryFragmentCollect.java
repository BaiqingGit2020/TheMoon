package com.school.baiqing.themoon.View;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.school.baiqing.themoon.R;
import com.school.baiqing.themoon.SearchBookActivity;
import com.school.baiqing.themoon.Util.APPCONST;
import com.school.baiqing.themoon.Util.DaoCaoRenReadUtil;
import com.school.baiqing.themoon.Util.NetUtil;
import com.school.baiqing.themoon.Util.TianLaiReadUtil;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import org.geometerplus.android.fbreader.library.LibraryActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class LibraryFragmentCollect extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private List<String> mCategoryNameList = new ArrayList<>();
    private List<String> mMoreList = new ArrayList<>();

    private List<DiscoveryNovelData> mNovelDataList = new ArrayList<>();
    private CategoryAdapter mCategoryAdapter;

    private Unbinder unbinder;
    @BindView(R.id.srv_press_refresh)
    public SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.rv_press_category_novel_list)
    public RecyclerView mCategoryNovelRv;
    @BindView(R.id.add_local_book)
    public LinearLayout addlocalbook;
    @BindView(R.id.add_network_book)
    public LinearLayout addnetworkbook;
    public LibraryFragmentCollect() {
        // Required empty public constructor
    }

    public static LibraryFragmentCollect newInstance(String param1, String param2) {
        LibraryFragmentCollect fragment = new LibraryFragmentCollect();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_library_fragment_collect, container, false);
        unbinder = ButterKnife.bind(this,view);
        initData();
        mCategoryNovelRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        setOnClickLisener();
        return view;
    }

    public void setOnClickLisener(){
        addlocalbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),LibraryActivity.class));
            }
        });
        addnetworkbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),SearchBookActivity.class));
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
    protected void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mNovelDataList = TianLaiReadUtil.getCategory();
                for (int i=0;i<mNovelDataList.size();i++){
                    String t = mNovelDataList.get(i).getListname();
                    mCategoryNameList.add(t);
                    mMoreList.add("更多"+t);
                }
                if(!mNovelDataList.isEmpty()){
                    Message msg = mhandler.obtainMessage(1);
                    mhandler.sendMessage(msg);
                }
            }
        }).start();
    }
    Handler mhandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    initCategoryAdapter();
                    break;
                case 2:
                    break;
            }
        }
    };

    private void initCategoryAdapter() {
        mCategoryAdapter = new CategoryAdapter(getActivity(),
                mCategoryNameList, mMoreList, mNovelDataList,
                new CategoryAdapter.CategoryListener() {
                    @Override
                    public void clickNovel(String novelName) {

                        if (!NetUtil.hasInternet(getActivity())) {
                            return;
                        }
                        // 跳转到该小说的搜索结果页
//                        Intent intent = new Intent(getActivity(), SearchActivity.class);
//                        intent.putExtra(SearchActivity.KEY_NOVEL_NAME, novelName);
//                        startActivity(intent);
                    }

                    @Override
                    public void clickMore(int position) {
                        if (!NetUtil.hasInternet(getActivity())) {
                            return;
                        }
                        int gender = 2;
                        String major;
                        switch (position) {
                            case 0:
                                major = APPCONST.CATEGORY_MAJOR_CBXS;
                                break;
                            case 1:
                                major = APPCONST.CATEGORY_MAJOR_QCYQ;
                                break;
                            case 2:
                                major = APPCONST.CATEGORY_MAJOR_ZJMZ;
                                break;
                            default:
                                major = APPCONST.CATEGORY_MAJOR_RWSK;
                                break;
                        }
                        // 跳转到全部小说页面
//                        Intent intent = new Intent(getActivity(), AllNovelActivity.class);
//                        intent.putExtra(AllNovelActivity.KEY_GENDER, gender);   // 性别
//                        intent.putExtra(AllNovelActivity.KEY_MAJOR, major);     // 一级分类
//                        startActivity(intent);
                    }
                });
        mCategoryNovelRv.setAdapter(mCategoryAdapter);
    }

}
