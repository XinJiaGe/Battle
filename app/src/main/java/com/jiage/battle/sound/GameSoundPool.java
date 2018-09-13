package com.jiage.battle.sound;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.jiage.battle.R;
import com.jiage.battle.util.SDTimerUtil;

/**
 * 作者：李忻佳
 * 日期：2017/12/28/028.
 * 说明：
 */

public class GameSoundPool {
    public static GameSoundPool INSTANCE;
    public static int ENEMYCLEARA = 1;
    public static int ENEMYCLEARB = 2;
    public static int ENEMYCLEARC = 3;
    public static int ENEMYCLEARD = 4;
    public static int ENEMYCLEARE = 5;
    private SoundPool mSoundPool;
    private Context mContext;
    private boolean isSleep = false;

    public static GameSoundPool getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new GameSoundPool(context);
        }
        return INSTANCE;
    }

    private GameSoundPool(Context context) {
        this.mContext = context;
        mSoundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 100);
        ENEMYCLEARA = mSoundPool.load(context, R.raw.beep, 0);//打方块子弹击中方块
    }

    /**
     * @param soundId 播发音乐的编号ID map的key
     */
    public int play(int soundId) {
        return mSoundPool.play(soundId, 1, 1, 0, 0, 1);
    }
    /**
     * @param soundId 无限播发音乐的编号ID map的key
     */
    public int playInfinite(int soundId) {
        return mSoundPool.play(soundId, 1, 1, 0, -1, 1);
    }
    /**
     * 播放后延迟几秒在播放
     * @param soundId 播发音乐的编号ID map的key
     */
    public void playDelay(int soundId) {
        if(!isSleep){
            mSoundPool.play(soundId, 1, 1, 0, 0, 1);
            isSleep = true;
            SDTimerUtil timerUtil = new SDTimerUtil();
            timerUtil.startWork(1000, new SDTimerUtil.SDTimerListener() {
                @Override
                public void onWork() {

                }

                @Override
                public void onWorkMain() {
                    isSleep = false;
                }
            });
        }
    }

    public void stop(int soundId){
        mSoundPool.stop(soundId);
    }
}
