package com.example.mediaplayer;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks {

    private static final int REQUEST_FILE_CODE = 89;
    private Button mPlay;
    private static final String TAG = "MainActivity";
    private RecyclerView mRecyclerview;
    private MyAdapter myAdapter;
    private Button mPrevious;
    private Button mNext;


    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            BindService.MyBinder myBinder = (BindService.MyBinder) service;
            bindService = myBinder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    private BindService bindService;
    private SeekBar mSeekbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        String[] permissions = {READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE};
        // 判断有没有这些权限
        if (EasyPermissions.hasPermissions(this, permissions)) {
            initView();
            initService();
            initData();

        } else {
            EasyPermissions.requestPermissions(
                    new PermissionRequest.Builder(this, REQUEST_FILE_CODE, permissions)
                            .setRationale("请确认相关权限！！")
                            .setPositiveButtonText("ok")
                            .setNegativeButtonText("cancal")
//                            .setTheme(R.style.my_fancy_style)
                            .build());
        }


    }

    private void initService() {

        bindService(new Intent(this, BindService.class), serviceConnection, BIND_AUTO_CREATE);
    }

    private void initData() {

        List<String> resultList = new ArrayList<>();
        final Cursor query = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);

        while (query.moveToNext()) {

            String path = query.getString(query.getColumnIndex(MediaStore.Audio.Media.DATA));
            resultList.add(path);

        }

        myAdapter.initData(resultList);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    private void initView() {
        mPlay = (Button) findViewById(R.id.play);
        mPlay.setOnClickListener(this);
        mSeekbar = (SeekBar) findViewById(R.id.seekbar);
        mRecyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new MyAdapter(this);
        mRecyclerview.setAdapter(myAdapter);
        mPrevious = (Button) findViewById(R.id.previous);
        mPrevious.setOnClickListener(this);
        mNext = (Button) findViewById(R.id.next);
        mNext.setOnClickListener(this);


        mSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                int progress = seekBar.getProgress();
                Log.d(TAG, "onStopTrackingTouch: "+progress);
                String path = myAdapter.getItemString(currPos);
                bindService.seekBar(progress,path);
            }
        });
    }

    int currPos = 0;
    String path;
    boolean isPlaying = false;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play:
                if (!isPlaying){
                    isPlaying = true;
                    mPlay.setText("暂停");
                    String path = myAdapter.getItemString(currPos);
                    bindService.play(path);
                    updateSeekbar();
                }else{
                    isPlaying = false;
                    mPlay.setText("播放");
                    bindService.pause();

                }

                break;
            case R.id.previous:// TODO 19/08/13

                if (currPos > 0) {
                    currPos--;
                    path = myAdapter.getItemString(currPos);
                    bindService.previus(path);
                    updateSeekbar();
                }

                break;
            case R.id.next:// TODO 19/08/13
                if (currPos >= 0) {
                    currPos++;
                    path = myAdapter.getItemString(currPos);
                    bindService.next(path);
                    updateSeekbar();
                }
                break;
            default:
                break;
        }
    }


    public void updateSeekbar(){

        new Thread(){
            @Override
            public void run() {
                super.run();
                while (bindService.isPlaying()){
                    try {
                        Thread.sleep(1000);
                        int curr = bindService.getCurrSize();
                        int size = bindService.getSize();

                        Log.d(TAG, "run: "+curr+"--size="+size);
                        mSeekbar.setMax(size);
                        mSeekbar.setProgress(curr);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();



    }
    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        initView();

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onRationaleAccepted(int requestCode) {

    }

    @Override
    public void onRationaleDenied(int requestCode) {

    }
}
