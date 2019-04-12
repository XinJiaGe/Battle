package com.jiage.battle.surface.aircraft2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.jiage.battle.R;
import com.jiage.battle.constant.ApkConstant;
import com.jiage.battle.util.BitmapUtils;
import com.jiage.battle.util.OtherUtil;
import com.jiage.battle.util.SurfaceViewUtil;

import java.util.Vector;

/**
 * 作者：李忻佳
 * 日期：2019/4/10
 * 说明：补给
 */

public class Supply {
    private Rect rect = new Rect(0,0,0,0);
    private Bitmap bitmap;
    private Bitmap bitmapBg;
    private int x,y,mScreenW,mScreenH;
    private int speed = 8; //速度
    private boolean isDead = false;
    private TYPE type = TYPE.BLOOD;


    public Supply(Context context, Bitmap bitmap, TYPE type, Rect rect, int screenW, int screenH){
        this.mScreenW = screenW;
        this.mScreenH = screenH;
        this.bitmap = bitmap;
        this.type = type;
        bitmapBg = BitmapUtils.ReadBitMap(context, R.drawable.icon_aircraftwars_supply_bg);
        x = rect.centerX();
        y = rect.centerY();
    }
    public void myDraw(Canvas mCanvas, Paint mPaint){
        mCanvas.drawBitmap(bitmapBg,x-bitmapBg.getWidth()/2,y-bitmapBg.getHeight()/2,mPaint);
        mCanvas.drawBitmap(bitmap,x-bitmap.getWidth()/2,y-bitmap.getHeight()/2,mPaint);

        rect = new Rect(x-bitmapBg.getWidth()/4,y-bitmapBg.getHeight()/4,x+bitmapBg.getWidth()/4,y+bitmapBg.getHeight()/4);

        if(ApkConstant.isDebug) {
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setColor(Color.RED);
            mCanvas.drawRect(rect, mPaint);
        }
    }
    public void logic(Play play, Vector<Enemy> vcEnemy, AircraftSurface.onSurfaceListener mListener) {
        y += speed;
        if(y>mScreenH){
            isDead = true;
        }

        if(SurfaceViewUtil.isCollsionWithRect(rect,play.getRect())){//和主角碰撞了
            isDead = true;
            switch (type) {
                case BLOOD:
                    if(mListener!=null) mListener.addBlood(1);
                    break;
                case BOOM:
                    for (int i = 0; i < vcEnemy.size(); i++) {//敌人逻辑
                        Enemy enemy = vcEnemy.elementAt(i);
                        if(!enemy.isBoos()) {
                            enemy.setDead(true);
                            if (mListener != null) mListener.addFraction(enemy.getFraction());
                        }
                    }
                    break;
                case PROTECTION:
                    play.addProtectionCover();
                    break;
                case ARMS:
                    play.upgrade();
                    break;
            }
        }
    }
    enum TYPE{
        BLOOD,//加血
        BOOM,//炸弹
        PROTECTION,//防护罩
        ARMS//升级武器
    }

    public boolean isDead() {
        return isDead;
    }
}
