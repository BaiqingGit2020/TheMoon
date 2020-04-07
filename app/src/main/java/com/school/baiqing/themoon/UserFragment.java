package com.school.baiqing.themoon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.content.Context.MODE_PRIVATE;

public class UserFragment extends Fragment implements View.OnClickListener{
    private String context;
    private TextView mTextView,userName;
    private LinearLayout lyLogin;

    private SharedPreferences.Editor editor;
    private SharedPreferences pref;

    private static final String BUNDLE_CONTEXT= "context";

    public static UserFragment newInstance(String context){
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_CONTEXT,context);

        UserFragment userfragment = new UserFragment();
        userfragment.setArguments(bundle);
        return userfragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle=getArguments();
        View view = inflater.inflate(R.layout.fragment_user,container,false);
        mTextView = (TextView)view.findViewById(R.id.sync_state);
        lyLogin = (LinearLayout)view.findViewById(R.id.ly_login);
        userName = (TextView)view.findViewById(R.id.user_name);
        mTextView.setText(bundle.getString(BUNDLE_CONTEXT));
        lyLogin.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ly_login:
                startActivity( new Intent(getActivity(),ActivityLogin.class));
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        pref = getActivity().getSharedPreferences("LoginMessage",MODE_PRIVATE);
        if(pref.getBoolean("isLunched",false)){
            String username = pref.getString("UserName","");
            userName.setText(username);
        }
        else userName.setText("登录");
    }
}


