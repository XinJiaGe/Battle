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
    private Vector<Snake> vcSnakeMy = new Vector<>();
    private Vector<Snake> vcSnaketo = new Vector<>();
    private Vector<Food> vcFood = new Vector<>();
    private boolean suspend = true; //是否暂停
    private long time;
    private RectangleKeyboard.Direction DIRECTIONMY = RectangleKeyboard.Direction.TOP;
    private RectangleKeyboard.Direction DIRECTIONTO = RectangleKeyboard.Direction.TOP;
    private onListener mOnListener;
    private boolean isMy;
    private String address;
    private boolean server = false;

    @Override
    public void created() {
        rectangleKeyboard = new RectangleKeyboard(mScreenW / 2 - 250, mScreenH - 450, 500, 400);
        rectangleKeyboard.setClickDirectionListener(this);
        if(isMy) {
            vcSnakeMy.add(new Snake(mScreenW / 3, (mScreenH - 500) / 2, true, Color.BLACK,address));
        }else {
            vcSnaketo.add(new Snake(mScreenW - mScreenW / 3, (mScreenH - 500) / 2, true, Color.BLUE,address));
        }
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
        if (!suspend && time % 5 == 0) {
            //蛇逻辑
            if(server) {
                Rect mtrect = snakeLogic(DIRECTIONMY, 1, vcSnakeMy);
                Rect torect = snakeLogic(DIRECTIONTO, 2, vcSnaketo);
                if(mOnListener!=null)
                    mOnListener.rect(mtrect,torect);
            }
        }

        if (time >= 10000)
            time = 0;
    }

    private Rect snakeLogic(RectangleKeyboard.Direction directionmy, int type, Vector<Snake> vcSnake){
        Snake snake1 = vcSnake.firstElement();
        snake1.setHead(false);
        Rect torect = toRect(directionmy,snake1);

        if(torect.left<0||torect.top<0||torect.right>mScreenW||torect.bottom>mScreenH - 500){//超出界限
            if(mOnListener!=null)
                mOnListener.gameOver(type);
            return torect;
        }
        for (int i = 0; i < vcSnake.size(); i++) {//与自己身体相撞
            Snake snake = vcSnake.elementAt(i);
            if(SurfaceViewUtil.isCollsionBumpRect(torect,snake.getRect())){
                if(mOnListener!=null)
                    mOnListener.gameOver(type);
                return torect;
            }
        }
        switch (type) {
            case 1:
                for (int i = 0; i < vcSnaketo.size(); i++) {//相撞对方身体
                    Snake snake = vcSnaketo.elementAt(i);
                    if(SurfaceViewUtil.isCollsionBumpRect(torect,snake.getRect())){
                        if(mOnListener!=null)
                            mOnListener.gameOver(type);
                        return torect;
                    }
                }
                break;
            case 2:
                for (int i = 0; i < vcSnakeMy.size(); i++) {//相撞对方身体
                    Snake snake = vcSnakeMy.elementAt(i);
                    if(SurfaceViewUtil.isCollsionBumpRect(torect,snake.getRect())){
                        if(mOnListener!=null)
                            mOnListener.gameOver(type);
                        return torect;
                    }
                }
                break;
        }
        for (int i = 0; i < vcFood.size(); i++) {//吃到食物
            Food food = vcFood.elementAt(i);
            if(SurfaceViewUtil.isCollsionBumpRect(snake1.getRect(),food.getRect())){
                food.setFoodx(-20);
                food.UpdataFood();
                vcSnake.insertElementAt(new Snake(torect.left,torect.top,true,Color.BLACK,snake1.getAddress()),0);
                if(mOnListener!=null)
                    mOnListener.fraction(type);
            }
        }
        Snake snake2 = vcSnake.lastElement();
        vcSnake.remove(snake2);
        snake2.setX(torect.left);
        snake2.setY(torect.top);
        snake2.setHead(true);
        vcSnake.insertElementAt(snake2,0);
        return torect;
    }

    @Override
    protected void onTouchDown(int id, float rawX, float rawY) {
        rectangleKeyboard.setClickXY(rawX, rawY);
    }

    @Override
    public void clickDirection(RectangleKeyboard.Direction direction) {
        if(server)
            this.DIRECTIONMY = direction;
        else{
            if(mOnListener!=null)
                mOnListener.directionto(direction);
        }
    }
    /**
     * 获取下一个矩形位置
     * @return
     * @param snake
     */
    private Rect toRect(RectangleKeyboard.Direction direction ,Snake snake){
        Rect snakeRect = snake.getRect();
        Rect rect = new Rect();
        switch (direction) {
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
        DIRECTIONMY = RectangleKeyboard.Direction.TOP;
        DIRECTIONTO = RectangleKeyboard.Direction.TOP;
        created();
    }

    public interface onListener {
        void gameOver(int type);//结束游戏
        void fraction(int type);//分数
        void directionto(RectangleKeyboard.Direction direction);//方向
        void rect(Rect rectmy,Rect rectto);//移动
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

    public void addSnake(boolean isMy, String address){
        this.isMy = isMy;
        this.address = address;
    }
    public void addSnakeMy(String address){
        vcSnakeMy.add(new Snake(mScreenW / 3, (mScreenH - 500) / 2, true, Color.BLACK,address));
    }
    public void addSnakeTo(String address){
        vcSnaketo.add(new Snake(mScreenW - mScreenW / 3, (mScreenH - 500) / 2, true, Color.BLUE,address));
    }
    public int getMyX(){
        if(vcSnakeMy.size()>0)
            return vcSnakeMy.get(0).getX();
        return 0;
    }
    public int getMyY(){
        if(vcSnakeMy.size()>0)
            return vcSnakeMy.get(0).getY();
        return 0;
    }

    public void setServer(boolean server) {
        this.server = server;
    }

    public void setDIRECTIONTO(RectangleKeyboard.Direction DIRECTIONTO) {
        this.DIRECTIONTO = DIRECTIONTO;
    }
    public void setMyrect(Rect torect) {
        Snake snake2 = vcSnakeMy.lastElement();
        vcSnakeMy.remove(snake2);
        snake2.setX(torect.left);
        snake2.setY(torect.top);
        snake2.setHead(true);
        vcSnakeMy.insertElementAt(snake2,0);
    }
    public void setTorect(Rect torect) {
        Snake snake2 = vcSnaketo.lastElement();
        vcSnaketo.remove(snake2);
        snake2.setX(torect.left);
        snake2.setY(torect.top);
        snake2.setHead(true);
        vcSnaketo.insertElementAt(snake2,0);
    }
}
