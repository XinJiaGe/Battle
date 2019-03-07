package com.jiage.battle.surface.defense;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * 作者：李忻佳
 * 日期：2018/12/18
 * 说明：
 */

public class Map {
    private int x,y,w,i,j,column,row,postion,text,index;
    private boolean build =false;

    /**
     * @param x
     * @param y
     * @param w  宽度
     * @param i
     * @param j
     * @param column  列
     * @param row     行
     * @param text
     */
    public Map(int x, int y,int w,int i,int j,int column,int row ,int postion, int text){
        this.x = x;
        this.y = y;
        this.w = w;
        this.i = i;
        this.j = j;
        this.column = column;
        this.row = row;
        this.postion = postion;
        this.text = text;
    }

    public void draw(Canvas canvas, Paint paint) {
        switch (text) {
            case -1:
                paint.setColor(Color.GRAY);
                break;
            case 0:
                paint.setColor(Color.WHITE);
                break;
            case 1:
                paint.setColor(Color.GRAY);
                break;
            case 2:
                paint.setColor(Color.BLUE);
                break;
            case 3:
                paint.setColor(Color.YELLOW);
                break;
            case 4:
                paint.setColor(Color.RED);
                break;
        }
        canvas.drawRect(new Rect(x,y,x+w,y+w),paint);
        canvas.drawText(text+"",x+w/2,y+w/2,paint);
    }

    public void setIndex(int index) {
        this.index += index;
    }

    public int getIndex() {
        return index;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getW() {
        return w;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public int getText() {
        return text;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public int getPostion() {
        return postion;
    }
}
