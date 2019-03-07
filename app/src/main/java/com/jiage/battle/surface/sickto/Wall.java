package com.jiage.battle.surface.sickto;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * 作者：李忻佳
 * 日期：2018/12/26
 * 说明：墙
 */

public class Wall {
    private int x,y;
    private boolean isDead;// 优化处理

    public Wall(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void draw(Canvas canvas , Paint paint) {

    }

    public void logic() {

    }

    public boolean isDead() {
        return isDead;
    }
}
