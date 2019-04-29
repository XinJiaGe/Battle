package com.jiage.battle.surface.aircraft2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.jiage.battle.constant.ApkConstant;
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
    private Rect rect = new Rect(0, 0, 0, 0);
    private Bitmap bitmap;
    private BoosType.ENEMYTYPE enemytype = BoosType.ENEMYTYPE.ENEMY1;
    private int x, y, mScreenW, mScreenH;
    private int speed = 5; //速度
    private int fraction; //分数
    private int blood; //当前血量
    private int maxBlood; //总血量
    private boolean leftShift = true;
    private boolean isBoos = false;//是否是boos
    private boolean isBoosAttack = false;//boos是否正在攻击
    private boolean isDead = false;
    private BoosType.BOOSMODE modeRandom = BoosType.BOOSMODE.MODE1;//攻击模式

    public Enemy(Context mContext, BoosType.ENEMYTYPE enemytype, int screenW, int screenH) {
        bitmap = BoosType.getBoosBitmap(mContext, enemytype);
        switch (enemytype) {
            case ENEMY1:
                isBoos = false;
                fraction = 1;
                blood = 1;
                break;
            case ENEMY2:
                isBoos = false;
                fraction = 2;
                blood = 2;
                break;
            case ENEMY3:
                isBoos = false;
                fraction = 3;
                blood = 3;
                break;
            case BOOS1:
                isBoos = true;
                fraction = 100;
                blood = 100;
                maxBlood = 100;
                break;
            case BOOS2:
                isBoos = true;
                fraction = 100;
                blood = 100;
                maxBlood = 100;
                break;
            case BOOS3:
                isBoos = true;
                fraction = 100;
                blood = 100;
                maxBlood = 100;
                break;
            case BOOS4:
                isBoos = true;
                fraction = 100;
                blood = 100;
                maxBlood = 100;
                break;
        }
        this.enemytype = enemytype;
        this.mScreenW = screenW;
        this.mScreenH = screenH;
        x = OtherUtil.getRandom(0, screenW - bitmap.getWidth());
        y = -bitmap.getHeight();
    }


    public void myDraw(Canvas mCanvas, Paint mPaint) {
        mCanvas.drawBitmap(bitmap, x, y, mPaint);

        rect = new Rect(x + bitmap.getWidth() / 4, y + bitmap.getHeight() / 4, x + (bitmap.getWidth() - bitmap.getWidth() / 4), y + (bitmap.getHeight() - bitmap.getHeight() / 4));

        if (ApkConstant.isDebug) {
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setColor(Color.RED);
            mCanvas.drawRect(rect, mPaint);
        }
    }

    public void logic(Context mContext, Vector<Bullet> vcBullet, long frame, int screenW, int screenH) {
        if (isBoos) {//是否是boos
            if (y <= bitmap.getHeight()) {//判断boos是否已经出来
                y += speed;
            } else {
                if (!isBoosAttack)//判断boos是否正在攻击
                    getBoosMode();
                else {
                    if (frame % BoosType.interval(enemytype,modeRandom) == 0) {//boos发射子弹间隔
                        boosAngle = BoosType.boosMode(mContext, enemytype, modeRandom, vcBullet, x, y, bitmap, boosAngle, screenW, screenH);
                    }
                }
                if (leftShift) {
                    x -= speed;
                    if (x <= 30) leftShift = false;
                } else {
                    x += speed;
                    if (x >= mScreenW - bitmap.getWidth() - 30) leftShift = true;
                }
            }
        } else {
            if (frame % BoosType.interval(enemytype, modeRandom) == 0) {
                int bulletx = x + bitmap.getWidth() / 2;
                int bullety = y + bitmap.getHeight() / 2;
                vcBullet.add(new Bullet(mContext, Bullet.TYPE.ENEMY, enemytype, modeRandom, 1, Play.LEVEL.LEVEL1, bulletx, bullety,20, 0, screenW, screenH));
            }
            y += speed;
        }
        if (y > this.mScreenH) {
            isDead = true;
        }
    }

    /**
     * 获取boos攻击模式
     */
    private int modeDuration;//攻击模式持续时间
    private int boosAngle = 0;//boos 攻击角度
    private void getBoosMode() {
        isBoosAttack = true;
        modeDuration = OtherUtil.getRandom(5, 10);//攻击时间
        modeRandom = BoosType.getBoosModeRandom();//攻击模式
        if(modeRandom == BoosType.BOOSMODE.MODE4) boosAngle = 0;
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
     * 受伤
     */
    public void Injured(int aggressivity, onInjuredListener listener) {
        blood -= aggressivity;
        if (blood < 1) {
            isDead = true;
            if (listener != null) listener.death();
        }
    }

    public interface onInjuredListener {
        void death();//死亡
    }

    public interface onBoosInjuredListener {
        void boosDeath();//boos死亡
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

    public int getBlood() {
        return blood;
    }

    public int getMaxBlood() {
        return maxBlood;
    }
}
