package com.school.baiqing.themoon;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ShelfFragment extends Fragment {
    private String context;
    private TextView mTextView;


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
        mTextView = (TextView)view.findViewById(R.id.txt_content);
        Bundle bundle=getArguments();
        mTextView.setText(bundle.getString(BUNDLE_CONTEXT));
        return view;
    }
}


