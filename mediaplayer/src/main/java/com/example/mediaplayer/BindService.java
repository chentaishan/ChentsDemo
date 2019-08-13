package com.example.mediaplayer;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;

public class BindService extends Service {

    MyBinder myBinder = new MyBinder();
    private MediaPlayer mediaPlayer;
    private boolean isPlaying;

    class MyBinder extends Binder{

        public BindService getService(){

            return BindService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return myBinder ;
    }


    @Override
    public void onCreate() {
        super.onCreate();

        initMediaplayer();
    }

    private static final String TAG = "BindService";
    private void initMediaplayer() {

        if (mediaPlayer==null){

            mediaPlayer = new MediaPlayer();
            mediaPlayer.setLooping(true);

            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    isPlaying = false;
                    return false;
                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    isPlaying = false;
                    mp.reset();

                    Log.d(TAG, "onCompletion: ");
                }
            });
        }

    }
    public void play(String path){

        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(path);

            mediaPlayer.prepare();

            mediaPlayer.start();
            isPlaying = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void pause(){
        mediaPlayer.pause();

        isPlaying = false;
    }

    public void previus(String path){

        play(path);
    }

    public void next(String path){

        play(path);
    }

    public int getSize(){

        return mediaPlayer.getDuration();
    }

    public int  getCurrSize(){

        return mediaPlayer.getCurrentPosition();
    }

    public boolean isPlaying(){

        return isPlaying;
    }

    public void seekBar(int seek,String path){

        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(path);

            mediaPlayer.prepare();
            mediaPlayer.seekTo(seek);
            mediaPlayer.start();
            isPlaying = true;
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
