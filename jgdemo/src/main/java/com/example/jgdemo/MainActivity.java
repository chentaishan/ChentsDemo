package com.example.jgdemo;

import android.Manifest;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

import cn.jpush.android.api.JPushInterface;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity
{

    private int REQUEST_FILE_CODE=99;
    //for receive customer msg from jpush server
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



            JPushInterface.setDebugMode(true);
            JPushInterface.init(getApplicationContext());


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        JPushInterface.stopPush(getApplicationContext());

    }
}
