package com.example.testcontentprovider2;

import android.app.Application;

public class MyApp extends Application {

    public static MyOpenHelper myOpenHelper;
    public static MyApp context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = this;

        initDB();

    }

    private void initDB() {

        myOpenHelper = new MyOpenHelper(this);

    }

    public static MyOpenHelper getMyOpenHelper() {

        return myOpenHelper;
    }
}
