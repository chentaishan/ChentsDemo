package com.example.servicedemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mPlayClick;
    private Button mStopClick;
    private TextView mResult;
    private MyServiceConnection mConnection;

    MyService service1;


    private static final String TAG = "MainActivity";

    class MyServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {


            Log.d(TAG, "onServiceConnected: ");

            MyService.MyBinder myBinder = (MyService.MyBinder) service;
            service1 = myBinder.getService();



            service1.start();


        }

        @Override
        public void onServiceDisconnected(ComponentName name) {


            Log.d(TAG, "onServiceDisconnected: ");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mPlayClick = (Button) findViewById(R.id.play_click);
        mStopClick = (Button) findViewById(R.id.stop_click);
        mResult = (TextView) findViewById(R.id.result);

        mPlayClick.setOnClickListener(this);
        mStopClick.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.play_click:

                Intent intent = new Intent(this, MyService.class);

                intent.putExtra("data", 10);
                mConnection = new MyServiceConnection();
                //参数 intent：启动服务的意图
                //参数 conn:ServiceConnection的实现类，在该接口中接收服务返回的IBinder 对象
                //参数 BIND_AUTO_CREATE：当服务存在时则直接绑定，当服务不存在时则创建服务并绑定
                bindService(intent, mConnection, BIND_AUTO_CREATE);


//                startService(new Intent(MainActivity.this,MyService.class));

                break;
            case R.id.stop_click:
//                stopService(new Intent(MainActivity.this,MyService.class));


                unbindService(mConnection);

                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConnection);


    }
}
