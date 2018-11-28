package com.jiage.battle.surface.aircraft;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.jiage.battle.R;
import com.jiage.battle.util.BitmapUtils;
import com.jiage.battle.util.SDViewUtil;

/**
 * 作者：李忻佳
 * 日期：2018/11/28
 * 说明：UFO
 */

public class UFO {
    private Rect rect;
    private int height;
    private TYPE mType;
    private Bitmap ufo;
    private int x,y;
    private int seep = 4;
    private boolean isDead = false;


    public UFO(Context context,int x, int y,TYPE type){
        switch (type) {
            case ADDBLOOD:
                this.ufo = BitmapUtils.ReadBitMap(context, R.drawable.ufo1);
                break;
            case BULLETUPGRADE:
                this.ufo = BitmapUtils.ReadBitMap(context, R.drawable.ufo2);
                break;
        }
        this.height = SDViewUtil.getScreenHeight();
        this.mType = type;
        this.x = x;
        this.y = y;
        this.rect = new Rect(x,y,x+ufo.getWidth(),y+ufo.getHeight());
    }
    public void myDraw(Canvas mCanvas, Paint mPaint) {
        mCanvas.drawBitmap(ufo, x, y, mPaint);
        rect = new Rect(x,y,x+ufo.getWidth(),y+ufo.getHeight());
    }

    public void logic(){
        if (y>height) {
            isDead = true;
        }
        y += seep;
    }
    enum TYPE{
        BULLETUPGRADE,
        ADDBLOOD
    }
    public boolean isDead() {
        return isDead;
    }

    public Rect getRect() {
        return rect;
    }

    public TYPE getmType() {
        return mType;
    }
}
