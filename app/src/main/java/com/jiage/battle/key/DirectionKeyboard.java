package com.jiage.battle.key;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.jiage.battle.util.SurfaceViewUtil;

/**
 * 作者：李忻佳.
 * 时间：2017/5/7.
 * 说明：绘制方向键盘
 */

public class DirectionKeyboard {
    //方向键盘移动时的坐标
    private float directionMoveX, directionMoveY;
    //方向键盘点击时的坐标
    private float directionDownX, directionDownY;
    //大圈半径
    private int bigRadius = 170;
    //小圈半径
    private int smallRadius = 50;
    //是否显示方向键盘
    private boolean isDirection = false;
    //方向键盘的角度
    private int angle;
    //回调
    private OnAugListener onAugListener;
    private int touchId;

    public DirectionKeyboard(boolean isDirection) {
        this.isDirection = isDirection;
    }

    public void draw(Canvas canvas, Paint paint) {
        //绘制方向键盘
        if (isDirection) {
            paint = new Paint();
            paint.setColor(Color.BLACK);
            paint.setAlpha(10);
            //绘制大圈
            canvas.drawCircle(directionDownX, directionDownY, bigRadius, paint);
            paint.setAlpha(30);
            //计算滑动距离
            double moveDistace = SurfaceViewUtil.gePointDistance(directionMoveX, directionMoveY, directionDownX, directionDownY);
            //计算角度
            angle = SurfaceViewUtil.getRotationAngle((int) directionDownX, (int) directionDownY, (int) directionMoveX, (int) directionMoveY);

            //绘制小圈
            if (moveDistace + smallRadius > bigRadius) {
                //让小圆的距离不超过大圆范围
                canvas.drawCircle((int) SurfaceViewUtil.getCircleCoordinatesX((int) directionDownX, -angle, bigRadius - smallRadius), (int) SurfaceViewUtil.getCircleCoordinatesY((int) directionDownY, -angle, bigRadius - smallRadius), smallRadius, paint);
            } else {
                canvas.drawCircle(directionMoveX, directionMoveY, smallRadius, paint);
            }

            //开始回调
            if (onAugListener != null) {
                onAugListener.onAugListener(angle);
            }
        }
    }

    public void setTouchMove(int id, float rawX, float rawY) {
        if (isDirection && id == touchId) {
            directionMoveX = rawX;
            directionMoveY = rawY;

            if (isDirection()) {
//                mTanke.isGo(true);//坦克是否能行走
            }
        }
    }

    public void setTouchDown(int id, float screenw, float rawX, float rawY) {
        if(touchId == 0){
            touchId = id;
            //是否可以绘制方向键盘
            if (rawX < screenw / 2) {
                directionDownX = rawX;
                directionDownY = rawY;
                directionMoveX = rawX;
                directionMoveY = rawY;
                isDirection = true;
            }
//            mTanke.isGo(false);//坦克是否能行走
        }
    }
    public void setTouchUp(int id, float rawX, float rawY){
        if(id == touchId){
            touchId = 0;
            setDirection(false);
//            mTanke.isGo(false);
        }
    }

    public void setDirection(boolean direction) {
        //隐藏方向键盘
        if (isDirection) {
            isDirection = false;
        }
    }

    public interface OnAugListener {
        public void onAugListener(int angle);
    }

    /**
     * 设置回调
     *
     * @param listener
     */
    public void setAngListener(OnAugListener listener) {
        this.onAugListener = listener;
    }

    public int getAngle() {
        return angle;
    }

    public float getDirectionDownX() {
        return directionDownX;
    }

    public float getDirectionDownY() {
        return directionDownY;
    }

    public boolean isDirection() {
        return isDirection;
    }

}
