package com.example.servicedemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyBindService extends Service {

    boolean isRunning;
    MyBinder myBinder = new MyBinder();
    private MyThread myThread;

    public  void start() {

        if (isRunning){
            return;
        }
        isRunning= true;

        myThread = new MyThread(index);
        new Thread(myThread).start();


        myThread.setiUpdateProgress(new MyThread.IUpdateProgress() {
            @Override
            public void updateUI(int progress) {

                Log.d(TAG, "updateUI: "+progress);

                if (progress==0){

                   stopSelf();
                }
            }
        });

        Log.d(TAG, "start: ");

    }

    public class MyBinder extends Binder {

        public void call(int index) {

            updateData(index);

        }

        public MyBindService getService() {

            return MyBindService.this;
        }

    }

    int index;

    public void updateData(int index) {


        Log.d(TAG, "updateData: hello world!!!!" + index);

    }

    private static final String TAG = "MyService";

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");
        index = intent.getIntExtra("data", 0);


        return myBinder;
    }


    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");


        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onCreate() {
        super.onCreate();


        Log.d(TAG, "onCreate: ");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestroy: ");

        myThread.stop();
    }





}
