package com.jiage.battle.surface.snake;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * 作者：李忻佳
 * 日期：2018/9/13
 * 说明：蛇集合
 */

public class Snake implements RectangleKeyboard.onClickListener {
    private RectangleKeyboard rectangleKeyboard;
    private int x,y;
    private int w=20,h=20;//宽高
    private boolean isHead;//是否是头部
    private Rect rect;
    private int seep = 20;
    private int index;
    private RectangleKeyboard.Direction direction = RectangleKeyboard.Direction.TOP;

    public Snake(int x, int y, boolean head, int index, RectangleKeyboard rectangleKeyboard){
        this.x = x;
        this.y = y;
        this.isHead = head;
        this.index = index;
        this.rectangleKeyboard = rectangleKeyboard;
        if(rectangleKeyboard!=null)
            rectangleKeyboard.setClickDirectionListener(this);
    }

    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(Color.BLACK);
        rect = new Rect(x,y,x+w,y+h);
        canvas.drawRect(rect,paint);
    }

    public void logic(int nexx, int nexy) {
        if(!isHead){
            x = nexx;
            y = nexy;
        }
        switch (direction) {
            case TOP:
                y -= seep;
                break;
            case LEFT:
                x -= seep;
                break;
            case RIGHT:
                x += seep;
                break;
            case BOTTOM:
                y += seep;
                break;
        }
    }

    @Override
    public void clickDirection(RectangleKeyboard.Direction direction) {
        this.direction = direction;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
