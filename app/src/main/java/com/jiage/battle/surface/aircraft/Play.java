package com.jiage.battle.surface.aircraft;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.jiage.battle.R;
import com.jiage.battle.util.BitmapUtils;
import com.jiage.battle.util.SDTimerUtil;
import com.jiage.battle.util.SDViewUtil;

import java.util.Vector;

/**
 * 作者：李忻佳
 * 日期：2018/11/26
 * 说明：主角
 */

public class Play {
    private SDTimerUtil timerUtil = new SDTimerUtil();
    private int height;
    private int width;
    private double index = 1;
    private Bitmap mHero0;
    private Bitmap mHero1;
    private int interval = 5;//子弹间隔
    private int x;
    private int y;
    private Rect rect;
    private boolean Invincible = false;//无敌
    private int blood = 10;//血量

    public Play(Context mContext) {
        //加载主角图片
        mHero0 = BitmapUtils.ReadBitMap(mContext, R.drawable.hero0);
        mHero1 = BitmapUtils.ReadBitMap(mContext, R.drawable.hero1);
        width = SDViewUtil.getScreenWidth();
        height = SDViewUtil.getScreenHeight();
        x = width / 2 - mHero0.getWidth() / 2;
        y = height - mHero0.getHeight() - 100;
        rect = new Rect(x+mHero0.getWidth()/2-15, y+10, x+mHero0.getWidth()/2+15, y+mHero0.getHeight()-30);
    }

    private int invincibleStepping = 0;
    private boolean isLatent = false;
    public void myDraw(Canvas mCanvas, Paint mPaint, long time) {
        if(Invincible) {
            if(!isLatent){
                if (index % 2 == 0) {
                    mCanvas.drawBitmap(mHero0, x, y, mPaint);
                } else {
                    mCanvas.drawBitmap(mHero1, x, y, mPaint);
                }
            }
            invincibleStepping ++;
            if(invincibleStepping>5){
                invincibleStepping = 0;
                if(isLatent) isLatent = false;
                else isLatent = true;
            }
        }else{
            if (index % 2 == 0) {
                mCanvas.drawBitmap(mHero0, x, y, mPaint);
            } else {
                mCanvas.drawBitmap(mHero1, x, y, mPaint);
            }
        }

//        mPaint.setColor(Color.RED);
//        mCanvas.drawRect(rect, mPaint);
    }

    public void logic(Vector<Bullet> vcBullet, long time, AircraftSurface.PLAYERGRADE playerGrade) {
        index += 0.5;
        if (index > 100) {
            index = 1;
        }
        switch (playerGrade) {
            case INIT:
                if(time%interval == 0){
                    vcBullet.add(new Bullet(Bullet.TYPE.PLAYER,playerGrade,x+mHero0.getWidth()/2,y+mHero0.getHeight()/2,0));
                }
                break;
            case level1:
                if(time%interval == 0){
                    vcBullet.add(new Bullet(Bullet.TYPE.PLAYER,playerGrade,x+mHero0.getWidth()/2/2,y+mHero0.getHeight()/2,0));
                    vcBullet.add(new Bullet(Bullet.TYPE.PLAYER,playerGrade,x+mHero0.getWidth()/2,y+mHero0.getHeight()/2,0));
                    vcBullet.add(new Bullet(Bullet.TYPE.PLAYER,playerGrade,x+(mHero0.getWidth()-mHero0.getWidth()/2/2),y+mHero0.getHeight()/2,0));
                }
                break;
            case level2:
                if(time%interval == 0){
                    vcBullet.add(new Bullet(Bullet.TYPE.PLAYER,playerGrade,x+mHero0.getWidth()/2/2,y+mHero0.getHeight()/2,0));
                    vcBullet.add(new Bullet(Bullet.TYPE.PLAYER,playerGrade,x+mHero0.getWidth()/2,y+mHero0.getHeight()/2,0));
                    vcBullet.add(new Bullet(Bullet.TYPE.PLAYER,playerGrade,x+(mHero0.getWidth()-mHero0.getWidth()/2/2),y+mHero0.getHeight()/2,0));
                }
                break;
            case level3:
                if(time%interval == 0) {
                    vcBullet.add(new Bullet(Bullet.TYPE.PLAYER, playerGrade, x + mHero0.getWidth() / 2 / 2, y + mHero0.getHeight() / 2, 195));
                    vcBullet.add(new Bullet(Bullet.TYPE.PLAYER, playerGrade, x + mHero0.getWidth() / 2 / 2, y + mHero0.getHeight() / 2, 190));
                    vcBullet.add(new Bullet(Bullet.TYPE.PLAYER, playerGrade, x + mHero0.getWidth() / 2, y + mHero0.getHeight() / 2, 185));
                    vcBullet.add(new Bullet(Bullet.TYPE.PLAYER, playerGrade, x + mHero0.getWidth() / 2, y + mHero0.getHeight() / 2, 175));
                    vcBullet.add(new Bullet(Bullet.TYPE.PLAYER, playerGrade, x + (mHero0.getWidth() - mHero0.getWidth() / 2 / 2), y + mHero0.getHeight() / 2, 170));
                    vcBullet.add(new Bullet(Bullet.TYPE.PLAYER, playerGrade, x + (mHero0.getWidth() - mHero0.getWidth() / 2 / 2), y + mHero0.getHeight() / 2, 165));
                }
                break;
            case level4:
                if(time%interval == 0) {
                    vcBullet.add(new Bullet(Bullet.TYPE.PLAYER, playerGrade, x + mHero0.getWidth() / 2 / 2, y + mHero0.getHeight() / 2, 195));
                    vcBullet.add(new Bullet(Bullet.TYPE.PLAYER, playerGrade, x + mHero0.getWidth() / 2 / 2, y + mHero0.getHeight() / 2, 190));
                    vcBullet.add(new Bullet(Bullet.TYPE.PLAYER, playerGrade, x + mHero0.getWidth() / 2, y + mHero0.getHeight() / 2, 185));
                    vcBullet.add(new Bullet(Bullet.TYPE.PLAYER, playerGrade, x + mHero0.getWidth() / 2, y + mHero0.getHeight() / 2, 175));
                    vcBullet.add(new Bullet(Bullet.TYPE.PLAYER, playerGrade, x + (mHero0.getWidth() - mHero0.getWidth() / 2 / 2), y + mHero0.getHeight() / 2, 170));
                    vcBullet.add(new Bullet(Bullet.TYPE.PLAYER, playerGrade, x + (mHero0.getWidth() - mHero0.getWidth() / 2 / 2), y + mHero0.getHeight() / 2, 165));
                }
                break;
            case level5:
                if(time%interval == 0) {
                    vcBullet.add(new Bullet(Bullet.TYPE.PLAYER, playerGrade, x + mHero0.getWidth() / 2 / 2, y + mHero0.getHeight() / 2, 195));
                    vcBullet.add(new Bullet(Bullet.TYPE.PLAYER, playerGrade, x + mHero0.getWidth() / 2 / 2, y + mHero0.getHeight() / 2, 190));
                    vcBullet.add(new Bullet(Bullet.TYPE.PLAYER, playerGrade, x + mHero0.getWidth() / 2, y + mHero0.getHeight() / 2, 185));
                    vcBullet.add(new Bullet(Bullet.TYPE.PLAYER, playerGrade, x + mHero0.getWidth() / 2, y + mHero0.getHeight() / 2, 175));
                    vcBullet.add(new Bullet(Bullet.TYPE.PLAYER, playerGrade, x + (mHero0.getWidth() - mHero0.getWidth() / 2 / 2), y + mHero0.getHeight() / 2, 170));
                    vcBullet.add(new Bullet(Bullet.TYPE.PLAYER, playerGrade, x + (mHero0.getWidth() - mHero0.getWidth() / 2 / 2), y + mHero0.getHeight() / 2, 165));
                }
                break;
            case level6:
                if(time%interval == 0) {
                    vcBullet.add(new Bullet(Bullet.TYPE.PLAYER, playerGrade, x + mHero0.getWidth() / 2 / 2, y + mHero0.getHeight() / 2, 205));
                    vcBullet.add(new Bullet(Bullet.TYPE.PLAYER, playerGrade, x + mHero0.getWidth() / 2 / 2, y + mHero0.getHeight() / 2, 200));
                    vcBullet.add(new Bullet(Bullet.TYPE.PLAYER, playerGrade, x + mHero0.getWidth() / 2 / 2, y + mHero0.getHeight() / 2, 195));
                    vcBullet.add(new Bullet(Bullet.TYPE.PLAYER, playerGrade, x + mHero0.getWidth() / 2 / 2, y + mHero0.getHeight() / 2, 190));
                    vcBullet.add(new Bullet(Bullet.TYPE.PLAYER, playerGrade, x + mHero0.getWidth() / 2, y + mHero0.getHeight() / 2, 185));
                    vcBullet.add(new Bullet(Bullet.TYPE.PLAYER, playerGrade, x + mHero0.getWidth() / 2, y + mHero0.getHeight() / 2, 175));
                    vcBullet.add(new Bullet(Bullet.TYPE.PLAYER, playerGrade, x + (mHero0.getWidth() - mHero0.getWidth() / 2 / 2), y + mHero0.getHeight() / 2, 170));
                    vcBullet.add(new Bullet(Bullet.TYPE.PLAYER, playerGrade, x + (mHero0.getWidth() - mHero0.getWidth() / 2 / 2), y + mHero0.getHeight() / 2, 165));
                    vcBullet.add(new Bullet(Bullet.TYPE.PLAYER, playerGrade, x + (mHero0.getWidth() - mHero0.getWidth() / 2 / 2), y + mHero0.getHeight() / 2, 160));
                    vcBullet.add(new Bullet(Bullet.TYPE.PLAYER, playerGrade, x + (mHero0.getWidth() - mHero0.getWidth() / 2 / 2), y + mHero0.getHeight() / 2, 155));
                }
                break;
        }
    }

    /**
     * 受伤
     * @param mOnLenter
     */
    public void injured(AircraftSurface.onLenter mOnLenter){
        blood --;
        if(mOnLenter!=null){
            if(blood<1){
                mOnLenter.gameOver();
            }else{
                Invincible = true;
                timerUtil.startWork(3000, new SDTimerUtil.SDTimerListener() {
                    @Override
                    public void onWork() {

                    }

                    @Override
                    public void onWorkMain() {
                        Invincible = false;
                    }
                });
            }
        }
    }

    /**
     * 增加血量
     */
    public void addBlood(){
        blood ++;
    }

    public void setX(int x) {
        this.x = x;
        rect = new Rect(x+mHero0.getWidth()/2-15, y+10, x+mHero0.getWidth()/2+15, y+mHero0.getHeight()-30);
    }

    public void setY(int y) {
        this.y = y;
        rect = new Rect(x+mHero0.getWidth()/2-15, y+10, x+mHero0.getWidth()/2+15, y+mHero0.getHeight()-30);
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Bitmap getmHero() {
        return mHero0;
    }

    public Rect getRect() {
        return rect;
    }

    public int getBlood() {
        return blood;
    }

    public boolean isInvincible() {
        return Invincible;
    }
}
