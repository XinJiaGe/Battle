package com.jiage.battle.surface.arkanoid;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.jiage.battle.constant.Constant;
import com.jiage.battle.sharedPreference.SharedPreference;
import com.jiage.battle.sound.GameSoundPool;


/**
 * 作者：李忻佳
 * 日期：2018/8/15
 * 说明：方块
 */

public class Square {
    private int mHeght;
    private GameSoundPool mSoundPool;
    private int mType;//方块血量
    private int mSize;//一行方块有多少个
    private int mScreenW,mScreenH;
    private int mInitX,mInitY,width,height;
    private int mX,mY;//关卡布局xy
    private Rect rect;
    public boolean isDead;// 血量是否为0，优化处理

    public Square(GameSoundPool soundPool, int type, int x, int y, int screenW, int screenH, int size, int initX, int initY, int heght) {
        mSoundPool = soundPool;
        mType = type;
        mX = x;
        mY = y;
        mScreenW = screenW;
        mScreenH = screenH;
        mInitX = initX;
        mInitY = initY;
        mSize = size;
        mHeght = heght;
    }

    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(Constant.Block.getColor(mType));
        rect = new Rect(mInitX,mInitY,mInitX+Constant.Block.getWidth(mScreenW,mSize),mInitY+mHeght);
        canvas.drawRect(rect,paint);
        paint.setColor(Color.WHITE);
        canvas.drawLine(rect.left,rect.top,rect.right,rect.top,paint);
        canvas.drawLine(rect.right,rect.top,rect.right,rect.bottom,paint);
        canvas.drawLine(rect.right,rect.bottom,rect.left,rect.bottom,paint);
        canvas.drawLine(rect.left,rect.bottom,rect.left,rect.top,paint);
        width = rect.width();
        height = rect.height();
    }
    public void logic(Bullet bullet) {
        if(rect!=null) {
            if (bullet.isTurn(false, false, rect.left, rect.top, rect.right, rect.bottom, bullet.getmInitX(), bullet.getmInitY(), Constant.Bullet.radius)) {
                if (mType != -1) {//是否是钢板
                    mType--;
                }
                if (mType == 0) {
                    isDead = true;
                }
                if(SharedPreference.getSharedPreference().isArkanoidBulletSound())
                    mSoundPool.play(GameSoundPool.ENEMYCLEARA);
            }
        }
    }

    public Rect getRect() {
        return rect;
    }

    public boolean isDead() {
        return isDead;
    }

    public int getmInitX() {
        return mInitX;
    }

    public void setmInitX(int mInitX) {
        this.mInitX = mInitX;
    }

    public int getmInitY() {
        return mInitY;
    }

    public void setmInitY(int mInitY) {
        this.mInitY = mInitY;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getmType() {
        return mType;
    }

    public int getmX() {
        return mX;
    }

    public int getmY() {
        return mY;
    }

    @Override
    public String toString() {
        return "Square{" +
                "mInitX=" + mInitX +
                ", mInitY=" + mInitY +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
