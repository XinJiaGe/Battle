package com.jiage.battle.surface.sickto;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import com.jiage.battle.util.SurfaceViewUtil;

import java.util.Vector;

/**
 * 作者：李忻佳
 * 日期：2018/12/26
 * 说明：小兵
 */

public class Soldier {
    private Bitmap mBtmaps[];
    private int x,y,mScreenW,mScreenH;
    private int angle = 0;//角度
    private int attackRange = 300;//攻击范围
    private boolean isDead;// 优化处理
    private Grade grade = Grade.ONE;
    private RectF mRect;
    private int target = -1;//攻击目标

    public Soldier(Bitmap[] bitmaps,int x,int y ,int screenW, int screenH,Grade grade){
        this.mBtmaps = bitmaps;
        this.grade = grade;
        this.x = x;
        this.y = y;
        this.mScreenW = screenW;
        this.mScreenH = screenH;
    }

    public void draw(Canvas canvas , Paint paint) {
        switch (grade) {
            case ONE:
                mRect = new RectF(x-mBtmaps[0].getWidth()/2,y-mBtmaps[0].getHeight()/2,x+mBtmaps[0].getWidth()/2,y+mBtmaps[0].getHeight()/2);
                SurfaceViewUtil.drawRotateBitmap(canvas,paint,mBtmaps[0],angle,x-mBtmaps[0].getWidth()/2,y-mBtmaps[0].getHeight()/2);
                break;
            case TWO:
                mRect = new RectF(x-mBtmaps[1].getWidth()/2,y-mBtmaps[1].getHeight()/2,x+mBtmaps[1].getWidth()/2,y+mBtmaps[1].getHeight()/2);
                SurfaceViewUtil.drawRotateBitmap(canvas,paint,mBtmaps[1],angle,x-mBtmaps[1].getWidth()/2,y-mBtmaps[1].getHeight()/2);
                break;
            case THREE:
                mRect = new RectF(x-mBtmaps[2].getWidth()/2,y-mBtmaps[2].getHeight()/2,x+mBtmaps[2].getWidth()/2,y+mBtmaps[2].getHeight()/2);
                SurfaceViewUtil.drawRotateBitmap(canvas,paint,mBtmaps[2],angle,x-mBtmaps[2].getWidth()/2,y-mBtmaps[2].getHeight()/2);
                break;
        }
        paint.setColor(Color.YELLOW);
        paint.setStyle(Paint.Style.STROKE);
        RectF rect = new RectF(mRect.centerX() - 20, mRect.centerY() - 20, mRect.centerX() + 20, mRect.centerY() + 20);
        canvas.drawRect(rect,paint);
        paint.setColor(Color.RED);
        canvas.drawCircle(x,y,attackRange,paint);
    }

    public void logic(Vector<Enemy> vcEnemy) {
        if(target == -1){
            for (int i = 0; i < vcEnemy.size(); i++) {
                Enemy enemy = vcEnemy.elementAt(i);
                double juli = SurfaceViewUtil.gePointDistance(mRect.centerX(),mRect.centerY(),enemy.getmRect().centerX(),enemy.getmRect().centerY());//与敌人距离
                if(juli<=attackRange){//敌人是否在攻击氛围内
                    target = i;
                    Log.e("Soldier","检测到敌人进入攻击范围 target="+target);
                    break;
                }
            }
        }
        if(target != -1){
            double juli = SurfaceViewUtil.gePointDistance(mRect.centerX(),mRect.centerY(),vcEnemy.elementAt(target).getmRect().centerX(),vcEnemy.elementAt(target).getmRect().centerY());//与敌人距离
            if(target<vcEnemy.size()&&juli<=attackRange){
                angle = SurfaceViewUtil.getRotationBetweenLines((int) vcEnemy.elementAt(target).getmRect().centerX(), (int) vcEnemy.elementAt(target).getmRect().centerY(),
                        (int) mRect.centerX(), (int) mRect.centerY());
                Log.e("Soldier","锁定敌人 angle="+angle);
            }else{
                target = -1;
                Log.e("Soldier","敌人离开攻击范围"+angle);
            }
        }



        switch (grade) {
            case ONE:
                break;
            case TWO:
                break;
            case THREE:
                break;
        }
    }

    public boolean isDead() {
        return isDead;
    }

    public enum Grade{
        ONE,TWO,THREE
    }

    public RectF getmRect() {
        return mRect;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }
}
