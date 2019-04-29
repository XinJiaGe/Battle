package com.jiage.battle.surface.aircraft2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import com.jiage.battle.R;
import com.jiage.battle.constant.ApkConstant;
import com.jiage.battle.util.BitmapUtils;
import com.jiage.battle.util.OtherUtil;
import com.jiage.battle.util.SurfaceViewUtil;

import java.util.Vector;

/**
 * 作者：李忻佳
 * 日期：2018/11/26
 * 说明：主角
 */

public class Bullet {
    private TYPE type = TYPE.PLAY;
    private BoosType.ENEMYTYPE enemy = BoosType.ENEMYTYPE.ENEMY1;
    private BoosType.BOOSMODE mode = BoosType.BOOSMODE.MODE1;
    private Bitmap bitmap;
    private Play.LEVEL level = Play.LEVEL.LEVEL1;
    private int x,y;
    private int i,mScreenW,mScreenH;
    private int speed; //速度
    private int angle = 0;//角度
    private int deviationDistance; //子弹偏移距离
    private int deviationSpeed=5; //子弹偏移速度
    private boolean isDeviation = false;//子弹是否偏移了
    private boolean isDead = false;
    private int aggressivity = 1;//攻击力
    private Rect rect = new Rect(0,0,0,0);
    private int probabilityBlood = 10;//加血几率
    private int probabilityBoom = 8;//炸弹几率
    private int probabilityProtection = 15;//防护罩几率
    private int probabilityArms = 20;//升级武器几率

    public Bullet(Context mContext, TYPE type, BoosType.ENEMYTYPE enemy, BoosType.BOOSMODE mode, int i, Play.LEVEL level, int x, int y,int speed,
                  int angle, int screenW, int screenH) {
        bitmap = BoosType.getBoosBulletBitmap(mContext, type, enemy, mode);
        switch (type) {
            case PLAY:
                aggressivity = 1;
                deviationDistance = bitmap.getWidth()+bitmap.getWidth()/2;
                break;
            case ENEMY:
                switch (enemy) {
                    case ENEMY1:
                        aggressivity = 1;
                        break;
                    case ENEMY2:
                        aggressivity = 1;
                        break;
                    case ENEMY3:
                        aggressivity = 1;
                        break;
                    case BOOS1:
                        aggressivity = 1;
                        break;
                    case BOOS2:
                        aggressivity = 1;
                        break;
                    case BOOS3:
                        aggressivity = 1;
                        break;
                    case BOOS4:
                        aggressivity = 1;
                        break;
                }
                break;
        }
        this.x = x-bitmap.getWidth()/2;
        this.y = y;
        this.i = i;
        this.speed = speed;
        this.angle = angle;
        this.enemy = enemy;
        this.level = level;
        this.mode = mode;
        this.mScreenW = screenW;
        this.mScreenH = screenH;
        this.type = type;
    }

    public void myDraw(Canvas mCanvas, Paint mPaint) {
        mCanvas.drawBitmap(bitmap,x,y,mPaint);

        rect = new Rect(x,y,x+bitmap.getWidth(),y+bitmap.getHeight());

        if(ApkConstant.isDebug) {
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setColor(Color.RED);
            mCanvas.drawRect(rect, mPaint);
        }
    }

    public void logic(final Context mContext, Play play, final Vector<Enemy> vcEnemy, final Vector<Supply> vcSupply, final AircraftSurface.onSurfaceListener mListener,
                      final Enemy.onBoosInjuredListener listener) {
        switch (type) {
            case PLAY:
                y -= speed;
                if(!isDeviation) {
                    switch (level) {
                        case LEVEL1:
                            isDeviation = true;
                            break;
                        case LEVEL2:
                            if (i == 2) {x += deviationSpeed;}
                            if (i == 3) {x -= deviationSpeed;}
                            break;
                        case LEVEL3:
                            if (i == 1) {x -= deviationSpeed;}
                            if (i == 3) {x += deviationSpeed;}
                            if (i == 4) {x -= deviationSpeed;}
                            if (i == 6) {x += deviationSpeed;}
                            break;
                    }
                    deviationDistance -= deviationSpeed;
                    if(deviationDistance < 1) isDeviation = true;
                }
                break;
            case ENEMY:
                switch (enemy) {
                    case ENEMY1:
                        y += speed;
                        break;
                    case ENEMY2:
                        y += speed;
                        break;
                    case ENEMY3:
                        y += speed;
                        break;
                    case BOOS1:
                        x = (int) SurfaceViewUtil.getCircleCoordinatesX(x, angle, speed);
                        y = (int) SurfaceViewUtil.getCircleCoordinatesY(y, angle, speed);
                        break;
                    case BOOS2:
                        x = (int) SurfaceViewUtil.getCircleCoordinatesX(x, angle, speed);
                        y = (int) SurfaceViewUtil.getCircleCoordinatesY(y, angle, speed);
                        break;
                    case BOOS3:
                        x = (int) SurfaceViewUtil.getCircleCoordinatesX(x, angle, speed);
                        y = (int) SurfaceViewUtil.getCircleCoordinatesY(y, angle, speed);
                        break;
                    case BOOS4:
                        x = (int) SurfaceViewUtil.getCircleCoordinatesX(x, angle, speed);
                        y = (int) SurfaceViewUtil.getCircleCoordinatesY(y, angle, speed);
                        break;
                }
                break;
        }
        switch (type) {
            case PLAY:
                for (int i = 0; i < vcEnemy.size(); i++) {
                    final Enemy enemy = vcEnemy.elementAt(i);
                    if(SurfaceViewUtil.isCollsionWithRect(rect,enemy.getRect())){//主角子弹和敌人碰撞了
                        isDead = true;
                        enemy.Injured(aggressivity, new Enemy.onInjuredListener() {
                            @Override
                            public void death() {//死亡
                                vcEnemy.removeElement(enemy);
                                addSupply(vcSupply,mContext,enemy,mScreenW,mScreenH);
                                if(mListener!=null) mListener.addFraction(enemy.getFraction());
                                if(listener!=null&&enemy.isBoos()) listener.boosDeath();
                            }
                        });
                    }
                }
                break;
            default:
                if(play.isProtectionCover()&&SurfaceViewUtil.isCollsionWithRectToCircle(true,play.getRect().centerX(),play.getRect().centerY(),
                        play.getProtectionCoverWidth(),rect.left,rect.top,rect.right,rect.bottom)){//敌人子弹和主角防护罩碰撞了
                    isDead = true;
                }else if(!play.isInvincible()&&SurfaceViewUtil.isCollsionWithRect(rect,play.getRect())){//敌人子弹和主角碰撞了
                    isDead = true;
                    play.downgrade();
                    if(mListener!=null) mListener.injured(aggressivity);
                }
                break;
        }

        if(y<-bitmap.getHeight()||y> this.mScreenH||x<-bitmap.getWidth()||x>mScreenW){
            isDead = true;
        }
    }

    enum TYPE{
        PLAY,//主角子弹
        ENEMY//敌人子弹
    }

    /**
     * 添加掉落物
     * @param vcSupply
     * @param context
     * @param enemy
     * @param screenW
     * @param screenH
     */
    private void addSupply(Vector<Supply> vcSupply, Context context, Enemy enemy, int screenW, int screenH){
        int random = OtherUtil.getRandom(1, 4);
        Bitmap bitmap = null;
        Supply.TYPE type = Supply.TYPE.BLOOD
                ;
        boolean isAdd = false;
        int randomAdd = OtherUtil.getRandom(1, 100);
        if(random == 1) {
            if(randomAdd<=probabilityBlood) {
                bitmap = BitmapUtils.ReadBitMap(context, R.drawable.icon_aircraftwars_supply_blood);
                type = Supply.TYPE.BLOOD;
                isAdd = true;
            }
        }else if(random == 2) {
            if(randomAdd<=probabilityBoom) {
                bitmap = BitmapUtils.ReadBitMap(context, R.drawable.icon_aircraftwars_supply_boom);
                type = Supply.TYPE.BOOM;
                isAdd = true;
            }
        }else if(random == 3) {
            if(randomAdd<=probabilityProtection) {
                bitmap = BitmapUtils.ReadBitMap(context, R.drawable.icon_aircraftwars_supply_protect);
                type = Supply.TYPE.PROTECTION;
                isAdd = true;
            }
        }else{
            if(randomAdd<=probabilityArms) {
                bitmap = BitmapUtils.ReadBitMap(context, R.drawable.icon_aircraftwars_supply_arms);
                type = Supply.TYPE.ARMS;
                isAdd = true;
            }
        }
        if(isAdd)
            vcSupply.add(new Supply(context,bitmap,type,enemy.getRect(),screenW,screenH));
    }

    public boolean isDead() {
        return isDead;
    }
}
