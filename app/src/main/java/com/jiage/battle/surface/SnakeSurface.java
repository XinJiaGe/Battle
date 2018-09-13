package com.jiage.battle.surface;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import com.jiage.battle.surface.snake.RectangleKeyboard;
import com.jiage.battle.surface.snake.Snake;

import java.util.Vector;

/**
 * 作者：李忻佳
 * 日期：2018/9/13
 * 说明：贪吃蛇Surface
 */

public class SnakeSurface extends BaseSurfaceView {
    private RectangleKeyboard rectangleKeyboard;
    private Vector<Snake> vcSnake;
    private boolean suspend = true; //是否暂停
    private long time;

    @Override
    public void created() {
        rectangleKeyboard = new RectangleKeyboard(mScreenW / 2 - 250, mScreenH - 450, 500, 400);
        vcSnake = new Vector<>();
        vcSnake.add(new Snake(mScreenW/2,(mScreenH - 500)/2,true,0,rectangleKeyboard));
        vcSnake.add(new Snake(-20,-20,false,1,null));
        vcSnake.add(new Snake(-20,-20,false,2,null));
        vcSnake.add(new Snake(-20,-20,false,3,null));
    }

    @Override
    public void myDraw() {
        time += 1;
        rectangleKeyboard.draw(mCanvas, mPaint);

        for (int i = 0; i < vcSnake.size(); i++) {//绘制蛇
            vcSnake.elementAt(i).draw(mCanvas,mPaint);
        }

        mPaint.setColor(Color.BLACK);
        mCanvas.drawLine(0, mScreenH - 500, mScreenW, mScreenH - 500, mPaint);
    }

    @Override
    public void logic() {
        if(!suspend&&time%10==0) {
            int x=0,y=0;
            for (int i = 0; i < vcSnake.size(); i++) {//蛇逻辑
                vcSnake.elementAt(i).logic(x,y);
                x = vcSnake.elementAt(i).getX();
                y = vcSnake.elementAt(i).getY();
            }
        }
        if(time >= 10000 )
            time = 0;
    }

    @Override
    protected void onTouchDown(int id, float rawX, float rawY) {
        rectangleKeyboard.setClickXY(rawX, rawY);
    }

    @Override
    protected void onTouchUp(int id, float rawX, float rawY) {
        rectangleKeyboard.setClickXY(0, 0);
    }

    public SnakeSurface(Context context) {
        super(context);
    }

    public SnakeSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SnakeSurface(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setSuspend(boolean suspend) {
        this.suspend = suspend;
    }
}
