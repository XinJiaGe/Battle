package com.jiage.battle.surface.aircraft2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.nfc.Tag;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.jiage.battle.R;
import com.jiage.battle.surface.BaseSurfaceView;
import com.jiage.battle.util.BitmapUtils;
import com.jiage.battle.util.OtherUtil;
import com.jiage.battle.util.SDTimerUtil;

import java.util.Vector;

/**
 * 作者：李忻佳
 * 日期：2019/4/9
 * 说明：
 */

public class AircraftSurface extends BaseSurfaceView {
    private SDTimerUtil timerUtil = new SDTimerUtil();
    private long time = 1;//时间
    private Vector<DrawBg> vcBg = new Vector<>();
    private Vector<Enemy> vcEnemy = new Vector<>();
    private Vector<Bullet> vcBullet = new Vector<>();
    private Vector<Supply> vcSupply = new Vector<>();
    private Bitmap bitmapBg;
    private Play play;
    private int downPlayerX, downPlayerY;
    private onSurfaceListener mListener;
    private boolean isBoos = false;//boos时间
    private int addEnemyTime = 20;//敌人出现时间
    private int addBoosTime = 10;//boos出现时间

    @Override
    public void created() {
        setBgColor(Color.parseColor("#040637"));
        addBgLogic();
        addEnemyLogic();
        play = new Play(mContext, mScreenW, mScreenH);
    }

    @Override
    public void myDraw() {
        if (time == 999999999)
            time = 1;
        time++;
        for (int i = 0; i < vcBg.size(); i++) {//绘制峡谷
            vcBg.elementAt(i).myDraw(mCanvas, mPaint);
        }
        for (int i = 0; i < vcSupply.size(); i++) {//绘制补给
            vcSupply.elementAt(i).myDraw(mCanvas, mPaint);
        }
        for (int i = 0; i < vcBullet.size(); i++) {//绘制子弹
            vcBullet.elementAt(i).myDraw(mCanvas, mPaint);
        }
        for (int i = 0; i < vcEnemy.size(); i++) {//绘制敌人
            vcEnemy.elementAt(i).myDraw(mCanvas, mPaint);
        }
        play.myDraw(mCanvas, mPaint);
    }

    @Override
    public void logic() {
        addEnemyLogic();
        for (int i = 0; i < vcBg.size(); i++) {//峡谷逻辑
            DrawBg bg = vcBg.elementAt(i);
            if (bg.isDead()) {
                vcBg.removeElement(bg);
            } else {
                bg.logic();
            }
        }
        for (int i = 0; i < vcBullet.size(); i++) {//子弹逻辑
            Bullet bullet = vcBullet.elementAt(i);
            if (bullet.isDead()) {
                vcBullet.removeElement(bullet);
            } else {
                bullet.logic(mContext, play, vcEnemy, vcSupply, mListener, new Enemy.onBoosInjuredListener() {
                    @Override
                    public void boosDeath() {
                        isBoos = false;
                    }
                });
            }
        }
        for (int i = 0; i < vcSupply.size(); i++) {//补给逻辑
            Supply supply = vcSupply.elementAt(i);
            if (supply.isDead()) {
                vcSupply.removeElement(supply);
            } else {
                supply.logic(play, vcEnemy,mListener);
            }
        }
        for (int i = 0; i < vcEnemy.size(); i++) {//敌人逻辑
            Enemy enemy = vcEnemy.elementAt(i);
            if (enemy.isDead()) {
                vcEnemy.removeElement(enemy);
            } else {
                enemy.logic(mContext, vcBullet, time, mScreenW, mScreenH);
            }
        }
        play.logic(mContext, vcBullet, vcEnemy, time, mScreenW, mScreenH,mListener);
    }

    /**
     * 定时添加敌人
     */
    private void addEnemyLogic() {
        if(!isBoos&&time%addEnemyTime == 0){
            Enemy.ENEMYTYPE type = Enemy.ENEMYTYPE.ENEMY1;
            int random = OtherUtil.getRandom(1, 3);
            if(random == 1){
                type = Enemy.ENEMYTYPE.ENEMY1;
            }
            if(random == 2){
                type = Enemy.ENEMYTYPE.ENEMY2;
            }
            if(random == 3){
                type = Enemy.ENEMYTYPE.ENEMY3;
            }
            vcEnemy.add(new Enemy(mContext, type, mScreenW, mScreenH));
        }
        if(!isBoos&&time%addBoosTime == 0){
            if(mListener!=null) mListener.BoosTime();
            Enemy.ENEMYTYPE type = Enemy.ENEMYTYPE.BOOS1;
            int random = OtherUtil.getRandom(1, 4);
            if(random == 1){
                type = Enemy.ENEMYTYPE.BOOS1;
            }
            if(random == 2){
                type = Enemy.ENEMYTYPE.BOOS2;
            }
            if(random == 3){
                type = Enemy.ENEMYTYPE.BOOS3;
            }
            if(random == 4){
                type = Enemy.ENEMYTYPE.BOOS4;
            }
            isBoos = true;
            final Enemy.ENEMYTYPE finalType = Enemy.ENEMYTYPE.BOOS1;
            timerUtil.startWork(2000, new SDTimerUtil.SDTimerListener() {
                @Override
                public void onWork() {

                }

                @Override
                public void onWorkMain() {
                    vcEnemy.add(new Enemy(mContext, finalType, mScreenW, mScreenH));
                }
            });
        }
    }

    /**
     * 添加背景移动
     */
    private void addBgLogic() {
        bitmapBg = BitmapUtils.ReadBitMap(mContext, R.drawable.icon_aircraftwars_xiagu);
        //把背景图大小设置为手机屏幕大小

        bitmapBg = BitmapUtils.getBitmap(mContext, bitmapBg, 1);
        SDTimerUtil timerUtil = new SDTimerUtil();
        addBg(timerUtil, 0);
    }

    /**
     * 随机背景出现时间
     *
     * @param timerUtil
     * @param time
     */
    private void addBg(final SDTimerUtil timerUtil, int time) {
        timerUtil.startWork(time, new SDTimerUtil.SDTimerListener() {
            @Override
            public void onWork() {
            }

            @Override
            public void onWorkMain() {
                vcBg.add(new DrawBg(bitmapBg, mScreenW, mScreenH));
                addBg(timerUtil, OtherUtil.getRandom(3000, 8000));
            }
        });
    }

    @Override
    protected void onTouchDown(int id, float rawX, float rawY) {
        downPlayerX = play.getX();
        downPlayerY = play.getY();
    }

    @Override
    protected void onTouchMove(int id, float downRawX, float downRawY, float rawX, float rawY) {
        int nexX = downPlayerX + (int) (rawX - downRawX);
        int nexY = downPlayerY + (int) (rawY - downRawY);
        if (nexX > 0 && nexX + play.getWidth() < mScreenW)
            play.setX(nexX);

        if (nexY > 0 && nexY + play.getHeight() < mScreenH)
            play.setY(nexY);
    }

    public void setmListener(onSurfaceListener listener) {
        this.mListener = listener;
    }

    public interface onSurfaceListener {
        void injured(int i);//受伤
        void addBlood(int i);//增加血量
        void addFraction(int i);//增加分数
        void BoosTime();//boos时间
    }

    public AircraftSurface(Context context) {
        super(context);
    }

    public AircraftSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AircraftSurface(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public Play getPlay() {
        return play;
    }
}
