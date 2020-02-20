package com.school.baiqing.themoon;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class LibraryFragment extends Fragment implements View.OnClickListener {
    private String context;
    private TextView mTextView;


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
        mTextView = (TextView)view.findViewById(R.id.txt_content);
        Bundle bundle=getArguments();
        mTextView.setText(bundle.getString(BUNDLE_CONTEXT));
        return view;
    }

    @Override
    public void onClick(View v) {

    }
}
