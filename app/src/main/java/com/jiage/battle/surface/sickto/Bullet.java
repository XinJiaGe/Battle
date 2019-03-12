package com.jiage.battle.surface.sickto;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.jiage.battle.util.SurfaceViewUtil;

import java.util.Vector;

/**
 * 作者：李忻佳
 * 日期：2018/12/26
 * 说明：子弹
 */

public class Bullet {
    private Bitmap mBtmap;
    private int angle;//角度
    private int aggressivity = 1;//攻击力
    private float x,y;
    public boolean isDead;// 优化处理
    private RectF mRect;
    private Soldier.Grade grade;
    private int sleep = 10;

    public Bullet(Bitmap bullet, float x, float y, int angle, Soldier.Grade grade){
        this.mBtmap = bullet;
        this.grade = grade;
        this.angle = angle;
        this.x = x;
        this.y = y;
        switch (grade) {

            case ONE:
                aggressivity = 1;
                break;
            case TWO:
                aggressivity = 5;
                break;
            case THREE:
                aggressivity = 10;
                break;
        }
    }

    public void draw(Canvas canvas , Paint paint) {
        mRect = new RectF(x-mBtmap.getWidth()/2,y-mBtmap.getHeight()/2,x+mBtmap.getWidth()/2,y+mBtmap.getHeight()/2);
        SurfaceViewUtil.drawRotateBitmap(canvas,paint,mBtmap,angle,x-mBtmap.getWidth()/2,y-mBtmap.getHeight()/2);
    }

    public int logic(Vector<Enemy> vcEnemy, int mScreenW, int mScreenH) {
        int isSi = 0;
        for (int i = 0; i < vcEnemy.size(); i++) {
            Enemy enemy = vcEnemy.elementAt(i);
            if(enemy.getmRect()!=null&&mRect!=null&&SurfaceViewUtil.isCollsionWithRect((int)mRect.left,(int)mRect.top,(int)mRect.width(),(int)mRect.height(),
                    (int)enemy.getmRect().left,(int)enemy.getmRect().top,(int)enemy.getmRect().width(),(int)enemy.getmRect().height())){
                enemy.Injured(aggressivity);
                isDead = true;
                isSi = enemy.getMoney();
//                Log.e("Bullet","相撞了");
            }
        }
        this.x = (int) SurfaceViewUtil.getCircleCoordinatesX((int) x, -(angle - 180), sleep);
        this.y = (int) SurfaceViewUtil.getCircleCoordinatesY((int) y, -(angle - 180), sleep);

        if(x<0||x>mScreenW||y<0||y>mScreenH)
            isDead = true;
        return isSi;
    }

    public boolean isDead() {
        return isDead;
    }
}
