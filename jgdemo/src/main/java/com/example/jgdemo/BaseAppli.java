package com.example.jgdemo;

import android.app.Application;

import cn.jpush.android.api.JPushInterface;

public class BaseAppli extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

//        JPushInterface.setDebugMode(true);
//        JPushInterface.init(this);
    }
}
