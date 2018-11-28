package com.jiage.battle.surface.aircraft;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.AttributeSet;

import com.jiage.battle.R;
import com.jiage.battle.surface.BaseSurfaceView;
import com.jiage.battle.util.BitmapUtils;
import com.jiage.battle.util.OtherUtil;
import com.jiage.battle.util.SDTimerUtil;
import com.jiage.battle.util.SDViewUtil;
import com.jiage.battle.util.SurfaceViewUtil;

import java.util.Vector;

/**
 * 作者：李忻佳
 * 日期：2018/11/26
 * 说明：
 */

public class AircraftSurface extends BaseSurfaceView {
    private DrawBackground backg;
    private Play play;
    private int downPlayerX, downPlayerY;
    private Vector<Enemy> vcEnemy;
    private Vector<UFO> vcUFO;
    private Vector<Bullet> vcBullet;
    private SDTimerUtil timerUtil = new SDTimerUtil();
    private long frame;//帧
    private long time;//时间
    private boolean isBoosTime = false;
    private PLAYERGRADE playerGrade = PLAYERGRADE.INIT;//主角子弹级别
    private Bitmap bloodBottom;
    private int checkpoint = 1;
    private onLenter mOnLenter;

    @Override
    public void created() {
        //加载血量图片
        bloodBottom = BitmapUtils.ReadBitMap(mContext, R.drawable.icon_bullet2);
        backg = new DrawBackground(mContext);
        play = new Play(mContext);
        vcEnemy = new Vector<>();
        vcBullet = new Vector<>();
        vcUFO = new Vector<>();
        startTime();
    }

    @Override
    public void myDraw() {
        if (frame == 999999999)
            frame = 0;
        frame++;
//        backg.myDraw(mCanvas, mPaint);//绘制背景
        for (int i = 0; i < vcBullet.size(); i++) {//绘制子弹
            vcBullet.elementAt(i).myDraw(mCanvas, mPaint);
        }
        for (int i = 0; i < vcEnemy.size(); i++) {//绘制敌人
            vcEnemy.elementAt(i).myDraw(mCanvas, mPaint);
        }
        for (int i = 0; i < vcUFO.size(); i++) {//绘制UFO
            vcUFO.elementAt(i).myDraw(mCanvas, mPaint);
        }
        play.myDraw(mCanvas, mPaint,time);//绘制主角

        //绘制主角生命值
        for (int i = 0; i < play.getBlood(); i++) {
            mCanvas.drawBitmap(bloodBottom, 10+i*15, 100, mPaint);
        }
        //绘制关卡
        mPaint.setColor(Color.BLUE);
        mPaint.setTextSize(20);
        mCanvas.drawText("第"+checkpoint+"关",10,150,mPaint);
        //绘制boos血量
        if(isBoosTime) {
            mPaint.setColor(Color.RED);
            for (int i = 0; i < vcEnemy.size(); i++) {
                Enemy enemy = vcEnemy.elementAt(i);
                if(enemy.getmType() == Enemy.TYPE.BOOS){
                    mCanvas.drawRect(new Rect(0, 0, mScreenW *enemy.getBlood()/enemy.getMaxBlood(),25),mPaint);
                    mPaint.setColor(Color.BLACK);
                    mPaint.setTextSize(20);
                    mCanvas.drawText(enemy.getBlood()+"",mScreenW/2-100,20,mPaint);
                }
            }
        }
    }

    @Override
    public void logic() {
//        backg.logic();//处理背景逻辑
        for (int i = 0; i < vcEnemy.size(); i++) {//处理敌人逻辑
            Enemy enemy = vcEnemy.elementAt(i);
            if (enemy.isDead())
                vcEnemy.removeElement(enemy);
            else {
                if(!play.isInvincible()&&play.getRect()!=null&&enemy.getRect()!=null&&SurfaceViewUtil.isCollsionWithRect(play.getRect(),enemy.getRect())){//敌人是否与主角碰撞
                    if(enemy.getmType() != Enemy.TYPE.BOOS)
                        vcEnemy.removeElement(enemy);
                    play.injured(mOnLenter);
                    downgradePlayerGrade();
                }else
                    vcEnemy.elementAt(i).logic(vcBullet, frame, play);
            }
        }
        for (int i = 0; i < vcBullet.size(); i++) {//处理子弹逻辑
            Bullet bullet = vcBullet.elementAt(i);
            if (bullet.isDead())
                vcBullet.removeElement(bullet);
            else {
                boolean isDead = false;
                Rect rect = new Rect(bullet.getX() - bullet.getRadius(), bullet.getY() - bullet.getRadius(), bullet.getX() + bullet.getRadius(), bullet.getY() + bullet.getRadius());
                switch (bullet.getmType()) {
                    case PLAYER:
                        for (int j = 0; j < vcEnemy.size(); j++) {
                            Enemy enemy = vcEnemy.elementAt(j);
                            if (enemy.getRect() != null && SurfaceViewUtil.isCollsionWithRect(enemy.getRect(), rect)) {//主角子弹是否与敌人碰撞
                                isDead = true;
                                enemy.injured(mContext,vcUFO,enemy,bullet,new Enemy.onDeath() {
                                    @Override
                                    public void death(Enemy.TYPE type) {
                                        switch (type) {
                                            case BOOS://boos死亡
                                                time = 0;
                                                isBoosTime = false;
                                                checkpoint ++;
                                                startTime();
                                                break;
                                        }
                                    }
                                });
                                break;
                            }
                        }
                        break;
                    default:
                        if (!play.isInvincible()&&play.getRect() != null && SurfaceViewUtil.isCollsionWithRect(play.getRect(), rect)) {//敌人子弹是否与主角碰撞
                            isDead = true;
                            play.injured(mOnLenter);
                            downgradePlayerGrade();
                        }
                        break;
                }
                if(isDead)
                    vcBullet.removeElement(bullet);
                else
                    vcBullet.elementAt(i).logic();
            }
        }
        for (int i = 0; i < vcUFO.size(); i++) {//处理ufo逻辑
            UFO ufo = vcUFO.elementAt(i);
            if(ufo.isDead())
                vcUFO.removeElement(ufo);
            else {
                if(play.getRect() != null&&ufo.getRect() != null&&SurfaceViewUtil.isCollsionWithRect(play.getRect(), ufo.getRect())){//主角是与UFO碰撞
                    vcUFO.removeElement(ufo);
                    switch (ufo.getmType()) {
                        case BULLETUPGRADE:
                            upgradePlayerGrade();
                            break;
                        case ADDBLOOD:
                            play.addBlood();
                            break;
                    }
                }else
                    vcUFO.elementAt(i).logic();
            }
        }
        play.logic(vcBullet, frame, playerGrade);//处理主角逻辑
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
        if (nexX > 0 && nexX + play.getmHero().getWidth() < SDViewUtil.getScreenWidth())
            play.setX(nexX);

        if (nexY > 0 && nexY + play.getmHero().getHeight() < SDViewUtil.getScreenHeight())
            play.setY(nexY);

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

    /**
     * 主角子弹级别
     */
    enum PLAYERGRADE {
        INIT,
        level1,
        level2,
        level3,
        level4,
        level5,
        level6
    }

    /**
     * 升级子弹
     */
    public void upgradePlayerGrade(){
        switch (playerGrade) {
            case INIT:
                playerGrade = PLAYERGRADE.level1;
                break;
            case level6:
                playerGrade = PLAYERGRADE.level6;
                break;
            case level5:
                playerGrade = PLAYERGRADE.level6;
                break;
            case level4:
                playerGrade = PLAYERGRADE.level5;
                break;
            case level3:
                playerGrade = PLAYERGRADE.level4;
                break;
            case level2:
                playerGrade = PLAYERGRADE.level3;
                break;
            case level1:
                playerGrade = PLAYERGRADE.level2;
                break;
        }
    }
    /**
     * 降级子弹
     */
    public void downgradePlayerGrade(){
        switch (playerGrade) {
            case INIT:
                playerGrade = PLAYERGRADE.INIT;
                break;
            case level6:
                playerGrade = PLAYERGRADE.level5;
                break;
            case level5:
                playerGrade = PLAYERGRADE.level4;
                break;
            case level4:
                playerGrade = PLAYERGRADE.level3;
                break;
            case level3:
                playerGrade = PLAYERGRADE.level2;
                break;
            case level2:
                playerGrade = PLAYERGRADE.level1;
                break;
            case level1:
                playerGrade = PLAYERGRADE.INIT;
                break;
        }
    }

    private void startTime(){
        timerUtil.startWork(0, 1000, new SDTimerUtil.SDTimerListener() {
            @Override
            public void onWork() {}
            @Override
            public void onWorkMain() {
                if(time == 20) {
                    isBoosTime = true;
                    vcEnemy.add(new Enemy(mContext, Enemy.TYPE.BOOS, checkpoint));
                }else{
                    if(!isBoosTime) {
                        int random = OtherUtil.getRandom(1, 2);
                        if (random == 1)
                            vcEnemy.add(new Enemy(mContext, Enemy.TYPE.ENEMY1,checkpoint));
                        else
                            vcEnemy.add(new Enemy(mContext, Enemy.TYPE.ENEMY2, checkpoint));
                    }
                }
                time ++;
            }
        });
    }

    public interface onLenter {
        void gameOver();//结束游戏
        void victory();//胜利
    }
    public void setOnLenter(onLenter lenter) {
        this.mOnLenter = lenter;
    }
    public void setSuspend(boolean suspend) {
        isStop = suspend;
    }
}
