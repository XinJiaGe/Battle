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
import com.jiage.battle.util.SDTimerUtil;
import com.jiage.battle.util.SurfaceViewUtil;

import java.util.Vector;

/**
 * 作者：李忻佳
 * 日期：2018/11/26
 * 说明：主角
 */

public class Play {
    private Rect rect = new Rect(0,0,0,0);
    private Bitmap bitmap,bitmapRotectionCover;
    private int x,y,width,height,protectionCoverx,protectionCovery,protectionCoverWidth;
    private LEVEL level = LEVEL.LEVEL1;//子弹级别
    private int interval = 6;//子弹间隔
    private boolean Invincible = true;//无敌
    private boolean protectionCover = false;//防护罩
    private SDTimerUtil protectionTimer = new SDTimerUtil();

    public Play(Context content, int mScreenW, int mScreenH) {
        bitmap = BitmapUtils.ReadBitMap(content, R.drawable.icon_aircraftwars_play);
        bitmapRotectionCover = BitmapUtils.ReadBitMap(content, R.drawable.icon_aircraftwar_rotection_cover);
        protectionCoverWidth = bitmapRotectionCover.getWidth()/2-bitmapRotectionCover.getWidth()/12;
        width = bitmap.getWidth();
        height = bitmap.getHeight();
        x = mScreenW/2-bitmap.getWidth()/2;
        y = mScreenH-bitmap.getHeight()-100;
    }

    private int invincibleStepping = 0;
    private boolean isLatent = false;
    public void myDraw(Canvas mCanvas, Paint mPaint) {
        if(Invincible) {
            if(!isLatent){
                mCanvas.drawBitmap(bitmap,x,y,mPaint);
            }
            invincibleStepping ++;
            if(invincibleStepping>5){
                invincibleStepping = 0;
                if(isLatent) isLatent = false;
                else isLatent = true;
            }
        }else{
            mCanvas.drawBitmap(bitmap,x,y,mPaint);
        }
        if(protectionCover){
            mCanvas.drawBitmap(bitmapRotectionCover,x+bitmap.getWidth()/2-bitmapRotectionCover.getWidth()/2,
                    y+bitmap.getHeight()/2-bitmapRotectionCover.getHeight()/2,mPaint);

            if(ApkConstant.isDebug) {
                mPaint.setStyle(Paint.Style.STROKE);
                mPaint.setColor(Color.RED);
                mCanvas.drawCircle(protectionCoverx, protectionCovery, protectionCoverWidth, mPaint);
            }
        }

        rect = new Rect(x+bitmap.getWidth()/4,y+bitmap.getHeight()/4,x+(bitmap.getWidth()-bitmap.getWidth()/4),y+(bitmap.getHeight()-bitmap.getHeight()/4));
        protectionCoverx = rect.centerX();
        protectionCovery = rect.centerY();

        if(ApkConstant.isDebug) {
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setColor(Color.RED);
            mCanvas.drawRect(rect, mPaint);
        }
    }

    public void logic(Context mContext, Vector<Bullet> vcBullet, Vector<Enemy> vcEnemy, long frame, int screenW, int screenH, AircraftSurface.onSurfaceListener mListener) {
        if(frame%interval == 0) {
            int bulletx = x + bitmap.getWidth() / 4;
            int bulletx2 = x + (bitmap.getWidth() - bitmap.getWidth() / 4);
            switch (level) {
                case LEVEL1:
                    vcBullet.add(new Bullet(mContext, Bullet.TYPE.PLAY, null, modeRandom, 1,level,bulletx, y,0,screenW,screenH));
                    vcBullet.add(new Bullet(mContext, Bullet.TYPE.PLAY, null, modeRandom, 2,level,bulletx2, y,0,screenW,screenH));
                    break;
                case LEVEL2:
                    vcBullet.add(new Bullet(mContext, Bullet.TYPE.PLAY, null, modeRandom, 1,level, bulletx, y,0,screenW,screenH));
                    vcBullet.add(new Bullet(mContext, Bullet.TYPE.PLAY, null, modeRandom, 2,level, bulletx, y,0,screenW,screenH));
                    vcBullet.add(new Bullet(mContext, Bullet.TYPE.PLAY, null, modeRandom, 3,level, bulletx2, y,0,screenW,screenH));
                    vcBullet.add(new Bullet(mContext, Bullet.TYPE.PLAY, null, modeRandom, 4,level, bulletx2, y,0,screenW,screenH));
                    break;
                case LEVEL3:
                    vcBullet.add(new Bullet(mContext, Bullet.TYPE.PLAY, null, modeRandom, 1,level, bulletx, y,0,screenW,screenH));
                    vcBullet.add(new Bullet(mContext, Bullet.TYPE.PLAY, null, modeRandom, 2,level, bulletx, y,0,screenW,screenH));
                    vcBullet.add(new Bullet(mContext, Bullet.TYPE.PLAY, null, modeRandom, 3,level, bulletx, y,0,screenW,screenH));
                    vcBullet.add(new Bullet(mContext, Bullet.TYPE.PLAY, null, modeRandom, 4,level, bulletx2, y,0,screenW,screenH));
                    vcBullet.add(new Bullet(mContext, Bullet.TYPE.PLAY, null, modeRandom, 5,level, bulletx2, y,0,screenW,screenH));
                    vcBullet.add(new Bullet(mContext, Bullet.TYPE.PLAY, null, modeRandom, 6,level, bulletx2, y,0,screenW,screenH));
                    break;
            }
        }
        if(!isInvincible()&&!isProtectionCover()) {//是否无敌，是否有防护罩
            for (int i = 0; i < vcEnemy.size(); i++) {
                Enemy enemy = vcEnemy.elementAt(i);
                if (SurfaceViewUtil.isCollsionWithRect(rect, enemy.getRect())) {//主角和敌人碰撞了
                    vcEnemy.removeElement(enemy);
                    if(mListener!=null) mListener.injured(9999);
                }
            }
        }
    }

    /**
     * 获得防护罩
     */
    public void addProtectionCover() {
        this.protectionCover = true;
        if(protectionTimer.isWorking()) protectionTimer.stopWork();
        protectionTimer.startWork(5000, new SDTimerUtil.SDTimerListener() {
            @Override
            public void onWork() {

            }

            @Override
            public void onWorkMain() {
                Play.this.protectionCover = false;
            }
        });
    }

    public enum LEVEL{//子弹级别
        LEVEL1,
        LEVEL2,
        LEVEL3
    }

    /**
     * 升级
     */
    public void upgrade(){
        switch (level) {
            case LEVEL1: level = LEVEL.LEVEL2; break;
            case LEVEL2: level = LEVEL.LEVEL3; break;
            default: level = LEVEL.LEVEL3; break;
        }
    }

    /**
     * 降级
     */
    public void downgrade(){
        switch (level) {
            case LEVEL3: level = LEVEL.LEVEL2; break;
            case LEVEL2: level = LEVEL.LEVEL1; break;
            default: level = LEVEL.LEVEL1; break;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Rect getRect() {
        return rect;
    }

    public boolean isInvincible() {
        return Invincible;
    }

    public void setInvincible(boolean invincible) {
        Invincible = invincible;
    }

    public boolean isProtectionCover() {
        return protectionCover;
    }

    public int getProtectionCoverx() {
        return protectionCoverx;
    }

    public int getProtectionCovery() {
        return protectionCovery;
    }

    public int getProtectionCoverWidth() {
        return protectionCoverWidth;
    }

}
