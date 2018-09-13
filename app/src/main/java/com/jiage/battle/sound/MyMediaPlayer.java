package com.jiage.battle.sound;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by Administrator on 2018/8/18/018.
 */

public class MyMediaPlayer {
    private static MyMediaPlayer INSTANCE;
    private Context mContext;
    private MediaPlayer mediaPlayer;

    public static MyMediaPlayer getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new MyMediaPlayer(context);
        }
        return INSTANCE;
    }
    public MyMediaPlayer(Context context){
        mContext = context;
        mediaPlayer = new MediaPlayer();
    }

    public void start(int muc){
        //重置mediaplayer
        mediaPlayer.reset();
        //将需要播放的资源与之绑定
        mediaPlayer= MediaPlayer.create(mContext, muc);
        //开始播放
        mediaPlayer.start();
        //是否循环播放
        mediaPlayer.setLooping(true);
    }
    public void stop(){
        mediaPlayer.stop();
    }
}
