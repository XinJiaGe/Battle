package com.jiage.battle.surface.arkanoid;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.jiage.battle.constant.Constant;
import com.jiage.battle.sharedPreference.SharedPreference;
import com.jiage.battle.sound.GameSoundPool;
import com.jiage.battle.util.SurfaceViewUtil;

/**
 * 作者：李忻佳
 * 日期：2018/8/15
 * 说明：子弹
 */

public class Bullet {
    private Player mPlayer;
    private int mInitX,mInitY;
    private int mScreenW,mScreenH;
    private int lastX,lastY;//上一次的坐标
    private int angle = 0;//角度
    private int speed = 10; //速度
    public boolean isDead;// 是否没有接到子弹，优化处理

    public Bullet(int bulletInitX, int bulletInitY, int screenW, int screenH, int angle, Player player){
        mInitX = bulletInitX;
        mInitY = bulletInitY;
        mScreenW = screenW;
        mScreenH = screenH;
        this.angle = angle;
        mPlayer = player;
    }

    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(Constant.Bullet.color);
        canvas.drawCircle(mInitX,mInitY,Constant.Bullet.radius,paint);
        lastX = mInitX;
        lastY = mInitY;
    }
    public void logic(boolean initSuspend, boolean suspend, Player player, GameSoundPool soundPool) {
        if(mInitY>mScreenH)
            isDead = true;
        else {
            if (initSuspend) {
                mInitX = (int) player.getmInitX() + Constant.Player.width / 2;
                mInitY = (int) player.getmInitY() - Constant.Bullet.radius;
            }
            if(!suspend) {
                mInitX = (int) SurfaceViewUtil.getCircleCoordinatesX(mInitX, angle, speed);
                mInitY = (int) SurfaceViewUtil.getCircleCoordinatesY(mInitY, angle, speed);
                if (mInitX < Constant.Bullet.radius * 2)
                    isTurn(false, false, -10, 0, 0, mScreenH, mInitX, mInitY, Constant.Bullet.radius);
                else if (mInitY < Constant.Bullet.radius * 2)
                    isTurn(false, false, 0, -10, mScreenW, 0, mInitX, mInitY, Constant.Bullet.radius);
                else if (mInitX > mScreenW - Constant.Bullet.radius * 2)
                    isTurn(false, false, mScreenW, 0, mScreenW + 10, mScreenH, mInitX, mInitY, Constant.Bullet.radius);
                else if (mInitX > mPlayer.getmInitX() && mInitX < (mPlayer.getmInitX() + Constant.Player.width) && mInitY > mPlayer.getmInitY() - Constant.Bullet.radius * 2)
                    if (isTurn(true, false, (int) mPlayer.getmInitX(), (int) mPlayer.getmInitY(), (int) (mPlayer.getmInitX() + Constant.Player.width),
                            (int) (mPlayer.getmInitY() + 1), mInitX, mInitY, Constant.Bullet.radius)) {
                        if(SharedPreference.getSharedPreference().isArkanoidBulletSound())
                            soundPool.play(GameSoundPool.ENEMYCLEARA);
                    }
            }
        }
    }

    /**
     * 判断是否碰撞
     * @param left
     * @param top
     * @param right
     * @param bottom
     * @param x
     * @param y
     * @param r
     * @return
     */
    public boolean isTurn(boolean isPlayer,boolean isJiao,int left, int top, int right, int bottom, int x, int y, int r) {
        boolean isY = hitRect(isPlayer,isJiao,x,y,r,left,top,right,bottom);
        if(isY)
            Backspace();
        return isY;
    }

    /**
     * 碰撞退格
     */
    private void Backspace(){
        mInitX = lastX;
        mInitY = lastY;
    }

    /**
     * 判断圆和矩形的碰撞   5 1 6
     *                      4   2
     * @param isPlayer    9 3 7
     * @param isJiao    是否计算角
     * @param ball_x
     * @param ball_y
     * @param ball_r
     * @param left
     * @param top
     * @param right
     * @param bottom
     *
     *
     *
     * @return
     */
    private boolean hitRect(boolean isPlayer,boolean isJiao, int ball_x, int ball_y, int ball_r, int left, int top, int right, int bottom) {
        if(isPlayer){
            if(top>=ball_y-ball_r && top<ball_y+ball_r){
                angle = 180 - angle;
                return true;
            }
        }else {

            // 球在1，3区域的碰撞判断
            if (ball_x >= left && ball_x <= right) {
                if (ball_y + ball_r >= top && ball_y < top) { // 1区域判断
                    angle = 180 - angle;
                    return true;
                } else if (ball_y - ball_r <= bottom && ball_y > bottom) { // 3区域判断
                    angle = 180 - angle;
                    return true;
                }
            }
            // 球在2，4区域的碰撞判断
            if (ball_y >= top && ball_y <= bottom) {
                if (ball_x - ball_r <= right && ball_x > right) { // 2区域判断
                    angle = 360 - angle;
                    return true;
                } else if (ball_x + ball_r >= left && ball_x < left) { // 4区域判断
                    angle = 360 - angle;
                    return true;
                }
            }
            if (isJiao) {
                // 圆在5区域的碰撞判断
                if (Math.pow(left - ball_x, 2) + Math.pow(top - ball_y, 2) <= Math.pow(ball_r, 2) && ball_x < left && ball_y < top) {
                    angle = 180 + angle;
                    return true;
                }
                // 圆在6区域的碰撞判断
                if (Math.pow(ball_x - right, 2) + Math.pow(top - ball_y, 2) <= Math.pow(ball_r, 2) && ball_x > right && ball_y < top) {
                    angle = 180 + angle;
                    return true;
                }
                // 圆在7区域的碰撞判断
                if (Math.pow(ball_x - right, 2) + Math.pow(ball_y - bottom, 2) <= Math.pow(ball_r, 2) && ball_x > right && ball_y > bottom) {
                    angle = angle - 180;
                    return true;
                }
                // 圆在8区域的碰撞判断
                if (Math.pow(left - ball_x, 2) + Math.pow(ball_y - bottom, 2) <= Math.pow(ball_r, 2) && ball_x < left && ball_y > bottom) {
                    angle = angle - 180;
                    return true;
                }
            }
//        //圆心在矩形内
//        Rect rect = new Rect(left, top, right, bottom);
//        if(SurfaceViewUtil.isCollsionClick(new Region(left, top, right, bottom),ball_x,ball_y)){
//            if(ball_x<rect.centerX()&&ball_y<rect.centerY()){//左上
//                angle = 180 + angle;
//                return true;
//            }
//            if(ball_x<rect.centerX()&&ball_y>rect.centerY()){//左下
//                angle = angle - 180;
//                return true;
//            }
//            if(ball_x>rect.centerX()&&ball_y<rect.centerY()){//右上
//                angle = 180 + angle;
//                return true;
//            }
//            if(ball_x>rect.centerX()&&ball_y>rect.centerY()){//右下
//                angle = angle - 180;
//                return true;
//            }
//        }
        }
        return false;
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

    public int getAngle() {
        return angle;
    }

    @Override
    public String toString() {
        return "Bullet{" +
                "mInitX=" + mInitX +
                ", mInitY=" + mInitY +
                '}';
    }
}
