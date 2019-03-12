package com.jiage.battle.surface.sickto;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import com.jiage.battle.util.OtherUtil;
import com.jiage.battle.util.SurfaceViewUtil;

import java.util.Vector;

/**
 * 作者：李忻佳
 * 日期：2018/12/26
 * 说明：敌人
 */

public class Enemy {
    private Bitmap mBitmap;
    private int blood=1;//血量
    private int x,y;
    private int sleep = 5;
    private int angle = -1;//角度
    private boolean isDead;// 优化处理
    private int attackRange = 100;//攻击范围
    private RectF mRect;
    private int target = -1; //目标
    private int money = 10;

    public Enemy(Bitmap bitmap, int screenW, int screenH, long time){
        blood += time;
        this.mBitmap = bitmap;
        int random = OtherUtil.getRandom(1,4);
        if(random == 1){
            this.x = -30;
            this.y = OtherUtil.getRandom(0,screenW+60)-30;
        }
        if(random == 2){
            this.x = screenW+30;
            this.y = OtherUtil.getRandom(0,screenH+60)-30;
        }
        if(random == 3){
            this.x = OtherUtil.getRandom(0,screenW+60)-30;
            this.y = -30;
        }
        if(random == 4){
            this.x = OtherUtil.getRandom(0,screenW+60)-30;
            this.y = screenH+30;
        }
//        Log.e("Enemy","x："+x+"  y："+y);
    }

    public void draw(Canvas canvas , Paint paint) {
        mRect = new RectF(x,y,x+mBitmap.getWidth(), y+mBitmap.getHeight());
        SurfaceViewUtil.drawRotateBitmap(canvas,paint,mBitmap,angle,x,y);
//        paint.setColor(Color.YELLOW);
//        paint.setStyle(Paint.Style.STROKE);
//        RectF rect = new RectF(mRect.centerX() - 20, mRect.centerY() - 20, mRect.centerX() + 20, mRect.centerY() + 20);
//        canvas.drawRect(rect,paint);
    }

    public void logic(Play mPlay, Vector<Soldier> vcSoldier) {
        if(mRect!=null) {
            if (target == -1) {
                double juli = 0;
                for (int i = 0; i < vcSoldier.size(); i++) {
                    Soldier soldier = vcSoldier.elementAt(i);
                    if(soldier.getmRect()!=null) {
                        double jl = SurfaceViewUtil.gePointDistance(mRect.centerX(), mRect.centerY(), soldier.getmRect().centerX(), soldier.getmRect().centerY());//与敌人距离
                        if (juli != 0 && jl > juli) {
                        } else {
                            juli = jl;
                            target = i;
//                            Log.e("Enemy", "锁定士兵：" + target);
                        }
                    }
                }
            }
            if (target != -1 && target < vcSoldier.size()) {
                Soldier soldier = vcSoldier.elementAt(target);
                if(soldier.getmRect()!=null) {
                    double jl = SurfaceViewUtil.gePointDistance(mRect.centerX(), mRect.centerY(), soldier.getmRect().centerX(), soldier.getmRect().centerY());//与敌人距离
//            Log.e("Enemy","与敌人距离："+jl);
                    if (jl > attackRange) {
//                        if (angle == -1)
                            angle = SurfaceViewUtil.getRotationBetweenLines(soldier.getmRect().centerX(),soldier.getmRect().centerY(), mRect.centerX(), mRect.centerY());
//                        Log.e("Enemy", soldier.getmRect().centerX()+"-"+soldier.getmRect().centerY()+"-"+mRect.centerX()+"-"+mRect.centerY());
//                        Log.e("Enemy", "敌人移动角度：" + angle);
                        x = (int) SurfaceViewUtil.getCircleCoordinatesX(x, -(angle - 180), sleep);
                        y = (int) SurfaceViewUtil.getCircleCoordinatesY(y, -(angle - 180), sleep);
                    }
                }
            } else {
                target = -1;
                angle = -1;
            }
        }
    }

    /**
     * 受伤
     */
    public void Injured(int attack){
        blood -=attack;
        if(blood<1)
            isDead = true;
    }

    public boolean isDead() {
        return isDead;
    }

    public RectF getmRect() {
        return mRect;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getMoney() {
        return money;
    }
}
