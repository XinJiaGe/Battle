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

public class Snake implements RectangleKeyboard.onClickListener {
    private RectangleKeyboard rectangleKeyboard;
    private int x,y;
    private int w=20,h=20;//宽高
    private boolean isHead;//是否是头部
    private Rect rect;
    private int seep = 20;
    private RectangleKeyboard.Direction direction;

    public Snake(int x, int y, boolean head,RectangleKeyboard.Direction direction, RectangleKeyboard rectangleKeyboard){
        this.isHead = head;
        this.rectangleKeyboard = rectangleKeyboard;
        this.direction = direction;
        if(head) {
            this.x = is20(x);
            this.y = is20(y);
        }
        if(rectangleKeyboard!=null)
            rectangleKeyboard.setClickDirectionListener(this);
    }

    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(Color.BLACK);
        rect = new Rect(x,y,x+w,y+h);
        canvas.drawRect(rect,paint);
    }

    public boolean logic(Vector<Snake> snake, Food food) {
        boolean isP = false;
//        Rect torect = toRect();
//        if(torect==null)
//            return isP;
//        if(isHead&&rect!=null&&SurfaceViewUtil.isCollsionWithRect(torect,food.getRect())) {
//            snake.add(new Snake(torect.left,torect.top,isHead,direction, rectangleKeyboard));
//            isHead = false;
//            rectangleKeyboard = null;
//            food.setFoodx(-20);
//        }else {
//            snake.elementAt(snake.size() - 1).setX(torect.left);
//            snake.elementAt(snake.size() - 1).setY(torect.top);
//        }
//        if(rect!=null&&SurfaceViewUtil.isCollsionWithRect(toRect(),food.getRect())) {
//            isHead = false;
//            rectangleKeyboard = null;
//            isP = true;
//        }else {
//            if(size==1) {
//                switch (direction) {
//                    case TOP:
//                        y -= seep;
//                        break;
//                    case LEFT:
//                        x -= seep;
//                        break;
//                    case RIGHT:
//                        x += seep;
//                        break;
//                    case BOTTOM:
//                        y += seep;
//                        break;
//                }
//            }
//        }
//        if(food.getFoodx()<0)
//            food.UpdataFood();
        return isP;
    }

    @Override
    public void clickDirection(RectangleKeyboard.Direction direction) {
        this.direction = direction;
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

    public RectangleKeyboard.Direction getDirection() {
        return direction;
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

    public void setRectangleKeyboard(RectangleKeyboard rectangleKeyboard) {
        this.rectangleKeyboard = rectangleKeyboard;
    }

    public void setHead(boolean head) {
        isHead = head;
    }
}
