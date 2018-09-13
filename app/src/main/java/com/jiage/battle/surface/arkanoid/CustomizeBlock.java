package com.jiage.battle.surface.arkanoid;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region;

import com.jiage.battle.constant.Constant;
import com.jiage.battle.util.SurfaceViewUtil;

/**
 * 作者：李忻佳
 * 日期：2018/8/15
 * 说明：方块
 */

public class CustomizeBlock {
    private int mI;//方块血量
    private int mSize;//一行方块有多少个
    private int mScreenW,mScreenH;
    private int mInitX,mInitY,width,height,mGao;
    private Rect rect;

    public CustomizeBlock(int i, int screenW, int screenH, int size, int initX, int initY, int gao) {
        mI = i;
        mScreenW = screenW;
        mScreenH = screenH;
        mInitX = initX;
        mInitY = initY;
        mSize = size;
        mGao = gao;
    }

    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(Constant.Block.getColor(mI));
        rect = new Rect(mInitX,mInitY,mInitX+Constant.Block.getWidth(mScreenW,mSize),mInitY+mGao);
        canvas.drawRect(rect,paint);
        if(mI == 0)
            paint.setColor(Color.BLACK);
        else
            paint.setColor(Color.WHITE);
        canvas.drawLine(rect.left,rect.top,rect.right,rect.top,paint);
        canvas.drawLine(rect.right,rect.top,rect.right,rect.bottom,paint);
        canvas.drawLine(rect.right,rect.bottom,rect.left,rect.bottom,paint);
        canvas.drawLine(rect.left,rect.bottom,rect.left,rect.top,paint);
        width = rect.width();
        height = rect.height();
    }
    public void logic(float touchX, float touchY,int code) {
        if(rect!=null) {
            if (SurfaceViewUtil.isCollsionClick(new Region(rect), touchX, touchY)) {
                mI = code;
            }
        }
    }

    public Rect getRect() {
        return rect;
    }

    public int getmInitX() {
        return mInitX;
    }

    public void setmInitX(int mInitX) {
        this.mInitX = mInitX;
    }

    public int getmInitY() {
        return mInitY;
    }

    public void setmInitY(int mInitY) {
        this.mInitY = mInitY;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getmI() {
        return mI;
    }
}
