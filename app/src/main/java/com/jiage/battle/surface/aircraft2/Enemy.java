package com.jiage.battle.surface.aircraft2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.jiage.battle.R;
import com.jiage.battle.constant.ApkConstant;
import com.jiage.battle.util.BitmapUtils;
import com.jiage.battle.util.OtherUtil;
import com.jiage.battle.util.SDTimerUtil;

import java.util.Vector;

/**
 * 作者：李忻佳
 * 日期：2018/11/26
 * 说明：敌人
 */

public class Enemy {
    private SDTimerUtil durationTimerUtil = new SDTimerUtil();
    private Rect rect = new Rect(0,0,0,0);
    private Bitmap bitmap;
    private Enemy.ENEMYTYPE enemytype = ENEMYTYPE.ENEMY1;
    private int x,y,mScreenW,mScreenH;
    private int speed = 5; //速度
    private int fraction; //分数
    private int blood ; //血量
    private int interval = 10;//子弹间隔
    private boolean leftShift = true;
    private boolean isBoos = false;//是否是boos
    private boolean isBoosAttack = false;//boos是否正在攻击
    private boolean isDead = false;
    private BOOSMODE modeRandom = BOOSMODE.MODE1;//攻击模式

    public Enemy(Context mContext, ENEMYTYPE enemytype, int screenW, int screenH) {
        switch (enemytype) {
            case ENEMY1:
                isBoos = false;
                bitmap = BitmapUtils.ReadBitMap(mContext, R.drawable.icon_aircraftwars_enemy1);
                interval = 150;
                fraction = 1;
                blood = 1;
                break;
            case ENEMY2:
                isBoos = false;
                bitmap = BitmapUtils.ReadBitMap(mContext, R.drawable.icon_aircraftwars_enemy2);
                interval = 130;
                fraction = 2;
                blood = 2;
                break;
            case ENEMY3:
                isBoos = false;
                bitmap = BitmapUtils.ReadBitMap(mContext, R.drawable.icon_aircraftwars_enemy3);
                fraction = 3;
                blood = 3;
                break;
            case BOOS1:
                isBoos = true;
                bitmap = BitmapUtils.ReadBitMap(mContext, R.drawable.icon_aircraftwars_boos1);
                fraction = 100;
                blood = 100;
                break;
            case BOOS2:
                isBoos = true;
                bitmap = BitmapUtils.ReadBitMap(mContext, R.drawable.icon_aircraftwars_boos2);
                fraction = 100;
                blood = 100;
                break;
            case BOOS3:
                isBoos = true;
                bitmap = BitmapUtils.ReadBitMap(mContext, R.drawable.icon_aircraftwars_boos3);
                fraction = 100;
                blood = 100;
                break;
            case BOOS4:
                isBoos = true;
                bitmap = BitmapUtils.ReadBitMap(mContext, R.drawable.icon_aircraftwars_boos4);
                interval = 50;
                fraction = 100;
                blood = 100;
                break;
        }
        this.enemytype = enemytype;
        this.mScreenW = screenW;
        this.mScreenH = screenH;
        x = OtherUtil.getRandom(0,screenW-bitmap.getWidth());
        y = -bitmap.getHeight();
    }


    public void myDraw(Canvas mCanvas, Paint mPaint) {
        mCanvas.drawBitmap(bitmap,x,y,mPaint);

        rect = new Rect(x+bitmap.getWidth()/4,y+bitmap.getHeight()/4,x+(bitmap.getWidth()-bitmap.getWidth()/4),y+(bitmap.getHeight()-bitmap.getHeight()/4));

        if(ApkConstant.isDebug) {
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setColor(Color.RED);
            mCanvas.drawRect(rect, mPaint);
        }
    }

    public void logic(Context mContext, Vector<Bullet> vcBullet, long frame, int screenW, int screenH) {
        if(isBoos){
            if(y <= bitmap.getHeight()+30){
                y += speed;
            }else{
                if(!isBoosAttack)
                    getBoosMode();
                else{
                    if(frame%interval == 0) {
                        switch (modeRandom) {
                            case MODE1:
                                vcBullet.add(new Bullet(mContext, Bullet.TYPE.ENEMY, enemytype,modeRandom, 1, Play.LEVEL.LEVEL1,
                                        x + bitmap.getWidth() / 3 , y + bitmap.getHeight(), 0, screenW, screenH));
                                vcBullet.add(new Bullet(mContext, Bullet.TYPE.ENEMY, enemytype, modeRandom, 1, Play.LEVEL.LEVEL1,
                                        x + (bitmap.getWidth()-bitmap.getWidth() / 3), y + bitmap.getHeight(), 0, screenW, screenH));
                                break;
                            case MODE2:
                                vcBullet.add(new Bullet(mContext, Bullet.TYPE.ENEMY, enemytype, modeRandom, 1, Play.LEVEL.LEVEL1,
                                        x + bitmap.getWidth() / 3 , y + bitmap.getHeight(), 30, screenW, screenH));
                                vcBullet.add(new Bullet(mContext, Bullet.TYPE.ENEMY, enemytype, modeRandom, 1, Play.LEVEL.LEVEL1,
                                        x + bitmap.getWidth() / 3 , y + bitmap.getHeight(), 0, screenW, screenH));
                                vcBullet.add(new Bullet(mContext, Bullet.TYPE.ENEMY, enemytype, modeRandom, 1, Play.LEVEL.LEVEL1,
                                        x + bitmap.getWidth() / 3 , y + bitmap.getHeight(), 330, screenW, screenH));
                                vcBullet.add(new Bullet(mContext, Bullet.TYPE.ENEMY, enemytype, modeRandom, 1, Play.LEVEL.LEVEL1,
                                        x + (bitmap.getWidth()-bitmap.getWidth() / 3), y + bitmap.getHeight(), 30, screenW, screenH));
                                vcBullet.add(new Bullet(mContext, Bullet.TYPE.ENEMY, enemytype, modeRandom, 1, Play.LEVEL.LEVEL1,
                                        x + (bitmap.getWidth()-bitmap.getWidth() / 3), y + bitmap.getHeight(), 0, screenW, screenH));
                                vcBullet.add(new Bullet(mContext, Bullet.TYPE.ENEMY, enemytype, modeRandom, 1, Play.LEVEL.LEVEL1,
                                        x + (bitmap.getWidth()-bitmap.getWidth() / 3), y + bitmap.getHeight(), 330, screenW, screenH));
                                break;
                            case MODE3:
                                for (int i = 0; i < 360; i+=18) {
                                    vcBullet.add(new Bullet(mContext, Bullet.TYPE.ENEMY, enemytype, modeRandom, 1, Play.LEVEL.LEVEL1,
                                            x + bitmap.getWidth() / 2 , y + bitmap.getHeight()/2, i, screenW, screenH));
                                }
                                break;
                            case MODE4:

                                break;
                            case MODE5:

                                break;
                        }
                    }
                }
                if(leftShift) {
                    x -= speed;
                    if(x<=30) leftShift = false;
                }else{
                    x += speed;
                    if(x>=mScreenW-bitmap.getWidth()-30) leftShift = true;
                }
            }
        }else{
            if(frame%interval == 0) {
                int bulletx = x + bitmap.getWidth() / 2;
                int bullety = y + bitmap.getHeight() /2;
                vcBullet.add(new Bullet(mContext, Bullet.TYPE.ENEMY,enemytype, modeRandom, 1,Play.LEVEL.LEVEL1, bulletx, bullety,0,screenW,screenH));
            }
            y += speed;
        }
        if(y > this.mScreenH){
            isDead = true;
        }
    }


    /**
     * 受伤
     */
    public void Injured(int aggressivity,onInjuredListener listener){
        blood -= aggressivity;
        if(blood<1){
            isDead = true;
            if(listener!=null) listener.death();
        }
    }

    public interface onInjuredListener{
        void death();//死亡
    }
    public interface onBoosInjuredListener{
        void boosDeath();//boos死亡
    }

    public enum ENEMYTYPE{
        ENEMY1,//敌机1
        ENEMY2,//敌机2
        ENEMY3, //敌机3
        BOOS1,//boos1
        BOOS2,//boos2
        BOOS3,//boos3
        BOOS4 //boos4
    }

    /**
     * boos攻击方式
     */
    public enum BOOSMODE{
        MODE1,
        MODE2,
        MODE3,
        MODE4,
        MODE5
    }

    private int modeDuration;//攻击模式持续时间
    private void getBoosMode(){
        isBoosAttack = true;
        modeDuration = OtherUtil.getRandom(5,10);//攻击时间
        modeRandom = getBoosModeRandom();//攻击模式
        durationTimerUtil.startWork(modeDuration * 1000, new SDTimerUtil.SDTimerListener() {
            @Override
            public void onWork() {

            }

            @Override
            public void onWorkMain() {
                isBoosAttack = false;
            }
        });
    }

    /**
     * 获取随机boos攻击方式
     */
    private BOOSMODE getBoosModeRandom(){
//        int random = OtherUtil.getRandom(1,5);
//        if(random == 1){ return BOOSMODE.MODE1; }
//        if(random == 2){ return BOOSMODE.MODE2; }
//        if(random == 3){ return BOOSMODE.MODE3; }
//        if(random == 4){ return BOOSMODE.MODE4; }
//        if(random == 5){ return BOOSMODE.MODE5; }
//        interval = 15;
//        interval = 15;
        interval = 15;
        return BOOSMODE.MODE3;
    }

    public Rect getRect() {
        return rect;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public int getFraction() {
        return fraction;
    }

    public boolean isBoos() {
        return isBoos;
    }
}
