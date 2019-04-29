package com.jiage.battle.surface.aircraft2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.jiage.battle.R;
import com.jiage.battle.util.BitmapUtils;

/**
 * 作者：李忻佳
 * 日期：2018/11/26
 * 说明：主角
 */

public class DrawBg {
    private Bitmap bitmap;
    private int x, y, mScreenW, mScreenH;
    public static int speed = 7; //速度
    private boolean isDead = false;

    public DrawBg(Bitmap bitmapBg, int screenW, int screenH) {
        this.bitmap = bitmapBg;
        this.mScreenW = screenW;
        this.mScreenH = screenH;
        this.y = -bitmap.getHeight();
    }

    public void myDraw(Canvas mCanvas, Paint mPaint) {
        mCanvas.drawBitmap(bitmap, x, y, mPaint);
    }

    public void logic() {
        y += speed;
        if(y > mScreenH){
            isDead = true;
        }
    }

    public boolean isDead() {
        return isDead;
    }
}
