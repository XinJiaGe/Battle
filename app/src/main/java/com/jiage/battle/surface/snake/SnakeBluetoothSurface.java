package com.jiage.battle.surface.snake;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.AttributeSet;

import com.jiage.battle.surface.BaseSurfaceView;
import com.jiage.battle.util.SurfaceViewUtil;

import java.util.Vector;

/**
 * 作者：李忻佳
 * 日期：2018/9/13
 * 说明：贪吃蛇Surface
 */

public class SnakeBluetoothSurface extends BaseSurfaceView implements RectangleKeyboard.onClickListener {
    private RectangleKeyboard rectangleKeyboard;
    private Vector<Snake> vcSnakeMy;
    private Vector<Snake> vcSnaketo;
    private boolean suspend = true; //是否暂停
    private long time;
    private RectangleKeyboard.Direction DIRECTION = RectangleKeyboard.Direction.TOP;
    private onListener mOnListener;
    private Vector<Food> vcFood;

    @Override
    public void created() {
        rectangleKeyboard = new RectangleKeyboard(mScreenW / 2 - 250, mScreenH - 450, 500, 400);
        vcSnakeMy = new Vector<>();
        vcFood = new Vector<>();
        vcSnaketo = new Vector<>();
        vcSnakeMy.add(new Snake(mScreenW/3,(mScreenH - 500)/2,true,Color.BLACK));
        rectangleKeyboard.setClickDirectionListener(this);
    }

    @Override
    public void myDraw() {
        time += 1;

        rectangleKeyboard.draw(mCanvas, mPaint);
        for (int i = 0; i < vcFood.size(); i++) {//绘制食物
            vcFood.elementAt(i).draw(mCanvas,mPaint);
        }
        for (int i = 0; i < vcSnakeMy.size(); i++) {//绘制蛇
            vcSnakeMy.elementAt(i).draw(mCanvas,mPaint,i);
        }
        for (int i = 0; i < vcSnaketo.size(); i++) {//绘制蛇
            vcSnaketo.elementAt(i).draw(mCanvas,mPaint,i);
        }

        mPaint.setColor(Color.BLACK);
        mCanvas.drawLine(0, mScreenH - 500, mScreenW, mScreenH - 500, mPaint);
    }

    @Override
    public void logic() {
        if(!suspend&&time%5==0) {
            //蛇逻辑
            Snake snake1 = vcSnakeMy.firstElement();
            snake1.setHead(false);
            Rect torect = toRect(snake1);

            if(torect.left<0||torect.top<0||torect.right>mScreenW||torect.bottom>mScreenH - 500){//超出界限
                if(mOnListener!=null)
                    mOnListener.gameOver();
                return;
            }
            for (int i = 0; i < vcSnakeMy.size(); i++) {//与自己身体相撞
                Snake snake = vcSnakeMy.elementAt(i);
                if(SurfaceViewUtil.isCollsionBumpRect(torect,snake.getRect())){
                    if(mOnListener!=null)
                        mOnListener.gameOver();
                    return;
                }
            }
            for (int i = 0; i < vcFood.size(); i++) {//吃到食物
                Food food = vcFood.elementAt(i);
                if(SurfaceViewUtil.isCollsionBumpRect(snake1.getRect(),food.getRect())){
                    food.setFoodx(-20);
                    food.UpdataFood();
                    vcSnakeMy.insertElementAt(new Snake(torect.left,torect.top,true,Color.BLACK),0);
                    if(mOnListener!=null)
                        mOnListener.fraction();
                }
            }
            Snake snake2 = vcSnakeMy.lastElement();
            vcSnakeMy.remove(snake2);
            snake2.setX(torect.left);
            snake2.setY(torect.top);
            snake2.setHead(true);
            vcSnakeMy.insertElementAt(snake2,0);
        }

        if(time >= 10000 )
            time = 0;
    }

    @Override
    protected void onTouchDown(int id, float rawX, float rawY) {
        rectangleKeyboard.setClickXY(rawX, rawY);
    }

    @Override
    public void clickDirection(RectangleKeyboard.Direction direction) {
        this.DIRECTION = direction;
    }
    /**
     * 获取下一个矩形位置
     * @return
     * @param snake
     */
    private Rect toRect(Snake snake){
        Rect snakeRect = snake.getRect();
        Rect rect = new Rect();
        switch (DIRECTION) {
            case TOP:
                rect.set(snakeRect.left,snakeRect.top-snake.getSeep(),snakeRect.right,snakeRect.bottom-snake.getSeep());
                break;
            case LEFT:
                rect.set(snakeRect.left-snake.getSeep(),snakeRect.top,snakeRect.right-snake.getSeep(),snakeRect.bottom);
                break;
            case RIGHT:
                rect.set(snakeRect.left+snake.getSeep(),snakeRect.top,snakeRect.right+snake.getSeep(),snakeRect.bottom);
                break;
            case BOTTOM:
                rect.set(snakeRect.left,snakeRect.top+snake.getSeep(),snakeRect.right,snakeRect.bottom+snake.getSeep());
                break;
        }
        return rect;
    }
    @Override
    protected void onTouchUp(int id, float rawX, float rawY) {
        rectangleKeyboard.setClickXY(0, 0);
    }

    /**
     * 重新游戏
     */
    public void renew() {
        DIRECTION = RectangleKeyboard.Direction.TOP;
        created();
    }

    public interface onListener {
        void gameOver();//结束游戏
        void fraction();//分数
    }
    public void setOnListener(onListener lenter) {
        this.mOnListener = lenter;
    }

    public SnakeBluetoothSurface(Context context) {
        super(context);
    }

    public SnakeBluetoothSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SnakeBluetoothSurface(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setSuspend(boolean suspend) {
        this.suspend = suspend;
    }

    public void addSnake(){
        vcSnaketo.add(new Snake(mScreenW-mScreenW/3,(mScreenH - 500)/2,true,Color.BLUE));
    }
}
