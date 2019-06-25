package com.example.day1.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;

import com.example.day1.R;

public class MyTabLayout extends TabLayout {
    public MyTabLayout(Context context) {
        super(context);
    }

    public MyTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        context.obtainStyledAttributes(R.styleable.TabLayout,defStyleAttr);

          context.obtainStyledAttributes(attrs,
                R.styleable.TabLayout, defStyleAttr, R.style.AppTheme);

    }
}
