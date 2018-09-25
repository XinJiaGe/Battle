package com.jiage.battle.surface.snake;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.jiage.battle.util.SurfaceViewUtil;

import java.util.Vector;

/**
 * 作者：李忻佳
 * 日期：2018/9/13
 * 说明：蛇集合
 */

public class Snake {
    private int x,y;
    private int w=20,h=20;//宽高
    private boolean isHead;//是否是头部
    private Rect rect;
    private int seep = 20;
    private int color;
    private String address;

    public Snake(int x, int y, boolean head,int color,String address){
        this.isHead = head;
        this.color = color;
        this.address = address;
        if(head) {
            this.x = is20(x);
            this.y = is20(y);
        }
    }

    public void draw(Canvas canvas, Paint paint, int i) {
        paint.setColor(color);
        rect = new Rect(x,y,x+w,y+h);
        canvas.drawRect(rect,paint);
    }

    public void logic() {
    }

    /**
     * 设置为20的倍数
     * @param random
     * @return
     */
    private int is20(int random){
        boolean isWhere = true;
        while (isWhere) {
            if(random >100){
                if(random%20==0){
                    isWhere = false;
                    return random;
                }else
                    random--;
            }else{
                if(random%20==0){
                    isWhere = false;
                    return random;
                }else
                    random++;
            }
        }
        return -20;
    }



    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Rect getRect() {
        return rect;
    }

    public String getAddress() {
        return address;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSeep() {
        return seep;
    }

    public void setHead(boolean head) {
        isHead = head;
    }
}
