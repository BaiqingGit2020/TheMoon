package com.school.baiqing.themoon.View.Library;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.school.baiqing.themoon.ActivityMain;
import com.school.baiqing.themoon.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class LibraryFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "themoon library";
    private static final String BUNDLE_CONTEXT= "context";

    private Unbinder unbinder;
    private ActivityMain mActivityMain;
    @BindView(R.id.MessageTablayout)
    public TabLayout tabLayout;
    @BindView(R.id.MessagViewpager)
    public ViewPager viewPager;
    //存放标签页标题
    private ArrayList<String> tab_title_list = new ArrayList<>();
    //存放ViewPager下的Fragment
    private ArrayList<Fragment> fragment_list = new ArrayList<>();
    private LibraryFragmentRecommend fragmentRecommend;
    private LibraryFragmentCollect fragmentCollect;
    //适配器
    private LibraryFragmentPagerAdapter adapter;

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
        unbinder = ButterKnife.bind(this,view);
        mActivityMain = (ActivityMain)getActivity();
        initview(view);
        return view;
    }

    @Override
    public void onClick(View v) {

    }
    private void initview(View view){

        tab_title_list.add("推荐");
        tab_title_list.add("所有");

        tabLayout.addTab(tabLayout.newTab().setText(tab_title_list.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(tab_title_list.get(1)));
        fragmentRecommend = LibraryFragmentRecommend.newInstance("yes","no");
        fragmentCollect = LibraryFragmentCollect.newInstance("yes","no");
        fragment_list.add(fragmentRecommend);
        fragment_list.add(fragmentCollect);

        adapter = new LibraryFragmentPagerAdapter(getActivity().getSupportFragmentManager(), tab_title_list, fragment_list);
        viewPager.setAdapter(adapter);//给ViewPager设置适配器
        tabLayout.setupWithViewPager(viewPager);//将TabLayout与Viewpager联动起来
        tabLayout.setTabsFromPagerAdapter(adapter);//给TabLayout设置适配器

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //选中了tab的逻辑
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //未选中tab的逻辑
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //再次选中tab的逻辑
            }

        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
