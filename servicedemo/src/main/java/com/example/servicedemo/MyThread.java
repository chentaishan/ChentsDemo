package com.example.servicedemo;

import android.util.Log;

public class MyThread implements Runnable {

    int index = 0;

    private static final String TAG = "MyThread";
    private boolean canRun = true;

    public MyThread(int index) {
        this.index = index;
    }

    public void stopThread() {
        this.canRun = false;
    }

    @Override
    public void run() {
        try {
            while (index > 0 && canRun) {
                Thread.sleep(1000);
                index--;
                Log.d(TAG, "run: " + index);
                if (iUpdateProgress != null) {
                    iUpdateProgress.updateUI(index);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private IUpdateProgress iUpdateProgress;

    public void setiUpdateProgress(IUpdateProgress iUpdateProgress) {
        this.iUpdateProgress = iUpdateProgress;
    }

    interface IUpdateProgress {
        void updateUI(int progress);
    }
}
