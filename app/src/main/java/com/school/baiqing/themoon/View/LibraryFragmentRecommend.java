package com.school.baiqing.themoon.View;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;
import com.school.baiqing.themoon.GreenDao.entity.Book;
import com.school.baiqing.themoon.R;
import com.school.baiqing.themoon.Util.DaoCaoRenReadUtil;
import com.school.baiqing.themoon.View.SwipeCard.ItemRemovedListener;
import com.school.baiqing.themoon.View.SwipeCard.RecommendAdapter;
import com.school.baiqing.themoon.View.SwipeCard.SwipeCardLayoutManager;
import com.school.baiqing.themoon.View.SwipeCard.SwipeCardRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class LibraryFragmentRecommend extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public Unbinder unbinder;
    @BindView(R.id.spin_kit_recom_card)
    SpinKitView spinKitView;
    @BindView(R.id.SwipCardView)
    SwipeCardRecyclerView swipeCardRecyclerView;
    RecommendAdapter recommendAdapter;
    private  List<Book> booklist = new ArrayList<>();

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    initAdapter();
                    break;
                case 2:
                    //TODO no net
                    break;
            }
        }
    };

    public LibraryFragmentRecommend() {
        // Required empty public constructor
    }
    public static LibraryFragmentRecommend newInstance(String param1, String param2) {
        LibraryFragmentRecommend fragment = new LibraryFragmentRecommend();
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
        View view = inflater.inflate(R.layout.fragment_library_fragment_recommend, container, false);
        unbinder = ButterKnife.bind(this,view);
        swipeCardRecyclerView.setLayoutManager(new SwipeCardLayoutManager());
        spinKitView.setVisibility(View.VISIBLE);
        initData();
        return view;

    }
    public void initAdapter(){
        for(int i = 0;i < 3;i++)
            booklist.addAll(booklist);
        recommendAdapter = new RecommendAdapter(getActivity(),booklist);
        swipeCardRecyclerView.setAdapter(recommendAdapter);
        spinKitView.setVisibility(View.GONE);
        swipeCardRecyclerView.setRemovedListener(new ItemRemovedListener() {
            @Override
            public void onRightRemoved() {
//                Toast.makeText(getActivity(), list.get(list.size() - 1) + " was right removed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLeftRemoved() {
//                Toast.makeText(getActivity(), list.get(list.size() - 1) + " was left removed", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
    public void initData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                booklist = DaoCaoRenReadUtil.getRecommend();
                if(!booklist.isEmpty()){
                    Message msg = handler.obtainMessage(1);
                    handler.sendMessage(msg);
                }else {
                    Message msg = handler.obtainMessage(2);
                    handler.sendMessage(msg);
                }


            }
        }).start();
//        Log.d("themoonRecomm",booklist.toString());
    }
}
