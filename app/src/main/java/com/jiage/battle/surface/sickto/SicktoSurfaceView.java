package com.jiage.battle.surface.sickto;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.AttributeSet;

import com.jiage.battle.R;
import com.jiage.battle.dialog.SDDialogConfirm;
import com.jiage.battle.surface.BaseSurfaceView;
import com.jiage.battle.util.BitmapUtils;
import com.jiage.battle.util.SDTimerUtil;
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
    private int soldierLenght = 1;
    private Bitmap[] soldiers = new Bitmap[3];
    private Bitmap soldier1,soldier2,soldier3,zidna,enemy1;
    private int money;
    private onMListener mListener;
    private long time;

    @Override
    public void created() {
        mPlay = new Play(mScreenW,mScreenH);
        soldier1 = BitmapUtils.ReadBitMap(mContext, R.drawable.soldier2);
        soldier2 = BitmapUtils.ReadBitMap(mContext, R.drawable.soldier1);
        soldier3 = BitmapUtils.ReadBitMap(mContext, R.drawable.soldier3);
        zidna = BitmapUtils.ReadBitMap(mContext, R.drawable.icon_bullet2);
        enemy1 = BitmapUtils.ReadBitMap(mContext, R.drawable.bullet_skin_05);
        soldiers[0] = soldier1;
        soldiers[1] = soldier2;
        soldiers[2] = soldier3;

        vcSoldier.add(new Soldier(soldiers,mScreenW/2-100,mScreenH/2-100,mScreenW,mScreenH, Soldier.Grade.ONE,zidna));

        SDTimerUtil timerUtil = new SDTimerUtil();
        timerUtil.startWork(500, 500, new SDTimerUtil.SDTimerListener() {
            @Override
            public void onWork() {

            }

            @Override
            public void onWorkMain() {
                vcEnemy.add(new Enemy(enemy1,mScreenW,mScreenH,time));
            }
        });
        SDTimerUtil timer = new SDTimerUtil();
        timer.startWork(1000, 10000, new SDTimerUtil.SDTimerListener() {
            @Override
            public void onWork() {

            }

            @Override
            public void onWorkMain() {
                time ++ ;
            }
        });
    }

    @Override
    public void myDraw() {
        mPlay.draw(mCanvas,mPaint);//绘制主角
        for (int i = 0; i < vcBullet.size(); i++) {//绘制子弹
            vcBullet.elementAt(i).draw(mCanvas, mPaint);
        }
        for (int i = 0; i < vcSoldier.size(); i++) {//绘制小兵
            vcSoldier.elementAt(i).draw(mCanvas, mPaint);
        }
        for (int i = 0; i < vcEnemy.size(); i++) {//绘制敌人
            vcEnemy.elementAt(i).draw(mCanvas, mPaint);
        }
        for (int i = 0; i < vcWall.size(); i++) {//绘制墙
            vcWall.elementAt(i).draw(mCanvas, mPaint);
        }

        mPaint.setColor(Color.BLUE);
        mPaint.setTextSize(20);
        mCanvas.drawText("金币"+money,10,100,mPaint);
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
                vcSoldier.elementAt(i).logic(vcEnemy,vcBullet);
            }
        }
        for (int i = 0; i < vcBullet.size(); i++) {//子弹逻辑
            Bullet bullet = vcBullet.elementAt(i);
            if(bullet.isDead()){
                vcBullet.removeElement(bullet);
            }else {
                int siMoney = vcBullet.elementAt(i).logic(vcEnemy,mScreenW,mScreenH);
                if(siMoney!=0) money += siMoney;
            }
        }
    }

    @Override
    protected void onTouchMove(int id, float downRawX, float downRawY, float rawX, float rawY) {
    }

    @Override
    protected void onTouchDown(int id, float rawX, float rawY) {
        for (int i = 0; i < vcSoldier.size(); i++) {
            Soldier soldier = vcSoldier.elementAt(i);
            if(SurfaceViewUtil.isCollsionClick(soldier.getmRect(),rawX,rawY)) {
                if (mListener != null)
                    mListener.showDialog(soldier);
                break;
            }
        }
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
    public interface onMListener{
        void showDialog(Soldier soldier);
    }
    public void setmListener(onMListener listener){
        this.mListener = listener;
    }

    /**
     * 添加小兵
     */
    public void addSoldier(){
        if(money>=100&&soldierLenght<4) {
            if (soldierLenght == 1)
                vcSoldier.add(new Soldier(soldiers, mScreenW / 2 + 100, mScreenH / 2 - 100, mScreenW, mScreenH, Soldier.Grade.ONE, zidna));
            if (soldierLenght == 2)
                vcSoldier.add(new Soldier(soldiers, mScreenW / 2 + 100, mScreenH / 2 + 100, mScreenW, mScreenH, Soldier.Grade.ONE, zidna));
            if (soldierLenght == 3)
                vcSoldier.add(new Soldier(soldiers, mScreenW / 2 - 100, mScreenH / 2 + 100, mScreenW, mScreenH, Soldier.Grade.ONE, zidna));
            soldierLenght++;
            money -= 100;
        }
    }

    /**
     * 升级
     * @return
     * @param soldier
     */
    public void upgrade(Soldier soldier) {
        if(soldier.getGrade() == Soldier.Grade.ONE&&money>=500){
            soldier.setGrade(Soldier.Grade.TWO);
            money -= 500;
        }
        if(soldier.getGrade() == Soldier.Grade.TWO&&money>=1000){
            soldier.setGrade(Soldier.Grade.THREE);
            money -= 1000;
        }
    }
}
