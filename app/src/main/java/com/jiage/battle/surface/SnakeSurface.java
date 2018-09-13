package com.jiage.battle.surface;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.AttributeSet;

import com.jiage.battle.surface.snake.Food;
import com.jiage.battle.surface.snake.RectangleKeyboard;
import com.jiage.battle.surface.snake.Snake;
import com.jiage.battle.util.SurfaceViewUtil;

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
    private Food food;

    @Override
    public void created() {
        food = new Food(mScreenW,mScreenH);
        rectangleKeyboard = new RectangleKeyboard(mScreenW / 2 - 250, mScreenH - 450, 500, 400);
        vcSnake = new Vector<>();
        vcSnake.add(new Snake(mScreenW/2,(mScreenH - 500)/2,true,RectangleKeyboard.Direction.TOP,rectangleKeyboard));

    }

    @Override
    public void myDraw() {
        time += 1;

        rectangleKeyboard.draw(mCanvas, mPaint);
        food.draw(mCanvas,mPaint);
        for (int i = 0; i < vcSnake.size(); i++) {//绘制蛇
            vcSnake.elementAt(i).draw(mCanvas,mPaint);
        }

        mPaint.setColor(Color.BLACK);
        mCanvas.drawLine(0, mScreenH - 500, mScreenW, mScreenH - 500, mPaint);
    }

    @Override
    public void logic() {
        if(!suspend&&time%5==0) {
//            for (int i = 0; i < vcSnake.size(); i++) {//蛇逻辑
//                if(i == vcSnake.size()-1) {
//                    Snake snake = vcSnake.elementAt(i);
//                    snake.logic(vcSnake, food);
//                }
//            }
            Snake snake1 = vcSnake.elementAt(0);
            Rect torect = toRect(snake1);
            if(SurfaceViewUtil.isCollsionWithRect(torect,food.getRect())){
                int foodx = food.getFoodx();
                int foody = food.getFoody();
                food.setFoodx(-20);
                food.UpdataFood();
                vcSnake.add(0,new Snake(foodx,foody,true,snake1.getDirection(), rectangleKeyboard));
                snake1.setHead(false);
                snake1.setRectangleKeyboard(null);
            }else{
                Snake snake2 = vcSnake.elementAt(vcSnake.size()-1);
                snake2.setX(torect.left);
                snake2.setY(torect.top);
            }
        }

        if(time >= 10000 )
            time = 0;
    }

    @Override
    protected void onTouchDown(int id, float rawX, float rawY) {
        rectangleKeyboard.setClickXY(rawX, rawY);
    }
    /**
     * 获取下一个矩形位置
     * @return
     * @param snake
     */
    private Rect toRect(Snake snake){
        Rect snakeRect = snake.getRect();
        Rect rect = new Rect();
        switch (snake.getDirection()) {
            case TOP:
//                rect.top = snakeRect.top - snake.getSeep();
//                rect.bottom = snakeRect.bottom-snake.getSeep();
                rect.set(snakeRect.left,snakeRect.top-snake.getSeep(),snakeRect.right,snakeRect.bottom-snake.getSeep());
                break;
            case LEFT:
//                rect.left = snakeRect.left-snake.getSeep();
//                rect.right = snakeRect.right-snake.getSeep();
                rect.set(snakeRect.left-snake.getSeep(),snakeRect.top,snakeRect.right-snake.getSeep(),snakeRect.bottom);
                break;
            case RIGHT:
//                rect.right = snakeRect.right+snake.getSeep();
//                rect.left = snakeRect.left+snake.getSeep();
                rect.set(snakeRect.left+snake.getSeep(),snakeRect.top,snakeRect.right+snake.getSeep(),snakeRect.bottom);
                break;
            case BOTTOM:
//                rect.top = snakeRect.top + snake.getSeep();
//                rect.bottom = snakeRect.bottom+snake.getSeep();
                rect.set(snakeRect.left,snakeRect.top+snake.getSeep(),snakeRect.right,snakeRect.bottom+snake.getSeep());
                break;
        }
        return rect;
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
