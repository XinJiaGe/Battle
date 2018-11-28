package com.jiage.battle.surface.aircraft;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.jiage.battle.R;
import com.jiage.battle.util.BitmapUtils;
import com.jiage.battle.util.OtherUtil;
import com.jiage.battle.util.SDViewUtil;
import com.jiage.battle.util.SurfaceViewUtil;

import java.util.Vector;

/**
 * 作者：李忻佳
 * 日期：2018/11/26
 * 说明：敌人
 */

public class Enemy {
    private Bitmap enemy1,enemy2,enemy3n1,enemy3n2;
    private TYPE mType;
    private POSITION position;
    private int record;
    private int seep = 6;
    private int x, y;
    private int width, height;
    private double index = 1;
    private boolean out = true;
    private int stop = 0;
    private int max = 0;
    private int blood = 1;//血量
    private int maxBlood = 1;//总血量血量
    private boolean isDead = false;
    private Rect rect;
    private BOOSBULLETMODE boosbulletmode = BOOSBULLETMODE.MODE1;
    private int progressively = 5;

    public Enemy(Context context, TYPE type, int checkpoint) {
        this.mType = type;
        this.width = SDViewUtil.getScreenWidth();
        this.height = SDViewUtil.getScreenHeight();
        switch (mType) {
            case ENEMY1:
                blood = 2*checkpoint;
                maxBlood = 2*checkpoint;
                enemy1 = BitmapUtils.ReadBitMap(context, R.drawable.enemy1);
                int random = OtherUtil.getRandom(1, 3);
                switch (random) {
                    case 1:
                        position = POSITION.POSITION1;
                        x = -enemy1.getWidth();
                        y = OtherUtil.getRandom(100, height / 2);
                        max = OtherUtil.getRandom(50, width - 50);
                        break;
                    case 2:
                        position = POSITION.POSITION2;
                        x = OtherUtil.getRandom(50, width - 50);
                        y = -enemy1.getHeight();
                        max = OtherUtil.getRandom(50, height / 2);
                        break;
                    case 3:
                        position = POSITION.POSITION3;
                        x = width;
                        y = OtherUtil.getRandom(100, height / 2);
                        max = OtherUtil.getRandom(50, width - 50);
                        break;
                }
                break;
            case ENEMY2:
                blood = 5*checkpoint;
                maxBlood = 5*checkpoint;
                enemy2 = BitmapUtils.ReadBitMap(context, R.drawable.enemy2);
                position = POSITION.POSITION2;
                x = OtherUtil.getRandom(100, width - 100);
                y = -enemy2.getHeight();
                max = OtherUtil.getRandom(100, height / 2);
                break;
            case BOOS:
                blood = 100*checkpoint;
                maxBlood = 100*checkpoint;
                enemy3n1 = BitmapUtils.ReadBitMap(context, R.drawable.enemy3_n1);
                enemy3n2 = BitmapUtils.ReadBitMap(context, R.drawable.enemy3_n2);
                position = POSITION.POSITION2;
                x = width / 2 - enemy3n1.getWidth() / 2;
                y = -enemy3n1.getHeight();
                seep = 4;
                break;
        }
    }

    public void myDraw(Canvas mCanvas, Paint mPaint) {
        switch (mType) {
            case ENEMY1:
                mCanvas.drawBitmap(enemy1, x, y, mPaint);
                rect = new Rect (x + enemy1.getWidth() / 2 - 15, y, x + enemy1.getWidth() / 2 + 15, y + enemy1.getHeight() - 10);
                break;
            case ENEMY2:
                mCanvas.drawBitmap(enemy2, x, y, mPaint);
                rect = new Rect (x + enemy2.getWidth() / 2 - 25, y, x + enemy2.getWidth() / 2 + 25, y + enemy2.getHeight() - 20);
                break;
            case BOOS:
                if (index % 2 == 0) {
                    mCanvas.drawBitmap(enemy3n1, x, y, mPaint);
                } else {
                    mCanvas.drawBitmap(enemy3n2, x, y, mPaint);
                }
                rect = new Rect (x + 10, y + 50, x + enemy3n2.getWidth() - 10, y + enemy3n2.getHeight() - 10);
                break;
        }
//        mPaint.setColor(Color.RED);
//        mCanvas.drawRect(rect, mPaint);
    }

    public void logic(Vector<Bullet> vcBullet, long time, Play play) {
        record++;
        switch (position) {
            case POSITION1:
                if (out) {
                    if (x > max) {
                        if (stop == 0)
                            stop = record + 100;
                        if (record == stop) {
                            out = false;
                        } else {
                            if (mType == TYPE.ENEMY1)
                                addBullet(vcBullet, time, play, enemy1);
                            else
                                addBullet(vcBullet, time, play, enemy2);
                        }
                    } else {
                        x += seep;
                    }
                } else {
                    x -= seep;
                    if (x < -enemy1.getWidth()) {
                        isDead = true;
                    }
                }
                break;
            case POSITION2:
                if (mType == TYPE.BOOS) {
                    index += 0.5;
                    if (index > 100) {
                        index = 1;
                    }
                    if (y > 50) {
                        if (out) {
                            x += seep;
                            if (x > width - enemy3n1.getWidth() - 50) out = false;
                        } else {
                            x -= seep;
                            if (x < 50) out = true;
                        }
                        addBulletBoss(vcBullet, time, enemy3n1, play);
                    } else {
                        y += seep;
                    }
                } else {
                    if (out) {
                        if (y > max) {
                            if (stop == 0)
                                stop = record + 100;
                            if (record == stop) {
                                out = false;
                                if (mType == TYPE.ENEMY2)
                                    addBulletCircle(vcBullet, enemy2);
                            } else {
                                if (mType == TYPE.ENEMY1)
                                    addBullet(vcBullet, time, play, enemy1);
                                else
                                    addBullet(vcBullet, time, play, enemy2);
                            }
                        } else {
                            y += seep;
                        }
                    } else {
                        y -= seep;
                        if (mType == TYPE.ENEMY1) {
                            if (y < -enemy1.getHeight()) {
                                isDead = true;
                            }
                        } else {
                            if (y < -enemy2.getHeight()) {
                                isDead = true;
                            }
                        }
                    }
                }
                break;
            case POSITION3:
                if (out) {
                    if (x < max) {
                        if (stop == 0)
                            stop = record + 100;
                        if (record == stop) {
                            out = false;
                        } else {
                            if (mType == TYPE.ENEMY1)
                                addBullet(vcBullet, time, play, enemy1);
                            else
                                addBullet(vcBullet, time, play, enemy2);
                        }
                    } else {
                        x -= seep;
                    }
                } else {
                    x += seep;
                    if (x > width) {
                        isDead = true;
                    }
                }
                break;
        }
    }

    /**
     * 添加boos攻击子弹
     *
     * @param vcBullet
     * @param time
     * @param bitmap
     * @param play
     */
    private void addBulletBoss(Vector<Bullet> vcBullet, long time, Bitmap bitmap, Play play) {
        switch (boosbulletmode) {
            case MODE1:
                if(time%8 == 0) {
                    for (int i = -40; i < 5; i+=20) {
                        vcBullet.add(new Bullet(Bullet.TYPE.BOOS,  x + bitmap.getWidth() / 2 + i, y + bitmap.getHeight() / 2, 0));
                    }
                    randomBoosbulletmode();
                }
                break;
            case MODE2:
                if(time%5 == 0) {
                    for (int i = 0; i < 90; i+=9) {
                        vcBullet.add(new Bullet(Bullet.TYPE.ENEMY,x+bitmap.getWidth()/2, y+bitmap.getHeight()/2,i-45));
                    }
                    randomBoosbulletmode();
                }
                break;
            case MODE3:
                if(time%15==0) {
                    int angle = SurfaceViewUtil.getRotationAngle(x,y,play.getX()+play.getmHero().getWidth()/2,play.getY()+play.getmHero().getHeight()/2);
                    vcBullet.add(new Bullet(Bullet.TYPE.ENEMY,x+bitmap.getWidth()/2, y+bitmap.getHeight()/2,angle));
                    randomBoosbulletmode();
                }
                break;
        }
    }

    /**
     * 随机boos攻击模式
     */
    private void randomBoosbulletmode() {
        progressively--;
        if (progressively == 0) {
            int random = OtherUtil.getRandom(1, 3);
            switch (random) {
                case 1:
                    progressively = OtherUtil.getRandom(5, 10);
                    boosbulletmode = BOOSBULLETMODE.MODE1;
                    break;
                case 2:
                    progressively = OtherUtil.getRandom(5, 20);
                    boosbulletmode = BOOSBULLETMODE.MODE2;
                    break;
                case 3:
                    progressively = 5;
                    boosbulletmode = BOOSBULLETMODE.MODE3;
                    break;
            }
        }
    }

    /**
     * 添加一个子弹
     *
     * @param vcBullet
     * @param time
     * @param play
     * @param bitmap
     */
    private void addBullet(Vector<Bullet> vcBullet, long time, Play play, Bitmap bitmap) {
        if(time%15==0) {
            int angle = SurfaceViewUtil.getRotationAngle(x,y,play.getX()+play.getmHero().getWidth()/2,play.getY()+play.getmHero().getHeight()/2);
            vcBullet.add(new Bullet(Bullet.TYPE.ENEMY,x+bitmap.getWidth()/2, y+bitmap.getHeight()/2,angle));
        }
    }

    /**
     * 添加一圈子弹
     *
     * @param vcBullet
     * @param bitmap
     */
    private void addBulletCircle(Vector<Bullet> vcBullet, Bitmap bitmap) {
        for (int i = 0; i < 360; i+=18) {
            vcBullet.add(new Bullet(Bullet.TYPE.ENEMY,x+bitmap.getWidth()/2, y+bitmap.getHeight()/2,i));
        }
    }

    /**
     * 受伤
     */
    public void injured(Context mContext, Vector<UFO> vcUFO, Enemy enemy, Bullet bullet, onDeath death){
        blood -= bullet.getHurt();
        if(blood<1){
            isDead = true;
            if(enemy.getmType() == TYPE.BOOS)
                vcUFO.add(new UFO(mContext,enemy.getX(),enemy.getY(), UFO.TYPE.BULLETUPGRADE));
            else
                if(OtherUtil.getRandom(1,5) == 1){
                    vcUFO.add(new UFO(mContext,enemy.getX(),enemy.getY(), UFO.TYPE.BULLETUPGRADE));
                }else if(OtherUtil.getRandom(1,10) == 1){
                    vcUFO.add(new UFO(mContext,enemy.getX(),enemy.getY(), UFO.TYPE.ADDBLOOD));
                }
            if(death!=null) death.death(mType);
        }
    }

    /**
     * 敌机种类
     */
    enum TYPE {
        ENEMY1,
        ENEMY2,
        BOOS
    }

    /**
     * 方位
     */
    enum POSITION {
        POSITION1,
        POSITION2,
        POSITION3,
    }

    /**
     * boos 子弹模式
     */
    enum BOOSBULLETMODE {
        MODE1,
        MODE2,
        MODE3
    }

    interface onDeath{
        void death(TYPE type);
    }

    public boolean isDead() {
        return isDead;
    }

    public Rect  getRect() {
        return rect;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public TYPE getmType() {
        return mType;
    }

    public int getBlood() {
        return blood;
    }

    public int getMaxBlood() {
        return maxBlood;
    }
}
