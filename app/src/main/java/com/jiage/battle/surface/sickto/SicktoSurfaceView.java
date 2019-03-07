package com.jiage.battle.surface.sickto;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;

import com.jiage.battle.R;
import com.jiage.battle.surface.BaseSurfaceView;
import com.jiage.battle.util.BitmapUtils;
import com.jiage.battle.util.SurfaceViewUtil;

import java.util.Vector;

/**
 * 作者：李忻佳
 * 日期：2018/12/26
 * 说明：
 */

public class SicktoSurfaceView extends BaseSurfaceView {
    private Vector<Bullet> vcBullet = new Vector<>();//子弹集合
    private Vector<Enemy> vcEnemy = new Vector<>();//敌人集合
    private Vector<Soldier> vcSoldier = new Vector<>();//小兵集合
    private Vector<Wall> vcWall = new Vector<>();//墙集合
    private Play mPlay;
    private Bitmap[] soldiers = new Bitmap[3];

    @Override
    public void created() {
        mPlay = new Play(mScreenW,mScreenH);
        Bitmap soldier1 = BitmapUtils.ReadBitMap(mContext, R.drawable.soldier1);
        Bitmap soldier2 = BitmapUtils.ReadBitMap(mContext, R.drawable.soldier2);
        Bitmap soldier3 = BitmapUtils.ReadBitMap(mContext, R.drawable.soldier3);
        soldiers[0] = soldier1;
        soldiers[1] = soldier2;
        soldiers[2] = soldier3;
        vcSoldier.add(new Soldier(soldiers,mScreenW/2-100,mScreenH/2-100,mScreenW,mScreenH, Soldier.Grade.ONE));
        vcSoldier.add(new Soldier(soldiers,mScreenW/2+100,mScreenH/2-100,mScreenW,mScreenH, Soldier.Grade.TWO));
        vcSoldier.add(new Soldier(soldiers,mScreenW/2+100,mScreenH/2+100,mScreenW,mScreenH, Soldier.Grade.THREE));
        vcSoldier.add(new Soldier(soldiers,mScreenW/2-100,mScreenH/2+100,mScreenW,mScreenH, Soldier.Grade.ONE));

        vcEnemy.add(new Enemy(soldier1));
    }

    @Override
    public void myDraw() {
        mPlay.draw(mCanvas,mPaint);//绘制主角
        for (int i = 0; i < vcWall.size(); i++) {//绘制墙
            vcWall.elementAt(i).draw(mCanvas, mPaint);
        }
        for (int i = 0; i < vcEnemy.size(); i++) {//绘制敌人
            vcEnemy.elementAt(i).draw(mCanvas, mPaint);
        }
        for (int i = 0; i < vcSoldier.size(); i++) {//绘制小兵
            vcSoldier.elementAt(i).draw(mCanvas, mPaint);
        }
        for (int i = 0; i < vcBullet.size(); i++) {//绘制子弹
            vcBullet.elementAt(i).draw(mCanvas, mPaint);
        }
    }

    @Override
    public void logic() {
        mPlay.logic();//主角逻辑
        for (int i = 0; i < vcWall.size(); i++) {//墙逻辑
            Wall wall = vcWall.elementAt(i);
            if(wall.isDead()){
                vcWall.removeElement(wall);
            }else {
                vcWall.elementAt(i).logic();
            }
        }
        for (int i = 0; i < vcEnemy.size(); i++) {//敌人逻辑
            Enemy enemy = vcEnemy.elementAt(i);
            if(enemy.isDead()){
                vcEnemy.removeElement(enemy);
            }else {
                vcEnemy.elementAt(i).logic(mPlay,vcSoldier);
            }
        }
        for (int i = 0; i < vcSoldier.size(); i++) {//小兵逻辑
            Soldier soldier = vcSoldier.elementAt(i);
            if(soldier.isDead()){
                vcSoldier.removeElement(soldier);
            }else {
                vcSoldier.elementAt(i).logic(vcEnemy);
            }
        }
        for (int i = 0; i < vcBullet.size(); i++) {//子弹逻辑
            Bullet bullet = vcBullet.elementAt(i);
            if(bullet.isDead()){
                vcBullet.removeElement(bullet);
            }else {
                vcBullet.elementAt(i).logic();
            }
        }
    }

    @Override
    protected void onTouchMove(int id, float downRawX, float downRawY, float rawX, float rawY) {
        Soldier soldier = vcSoldier.elementAt(0);
        int angle = SurfaceViewUtil.getRotationBetweenLines((int)rawX, (int)rawY, (int)soldier.getmRect().centerX(), (int)soldier.getmRect().centerY());
        Log.e("Soldier","点击位置："+angle);
        soldier.setAngle(angle);
    }

    public SicktoSurfaceView(Context context) {
        super(context);
    }

    public SicktoSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SicktoSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
