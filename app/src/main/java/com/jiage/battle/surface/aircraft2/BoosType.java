package com.jiage.battle.surface.aircraft2;

import android.content.Context;
import android.graphics.Bitmap;

import com.jiage.battle.R;
import com.jiage.battle.util.BitmapUtils;
import com.jiage.battle.util.OtherUtil;
import com.jiage.battle.util.SurfaceViewUtil;

import java.util.Vector;

/**
 * 作者：李忻佳
 * 日期：2019/4/17
 * 说明：boos图片和模式
 */

public class BoosType {

    /**
     * 发射子弹间隔
     * @return
     */
    public static int interval(ENEMYTYPE enemytype, BOOSMODE modeRandom){
        switch (enemytype) {
            case ENEMY1: return 150;
            case ENEMY2: return 130;
            case ENEMY3: return 150;
            case BOOS1:
                switch (modeRandom) {
                    case MODE1: return 15;
                    case MODE2: return 15;
                    case MODE3: return 15;
                    case MODE4: return 3;
                    case MODE5: return 15;
                }
            case BOOS2:
                switch (modeRandom) {
                    case MODE1: return 80;
                    case MODE2: return 15;
                    case MODE3: return 15;
                    case MODE4: return 3;
                    case MODE5: return 15;
                }
            case BOOS3:
                switch (modeRandom) {
                    case MODE1: return 15;
                    case MODE2: return 15;
                    case MODE3: return 15;
                    case MODE4: return 3;
                    case MODE5: return 15;
                }
            case BOOS4:
                switch (modeRandom) {
                    case MODE1: return 15;
                    case MODE2: return 15;
                    case MODE3: return 15;
                    case MODE4: return 3;
                    case MODE5: return 15;
                }
        }
        return 0;
    }
    /**
     * boos 子弹模式
     * @param mContext
     * @param enemytype
     * @param modeRandom
     * @param vcBullet
     * @param x
     * @param y
     * @param bitmap
     * @param boosAngle
     * @param screenW
     * @param screenH
     * @return
     */
    public static int boosMode(Context mContext, ENEMYTYPE enemytype, BOOSMODE modeRandom, Vector<Bullet> vcBullet, int x, int y, Bitmap bitmap,
                               int boosAngle, int screenW, int screenH) {
        int angle = boosAngle;
        int speed = 15;
        switch (enemytype) {
            case BOOS1:
                switch (modeRandom) {
                    case MODE1:
                        vcBullet.add(new Bullet(mContext, Bullet.TYPE.ENEMY, enemytype, modeRandom, 1, Play.LEVEL.LEVEL1,
                                x + bitmap.getWidth() / 3, y + bitmap.getHeight(), speed,0, screenW, screenH));
                        vcBullet.add(new Bullet(mContext, Bullet.TYPE.ENEMY, enemytype, modeRandom, 1, Play.LEVEL.LEVEL1,
                                x + (bitmap.getWidth() - bitmap.getWidth() / 3), y + bitmap.getHeight(),speed, 0, screenW, screenH));
                        break;
                    case MODE2:
                        vcBullet.add(new Bullet(mContext, Bullet.TYPE.ENEMY, enemytype, modeRandom, 1, Play.LEVEL.LEVEL1,
                                x + bitmap.getWidth() / 3, y + bitmap.getHeight(), speed,30, screenW, screenH));
                        vcBullet.add(new Bullet(mContext, Bullet.TYPE.ENEMY, enemytype, modeRandom, 1, Play.LEVEL.LEVEL1,
                                x + bitmap.getWidth() / 3, y + bitmap.getHeight(), speed,0, screenW, screenH));
                        vcBullet.add(new Bullet(mContext, Bullet.TYPE.ENEMY, enemytype, modeRandom, 1, Play.LEVEL.LEVEL1,
                                x + bitmap.getWidth() / 3, y + bitmap.getHeight(), speed,330, screenW, screenH));
                        vcBullet.add(new Bullet(mContext, Bullet.TYPE.ENEMY, enemytype, modeRandom, 1, Play.LEVEL.LEVEL1,
                                x + (bitmap.getWidth() - bitmap.getWidth() / 3), y + bitmap.getHeight(), speed,30, screenW, screenH));
                        vcBullet.add(new Bullet(mContext, Bullet.TYPE.ENEMY, enemytype, modeRandom, 1, Play.LEVEL.LEVEL1,
                                x + (bitmap.getWidth() - bitmap.getWidth() / 3), y + bitmap.getHeight(),speed, 0, screenW, screenH));
                        vcBullet.add(new Bullet(mContext, Bullet.TYPE.ENEMY, enemytype, modeRandom, 1, Play.LEVEL.LEVEL1,
                                x + (bitmap.getWidth() - bitmap.getWidth() / 3), y + bitmap.getHeight(), speed,330, screenW, screenH));
                        break;
                    case MODE3:
                        for (int i = 0; i < 360; i += 18) {
                            vcBullet.add(new Bullet(mContext, Bullet.TYPE.ENEMY, enemytype, modeRandom, 1, Play.LEVEL.LEVEL1,
                                    x + bitmap.getWidth() / 2, y + bitmap.getHeight() / 2, speed,i, screenW, screenH));
                        }
                        break;
                    case MODE4:
                        vcBullet.add(new Bullet(mContext, Bullet.TYPE.ENEMY, enemytype, modeRandom, 1, Play.LEVEL.LEVEL1,
                                x + bitmap.getWidth() / 2, y + bitmap.getHeight() / 2, speed,boosAngle, screenW, screenH));
                        vcBullet.add(new Bullet(mContext, Bullet.TYPE.ENEMY, enemytype, modeRandom, 1, Play.LEVEL.LEVEL1,
                                x + bitmap.getWidth() / 2, y + bitmap.getHeight() / 2, speed,boosAngle + 90, screenW, screenH));
                        vcBullet.add(new Bullet(mContext, Bullet.TYPE.ENEMY, enemytype, modeRandom, 1, Play.LEVEL.LEVEL1,
                                x + bitmap.getWidth() / 2, y + bitmap.getHeight() / 2, speed,boosAngle + 180, screenW, screenH));
                        vcBullet.add(new Bullet(mContext, Bullet.TYPE.ENEMY, enemytype, modeRandom, 1, Play.LEVEL.LEVEL1,
                                x + bitmap.getWidth() / 2, y + bitmap.getHeight() / 2, speed,boosAngle + 270, screenW, screenH));
                        angle += 18;
                        break;
                    case MODE5:
                        for (int i = 300; i < 430; i += 15) {
                            vcBullet.add(new Bullet(mContext, Bullet.TYPE.ENEMY, enemytype, modeRandom, 1, Play.LEVEL.LEVEL1,
                                    x + bitmap.getWidth() / 2, y + bitmap.getHeight(),speed, i, screenW, screenH));
                        }
                        break;
                }
                break;
            case BOOS2:
                switch (modeRandom) {
                    case MODE1:
                        vcBullet.add(new Bullet(mContext, Bullet.TYPE.ENEMY, enemytype, modeRandom, 1, Play.LEVEL.LEVEL1,
                                x + bitmap.getWidth() / 3, y + bitmap.getHeight(), speed,0, screenW, screenH));
                        vcBullet.add(new Bullet(mContext, Bullet.TYPE.ENEMY, enemytype, modeRandom, 1, Play.LEVEL.LEVEL1,
                                x + (bitmap.getWidth() - bitmap.getWidth() / 3), y + bitmap.getHeight(),speed, 0, screenW, screenH));
                        break;
                    case MODE2:
                        vcBullet.add(new Bullet(mContext, Bullet.TYPE.ENEMY, enemytype, modeRandom, 1, Play.LEVEL.LEVEL1,
                                x + bitmap.getWidth() / 3, y + bitmap.getHeight(), speed,30, screenW, screenH));
                        vcBullet.add(new Bullet(mContext, Bullet.TYPE.ENEMY, enemytype, modeRandom, 1, Play.LEVEL.LEVEL1,
                                x + bitmap.getWidth() / 3, y + bitmap.getHeight(), speed,0, screenW, screenH));
                        vcBullet.add(new Bullet(mContext, Bullet.TYPE.ENEMY, enemytype, modeRandom, 1, Play.LEVEL.LEVEL1,
                                x + bitmap.getWidth() / 3, y + bitmap.getHeight(), speed,330, screenW, screenH));
                        vcBullet.add(new Bullet(mContext, Bullet.TYPE.ENEMY, enemytype, modeRandom, 1, Play.LEVEL.LEVEL1,
                                x + (bitmap.getWidth() - bitmap.getWidth() / 3), y + bitmap.getHeight(),speed, 30, screenW, screenH));
                        vcBullet.add(new Bullet(mContext, Bullet.TYPE.ENEMY, enemytype, modeRandom, 1, Play.LEVEL.LEVEL1,
                                x + (bitmap.getWidth() - bitmap.getWidth() / 3), y + bitmap.getHeight(), speed,0, screenW, screenH));
                        vcBullet.add(new Bullet(mContext, Bullet.TYPE.ENEMY, enemytype, modeRandom, 1, Play.LEVEL.LEVEL1,
                                x + (bitmap.getWidth() - bitmap.getWidth() / 3), y + bitmap.getHeight(), speed,330, screenW, screenH));
                        break;
                    case MODE3:
                        for (int i = 0; i < 360; i += 18) {
                            vcBullet.add(new Bullet(mContext, Bullet.TYPE.ENEMY, enemytype, modeRandom, 1, Play.LEVEL.LEVEL1,
                                    x + bitmap.getWidth() / 2, y + bitmap.getHeight() / 2,speed, i, screenW, screenH));
                        }
                        break;
                    case MODE4:
                        vcBullet.add(new Bullet(mContext, Bullet.TYPE.ENEMY, enemytype, modeRandom, 1, Play.LEVEL.LEVEL1,
                                x + bitmap.getWidth() / 2, y + bitmap.getHeight() / 2, speed,boosAngle, screenW, screenH));
                        vcBullet.add(new Bullet(mContext, Bullet.TYPE.ENEMY, enemytype, modeRandom, 1, Play.LEVEL.LEVEL1,
                                x + bitmap.getWidth() / 2, y + bitmap.getHeight() / 2, speed,boosAngle + 90, screenW, screenH));
                        vcBullet.add(new Bullet(mContext, Bullet.TYPE.ENEMY, enemytype, modeRandom, 1, Play.LEVEL.LEVEL1,
                                x + bitmap.getWidth() / 2, y + bitmap.getHeight() / 2, speed,boosAngle + 180, screenW, screenH));
                        vcBullet.add(new Bullet(mContext, Bullet.TYPE.ENEMY, enemytype, modeRandom, 1, Play.LEVEL.LEVEL1,
                                x + bitmap.getWidth() / 2, y + bitmap.getHeight() / 2, speed,boosAngle + 270, screenW, screenH));
                        angle += 18;
                        break;
                    case MODE5:
                        for (int i = 300; i < 430; i += 15) {
                            vcBullet.add(new Bullet(mContext, Bullet.TYPE.ENEMY, enemytype, modeRandom, 1, Play.LEVEL.LEVEL1,
                                    x + bitmap.getWidth() / 2, y + bitmap.getHeight(),speed, i, screenW, screenH));
                        }
                        break;
                }
                break;
            case BOOS3:
                switch (modeRandom) {
                    case MODE1:
                        vcBullet.add(new Bullet(mContext, Bullet.TYPE.ENEMY, enemytype, modeRandom, 1, Play.LEVEL.LEVEL1,
                                x + bitmap.getWidth() / 3, y + bitmap.getHeight(), speed,0, screenW, screenH));
                        vcBullet.add(new Bullet(mContext, Bullet.TYPE.ENEMY, enemytype, modeRandom, 1, Play.LEVEL.LEVEL1,
                                x + (bitmap.getWidth() - bitmap.getWidth() / 3), y + bitmap.getHeight(),speed, 0, screenW, screenH));
                        break;
                    case MODE2:
                        vcBullet.add(new Bullet(mContext, Bullet.TYPE.ENEMY, enemytype, modeRandom, 1, Play.LEVEL.LEVEL1,
                                x + bitmap.getWidth() / 3, y + bitmap.getHeight(), speed,30, screenW, screenH));
                        vcBullet.add(new Bullet(mContext, Bullet.TYPE.ENEMY, enemytype, modeRandom, 1, Play.LEVEL.LEVEL1,
                                x + bitmap.getWidth() / 3, y + bitmap.getHeight(), speed,0, screenW, screenH));
                        vcBullet.add(new Bullet(mContext, Bullet.TYPE.ENEMY, enemytype, modeRandom, 1, Play.LEVEL.LEVEL1,
                                x + bitmap.getWidth() / 3, y + bitmap.getHeight(), speed,330, screenW, screenH));
                        vcBullet.add(new Bullet(mContext, Bullet.TYPE.ENEMY, enemytype, modeRandom, 1, Play.LEVEL.LEVEL1,
                                x + (bitmap.getWidth() - bitmap.getWidth() / 3), y + bitmap.getHeight(),speed, 30, screenW, screenH));
                        vcBullet.add(new Bullet(mContext, Bullet.TYPE.ENEMY, enemytype, modeRandom, 1, Play.LEVEL.LEVEL1,
                                x + (bitmap.getWidth() - bitmap.getWidth() / 3), y + bitmap.getHeight(), speed,0, screenW, screenH));
                        vcBullet.add(new Bullet(mContext, Bullet.TYPE.ENEMY, enemytype, modeRandom, 1, Play.LEVEL.LEVEL1,
                                x + (bitmap.getWidth() - bitmap.getWidth() / 3), y + bitmap.getHeight(), speed,330, screenW, screenH));
                        break;
                    case MODE3:
                        for (int i = 0; i < 360; i += 18) {
                            vcBullet.add(new Bullet(mContext, Bullet.TYPE.ENEMY, enemytype, modeRandom, 1, Play.LEVEL.LEVEL1,
                                    x + bitmap.getWidth() / 2, y + bitmap.getHeight() / 2,speed, i, screenW, screenH));
                        }
                        break;
                    case MODE4:
                        vcBullet.add(new Bullet(mContext, Bullet.TYPE.ENEMY, enemytype, modeRandom, 1, Play.LEVEL.LEVEL1,
                                x + bitmap.getWidth() / 2, y + bitmap.getHeight() / 2, speed,boosAngle, screenW, screenH));
                        vcBullet.add(new Bullet(mContext, Bullet.TYPE.ENEMY, enemytype, modeRandom, 1, Play.LEVEL.LEVEL1,
                                x + bitmap.getWidth() / 2, y + bitmap.getHeight() / 2, speed,boosAngle + 90, screenW, screenH));
                        vcBullet.add(new Bullet(mContext, Bullet.TYPE.ENEMY, enemytype, modeRandom, 1, Play.LEVEL.LEVEL1,
                                x + bitmap.getWidth() / 2, y + bitmap.getHeight() / 2, speed,boosAngle + 180, screenW, screenH));
                        vcBullet.add(new Bullet(mContext, Bullet.TYPE.ENEMY, enemytype, modeRandom, 1, Play.LEVEL.LEVEL1,
                                x + bitmap.getWidth() / 2, y + bitmap.getHeight() / 2, speed,boosAngle + 270, screenW, screenH));
                        angle += 18;
                        break;
                    case MODE5:
                        for (int i = 300; i < 430; i += 15) {
                            vcBullet.add(new Bullet(mContext, Bullet.TYPE.ENEMY, enemytype, modeRandom, 1, Play.LEVEL.LEVEL1,
                                    x + bitmap.getWidth() / 2, y + bitmap.getHeight(),speed, i, screenW, screenH));
                        }
                        break;
                }
                break;
            case BOOS4:
                switch (modeRandom) {
                    case MODE1:
                        vcBullet.add(new Bullet(mContext, Bullet.TYPE.ENEMY, enemytype, modeRandom, 1, Play.LEVEL.LEVEL1,
                                x + bitmap.getWidth() / 3, y + bitmap.getHeight(), speed,0, screenW, screenH));
                        vcBullet.add(new Bullet(mContext, Bullet.TYPE.ENEMY, enemytype, modeRandom, 1, Play.LEVEL.LEVEL1,
                                x + (bitmap.getWidth() - bitmap.getWidth() / 3), y + bitmap.getHeight(),speed, 0, screenW, screenH));
                        break;
                    case MODE2:
                        vcBullet.add(new Bullet(mContext, Bullet.TYPE.ENEMY, enemytype, modeRandom, 1, Play.LEVEL.LEVEL1,
                                x + bitmap.getWidth() / 3, y + bitmap.getHeight(), speed,30, screenW, screenH));
                        vcBullet.add(new Bullet(mContext, Bullet.TYPE.ENEMY, enemytype, modeRandom, 1, Play.LEVEL.LEVEL1,
                                x + bitmap.getWidth() / 3, y + bitmap.getHeight(), speed,0, screenW, screenH));
                        vcBullet.add(new Bullet(mContext, Bullet.TYPE.ENEMY, enemytype, modeRandom, 1, Play.LEVEL.LEVEL1,
                                x + bitmap.getWidth() / 3, y + bitmap.getHeight(), speed,330, screenW, screenH));
                        vcBullet.add(new Bullet(mContext, Bullet.TYPE.ENEMY, enemytype, modeRandom, 1, Play.LEVEL.LEVEL1,
                                x + (bitmap.getWidth() - bitmap.getWidth() / 3), y + bitmap.getHeight(),speed, 30, screenW, screenH));
                        vcBullet.add(new Bullet(mContext, Bullet.TYPE.ENEMY, enemytype, modeRandom, 1, Play.LEVEL.LEVEL1,
                                x + (bitmap.getWidth() - bitmap.getWidth() / 3), y + bitmap.getHeight(), speed,0, screenW, screenH));
                        vcBullet.add(new Bullet(mContext, Bullet.TYPE.ENEMY, enemytype, modeRandom, 1, Play.LEVEL.LEVEL1,
                                x + (bitmap.getWidth() - bitmap.getWidth() / 3), y + bitmap.getHeight(), speed,330, screenW, screenH));
                        break;
                    case MODE3:
                        for (int i = 0; i < 360; i += 18) {
                            vcBullet.add(new Bullet(mContext, Bullet.TYPE.ENEMY, enemytype, modeRandom, 1, Play.LEVEL.LEVEL1,
                                    x + bitmap.getWidth() / 2, y + bitmap.getHeight() / 2,speed, i, screenW, screenH));
                        }
                        break;
                    case MODE4:
                        vcBullet.add(new Bullet(mContext, Bullet.TYPE.ENEMY, enemytype, modeRandom, 1, Play.LEVEL.LEVEL1,
                                x + bitmap.getWidth() / 2, y + bitmap.getHeight() / 2, speed,boosAngle, screenW, screenH));
                        vcBullet.add(new Bullet(mContext, Bullet.TYPE.ENEMY, enemytype, modeRandom, 1, Play.LEVEL.LEVEL1,
                                x + bitmap.getWidth() / 2, y + bitmap.getHeight() / 2, speed,boosAngle + 90, screenW, screenH));
                        vcBullet.add(new Bullet(mContext, Bullet.TYPE.ENEMY, enemytype, modeRandom, 1, Play.LEVEL.LEVEL1,
                                x + bitmap.getWidth() / 2, y + bitmap.getHeight() / 2, speed,boosAngle + 180, screenW, screenH));
                        vcBullet.add(new Bullet(mContext, Bullet.TYPE.ENEMY, enemytype, modeRandom, 1, Play.LEVEL.LEVEL1,
                                x + bitmap.getWidth() / 2, y + bitmap.getHeight() / 2, speed,boosAngle + 270, screenW, screenH));
                        angle += 18;
                        break;
                    case MODE5:
                        for (int i = 300; i < 430; i += 15) {
                            vcBullet.add(new Bullet(mContext, Bullet.TYPE.ENEMY, enemytype, modeRandom, 1, Play.LEVEL.LEVEL1,
                                    x + bitmap.getWidth() / 2, y + bitmap.getHeight(),speed, i, screenW, screenH));
                        }
                        break;
                }
                break;
        }
        return angle;
    }

    /**
     * 获取boos图片
     *
     * @param mContext
     * @param enemy
     * @return
     */
    public static Bitmap getBoosBitmap(Context mContext, ENEMYTYPE enemy) {
        switch (enemy) {
            case ENEMY1:
                return BitmapUtils.ReadBitMap(mContext, R.drawable.icon_aircraftwars_enemy1);
            case ENEMY2:
                return BitmapUtils.ReadBitMap(mContext, R.drawable.icon_aircraftwars_enemy2);
            case ENEMY3:
                return BitmapUtils.ReadBitMap(mContext, R.drawable.icon_aircraftwars_enemy3);
            case BOOS1:
                return BitmapUtils.ReadBitMap(mContext, R.drawable.icon_aircraftwars_boos1);
            case BOOS2:
                return BitmapUtils.ReadBitMap(mContext, R.drawable.icon_aircraftwars_boos2);
            case BOOS3:
                return BitmapUtils.ReadBitMap(mContext, R.drawable.icon_aircraftwars_boos3);
            case BOOS4:
                return BitmapUtils.ReadBitMap(mContext, R.drawable.icon_aircraftwars_boos4);
        }
        return BitmapUtils.ReadBitMap(mContext, R.drawable.icon_aircraftwars_enemy1);
    }

    /**
     * 获取boos子弹图片
     *
     * @param mContext
     * @param type
     * @param enemy
     * @param mode
     * @return
     */
    public static Bitmap getBoosBulletBitmap(Context mContext, Bullet.TYPE type, ENEMYTYPE enemy, BOOSMODE mode) {
        switch (type) {
            case PLAY:
                return BitmapUtils.ReadBitMap(mContext, R.drawable.icon_aircraftwars_bullet_play);
            case ENEMY:
                switch (enemy) {
                    case ENEMY1:
                        return BitmapUtils.ReadBitMap(mContext, R.drawable.icon_aircraftwars_enemy1_bullet);
                    case ENEMY2:
                        return BitmapUtils.ReadBitMap(mContext, R.drawable.icon_aircraftwars_enemy2_bullet);
                    case ENEMY3:
                        return BitmapUtils.ReadBitMap(mContext, R.drawable.icon_aircraftwars_enemy2_bullet);
                    case BOOS1:
                        switch (mode) {
                            case MODE1:
                                return BitmapUtils.ReadBitMap(mContext, R.drawable.icon_aircraftwars_enemy2_bullet);
                            case MODE2:
                                return BitmapUtils.ReadBitMap(mContext, R.drawable.icon_aircraftwars_enemy2_bullet);
                            case MODE3:
                                return BitmapUtils.ReadBitMap(mContext, R.drawable.icon_aircraftwars_bullet2);
                            case MODE4:
                                return BitmapUtils.ReadBitMap(mContext, R.drawable.icon_aircraftwars_bullet2);
                            case MODE5:
                                return BitmapUtils.ReadBitMap(mContext, R.drawable.icon_aircraftwars_bullet14);
                        }
                        break;
                    case BOOS2:
                        switch (mode) {
                            case MODE1:
                                return SurfaceViewUtil.rotateBitmap(BitmapUtils.ReadBitMap(mContext, R.drawable.icon_aircraftwars_bullet11),0);
                            case MODE2:
                                return SurfaceViewUtil.rotateBitmap(BitmapUtils.ReadBitMap(mContext, R.drawable.icon_aircraftwars_bullet7), 0);
                            case MODE3:
                                return BitmapUtils.ReadBitMap(mContext, R.drawable.icon_aircraftwars_bullet1);
                            case MODE4:
                                return BitmapUtils.ReadBitMap(mContext, R.drawable.icon_aircraftwars_bullet1);
                            case MODE5:
                                return BitmapUtils.ReadBitMap(mContext, R.drawable.icon_aircraftwars_bullet1);
                        }
                        break;
                    case BOOS3:
                        switch (mode) {
                            case MODE1:
                                return SurfaceViewUtil.rotateBitmap(BitmapUtils.ReadBitMap(mContext, R.drawable.icon_aircraftwars_bullet12), 0);
                            case MODE2:
                                return BitmapUtils.ReadBitMap(mContext, R.drawable.icon_aircraftwars_bullet8);
                            case MODE3:
                                return BitmapUtils.ReadBitMap(mContext, R.drawable.icon_aircraftwars_bullet8);
                            case MODE4:
                                return BitmapUtils.ReadBitMap(mContext, R.drawable.icon_aircraftwars_bullet8);
                            case MODE5:
                                return BitmapUtils.ReadBitMap(mContext, R.drawable.icon_aircraftwars_bullet8);
                        }
                        break;
                    case BOOS4:
                        switch (mode) {
                            case MODE1:
                                return SurfaceViewUtil.rotateBitmap(BitmapUtils.ReadBitMap(mContext, R.drawable.icon_aircraftwars_bullet4), 0);
                            case MODE2:
                                return BitmapUtils.ReadBitMap(mContext, R.drawable.icon_aircraftwars_bullet2);
                            case MODE3:
                                return BitmapUtils.ReadBitMap(mContext, R.drawable.icon_aircraftwars_bullet2);
                            case MODE4:
                                return BitmapUtils.ReadBitMap(mContext, R.drawable.icon_aircraftwars_bullet2);
                            case MODE5:
                                return BitmapUtils.ReadBitMap(mContext, R.drawable.icon_aircraftwars_bullet2);
                        }
                        break;
                }
                break;
        }
        return BitmapUtils.ReadBitMap(mContext, R.drawable.icon_aircraftwars_enemy2_bullet);
    }

    /**
     * 获取boos攻击模式
     * @return
     */
    public static BOOSMODE getBoosModeRandom() {
        int random = OtherUtil.getRandom(1, 5);
        if (random == 1) {
            return BOOSMODE.MODE1;
        }
        if (random == 2) {
            return BOOSMODE.MODE2;
        }
        if (random == 3) {
            return BOOSMODE.MODE3;
        }
        if (random == 4) {
            return BOOSMODE.MODE4;
        }
        if (random == 5) {
            return BOOSMODE.MODE5;
        }
        return BOOSMODE.MODE1;
    }

    /**
     * 敌机
     */
    public enum ENEMYTYPE {
        ENEMY1,//敌机1
        ENEMY2,//敌机2
        ENEMY3, //敌机3
        BOOS1,//boos1
        BOOS2,//boos2
        BOOS3,//boos3
        BOOS4 //boos4
    }

    /**
     * boos攻击方式
     */
    public enum BOOSMODE {
        MODE1,
        MODE2,
        MODE3,
        MODE4,
        MODE5
    }
}
