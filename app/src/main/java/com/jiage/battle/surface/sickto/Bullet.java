package com.jiage.battle.surface.sickto;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * 作者：李忻佳
 * 日期：2018/12/26
 * 说明：子弹
 */

public class Bullet {
    private Bitmap mBtmap;
    private int x,y;
    public boolean isDead;// 优化处理

    public Bullet(Bitmap bullet,int x, int y){
        this.mBtmap = bullet;
        this.x = x;
        this.y = y;
    }

    public void draw(Canvas canvas , Paint paint) {
        canvas.save();
        canvas.clipRect(0,0,mBtmap.getWidth()/4,mBtmap.getHeight());
        canvas.drawBitmap(mBtmap, x, y,paint);
        canvas.restore();
    }

    public void logic() {

    }

    public boolean isDead() {
        return isDead;
    }
}
