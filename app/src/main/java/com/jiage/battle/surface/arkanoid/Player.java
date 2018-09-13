package com.jiage.battle.surface.arkanoid;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Region;

import com.jiage.battle.constant.Constant;

/**
 * 作者：李忻佳
 * 日期：2018/8/15
 * 说明：主角
 */

public class Player {
    private float mInitX,mInitY;
    private RectF mRecf;
    private int width;//增加宽度
    private int mScreenH;

    public Player(int playerInitX, int playerInitY, int screenH){
        this.mInitX = playerInitX;
        this.mInitY = playerInitY;
        this.mScreenH = screenH;
    }

    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(Constant.Player.color);
        mRecf = new RectF(mInitX, mInitY, mInitX+Constant.Player.width+width, mInitY+Constant.Player.height);
        canvas.drawRoundRect(mRecf, 30, 50, paint);
    }
    public void logic() {

    }

    /**
     * 获取
     * @return
     */
    public Region getRegion(){
        return new Region((int)mInitX, (int)mInitY, (int)mInitX+Constant.Player.width, mScreenH);
    }

    public void setmInitX(float mInitX) {
        this.mInitX = mInitX;
    }

    public float getmInitX() {
        return mInitX;
    }

    public float getmInitY() {
        return mInitY;
    }

    public void setmInitY(float mInitY) {
        this.mInitY = mInitY;
    }

    public RectF getmRecf() {
        return mRecf;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
