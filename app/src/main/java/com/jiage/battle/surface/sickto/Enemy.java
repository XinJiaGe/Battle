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
 * 说明：敌人
 */

public class Enemy {
    private Bitmap mBitmap;
    private int x,y;
    private int sleep = 10;
    private int angle = -1;//角度
    private boolean isDead;// 优化处理
    private RectF mRect;
    private int target = -1; //目标

    public Enemy(Bitmap bitmap){
        this.mBitmap = bitmap;
        this.x = 100;
        this.y = 100;
    }

    public void draw(Canvas canvas , Paint paint) {
        mRect = new RectF(x,y,mBitmap.getWidth()+x, mBitmap.getHeight()+y);
        SurfaceViewUtil.drawRotateBitmap(canvas,paint,mBitmap,angle,x,y);
        paint.setColor(Color.YELLOW);
        paint.setStyle(Paint.Style.STROKE);
        RectF rect = new RectF(mRect.centerX() - 20, mRect.centerY() - 20, mRect.centerX() + 20, mRect.centerY() + 20);
        canvas.drawRect(rect,paint);
    }

    public void logic(Play mPlay, Vector<Soldier> vcSoldier) {
        if(target == -1) {
            double juli = 0;
            for (int i = 0; i < vcSoldier.size(); i++) {
                Soldier soldier = vcSoldier.elementAt(i);
                double jl = SurfaceViewUtil.gePointDistance(mRect.centerX(), mRect.centerY(), soldier.getmRect().centerX(), soldier.getmRect().centerY());//与敌人距离
                if(juli!=0&&jl>juli){}else {
                    juli = jl;
                    target = i;
                }
            }
        }
        if(target != -1&&target<vcSoldier.size()){
            Soldier soldier = vcSoldier.elementAt(target);
            if(angle == -1)
                angle = SurfaceViewUtil.getRotationBetweenLines((int) soldier.getmRect().centerX(), (int) soldier.getmRect().centerY(),(int) mRect.centerX(), (int) mRect.centerY());
//            Log.e("Enemy","敌人移动角度："+angle);
            x = (int) SurfaceViewUtil.getCircleCoordinatesX(x,angle,sleep);
            y = (int) SurfaceViewUtil.getCircleCoordinatesY(y,angle,sleep);
        }else{
            target = -1;
            angle = -1;
        }

    }

    public boolean isDead() {
        return isDead;
    }

    public RectF getmRect() {
        return mRect;
    }
}
