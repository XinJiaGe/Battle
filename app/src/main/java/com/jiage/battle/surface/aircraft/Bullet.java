package com.jiage.battle.surface.aircraft;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.jiage.battle.util.SDViewUtil;
import com.jiage.battle.util.SurfaceViewUtil;

/**
 * 作者：李忻佳
 * 日期：2018/11/26
 * 说明：子弹
 */

public class Bullet {
    private TYPE mType;
    private int x, y;
    private int width, height;
    private AircraftSurface.PLAYERGRADE playerGrade;//主角子弹级别
    private int radius = 5;//子弹大小
    private int angle = 0;//角度
    private int speed = 10; //速度
    private int hurt = 1;//伤害
    public boolean isDead = false;// 是否没有接到子弹，优化处理


    public Bullet(TYPE type, AircraftSurface.PLAYERGRADE playerGrade, int x, int y,int angle) {
        this.mType = type;
        this.width = SDViewUtil.getScreenWidth();
        this.height = SDViewUtil.getScreenHeight();
        this.playerGrade = playerGrade;
        this.angle = angle;
        this.x = x;
        this.y = y;
        setSpeed();
    }
    public Bullet(TYPE type, int x, int y, int angle) {
        this.mType = type;
        this.width = SDViewUtil.getScreenWidth();
        this.height = SDViewUtil.getScreenHeight();
        this.angle = angle;
        this.x = x;
        this.y = y;
        setSpeed();
    }

    public void myDraw(Canvas mCanvas, Paint mPaint) {
        switch (mType) {
            case PLAYER:
                mPaint.setColor(Color.BLACK);
                break;
            case ENEMY:
                mPaint.setColor(Color.RED);
                break;
            case BOOS:
                mPaint.setColor(Color.RED);
                break;
        }
        mCanvas.drawCircle(x, y, radius, mPaint);
    }

    public void logic() {
        switch (mType) {
            case PLAYER:
                switch (playerGrade) {
                    case INIT:
                        radius = 5;
                        y -= speed;
                        hurt = 1;
                        break;
                    case level1:
                        radius = 5;
                        y -= speed;
                        hurt = 1;
                        break;
                    case level2:
                        radius = 10;
                        y -= speed;
                        hurt = 2;
                        break;
                    case level3:
                        radius = 5;
                        hurt = 1;
                        x = (int)SurfaceViewUtil.getCircleCoordinatesX(x, angle, speed);
                        y = (int)SurfaceViewUtil.getCircleCoordinatesY(y, angle, speed);
                        break;
                    case level4:
                        radius = 10;
                        hurt = 2;
                        x = (int)SurfaceViewUtil.getCircleCoordinatesX(x, angle, speed);
                        y = (int)SurfaceViewUtil.getCircleCoordinatesY(y, angle, speed);
                        break;
                    case level5:
                        radius = 20;
                        hurt = 3;
                        x = (int)SurfaceViewUtil.getCircleCoordinatesX(x, angle, speed);
                        y = (int)SurfaceViewUtil.getCircleCoordinatesY(y, angle, speed);
                        break;
                    case level6:
                        radius = 20;
                        hurt = 3;
                        x = (int)SurfaceViewUtil.getCircleCoordinatesX(x, angle, speed);
                        y = (int)SurfaceViewUtil.getCircleCoordinatesY(y, angle, speed);
                        break;
                }
                break;
            case ENEMY:
                x = (int)SurfaceViewUtil.getCircleCoordinatesX(x, -angle, speed);
                y = (int)SurfaceViewUtil.getCircleCoordinatesY(y, -angle, speed);
                break;
            case BOOS:
                x = (int)SurfaceViewUtil.getCircleCoordinatesX(x, -angle, speed);
                y = (int)SurfaceViewUtil.getCircleCoordinatesY(y, -angle, speed);
                break;
        }

        if (x < -radius || x > width || y < -radius || y > height) {
            isDead = true;
        }
    }

    public boolean isDead() {
        return isDead;
    }

    public void setSpeed(){
        switch (mType) {
            case PLAYER:
                speed = 20;
                break;
            case ENEMY:
                speed = 10;
                break;
            case BOOS:
                speed = 10;
                break;
        }
    }

    enum TYPE {
        PLAYER,
        ENEMY,
        BOOS
    }

    public TYPE getmType() {
        return mType;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getRadius() {
        return radius;
    }

    public int getHurt() {
        return hurt;
    }
}
