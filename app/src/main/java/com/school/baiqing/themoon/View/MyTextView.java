package com.school.baiqing.themoon.View;


import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by zhao on 2017/8/19.
 */

public class MyTextView extends /*AppCompatEditText*/ AppCompatTextView {

   /* private OnTouchListener mOnTouchListener;
    private long timeDown;
    private long timeUp;*/


    public MyTextView(Context context) {
        super(context);

    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }


   /* @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        if (timeUp - timeDown > 0 && timeUp - timeDown < 1000){
            return false;
        }else {
            return super.dispatchTouchEvent(event);
        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            timeDown = System.currentTimeMillis();
        }else if (event.getAction() == MotionEvent.ACTION_UP){
            timeUp = System.currentTimeMillis();
        }
        return super.onTouchEvent(event);
    }*/




}

