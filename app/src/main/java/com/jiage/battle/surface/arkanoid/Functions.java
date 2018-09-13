package com.jiage.battle.surface.arkanoid;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.jiage.battle.constant.Constant;
import com.jiage.battle.sharedPreference.SharedPreference;
import com.jiage.battle.util.SurfaceViewUtil;

import java.util.Vector;

/**
 * 作者：李忻佳
 * 日期：2018/8/17
 * 说明：功能
 */

public class Functions {
    private Square mSquare;
    private Constant.FunctionType mFunction;
    private int mScreenW, mScreenH;
    private int mInitX, mInitY;
    public boolean isDead;// 优化处理
    private int speed = 10; //速度
    private Rect rect;


    public Functions(Square square, int screenW, int screenH, Constant.FunctionType function) {
        mFunction = function;
        mSquare = square;
        mScreenW = screenW;
        mScreenH = screenH;
        mInitX = mSquare.getmInitX() + mSquare.getWidth() / 2 - Constant.FunctionTypeGet.getWidht(mFunction) / 2;
        mInitY = mSquare.getmInitY() + mSquare.getHeight();
    }

    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(Constant.FunctionTypeGet.getColor(mFunction));
        rect = new Rect(mInitX, mInitY, mInitX + Constant.FunctionTypeGet.getWidht(mFunction),
                mInitY + Constant.FunctionTypeGet.getHeight(mFunction));
        canvas.drawRect(rect, paint);
        paint.setColor(Color.BLACK);
        switch (mFunction) {
            case doubleTimes:
                canvas.drawCircle(rect.centerX() - 10, rect.centerY(), 5, paint);
                canvas.drawCircle(rect.centerX() + 10, rect.centerY(), 5, paint);
                break;
            case threeTimes:
                canvas.drawCircle(rect.centerX() - 10, rect.centerY() - 5, 5, paint);
                canvas.drawCircle(rect.centerX() + 10, rect.centerY() - 5, 5, paint);
                canvas.drawCircle(rect.centerX(), rect.centerY() + 5, 5, paint);
                break;
            case fourTimes:
                canvas.drawCircle(rect.centerX() - 10, rect.centerY() - 10, 5, paint);
                canvas.drawCircle(rect.centerX() + 10, rect.centerY() - 10, 5, paint);
                canvas.drawCircle(rect.centerX() - 10, rect.centerY() + 10, 5, paint);
                canvas.drawCircle(rect.centerX() + 10, rect.centerY() + 10, 5, paint);
                break;
        }
    }

    public void logic(boolean initSuspend, boolean suspend, Player player, Vector<Bullet> vcBullet) {
        if(!suspend)
            mInitY += speed;
        if ((mInitY + Constant.FunctionTypeGet.getHeight(mFunction) > mScreenH)||initSuspend) {
            isDead = true;
        }else {
            if(!suspend) {
                try {
                    if (SurfaceViewUtil.isCollsionWithRect(mInitX, mInitY, mInitX + Constant.FunctionTypeGet.getWidht(mFunction) - mInitX,
                            mInitY + Constant.FunctionTypeGet.getHeight(mFunction) - mInitY, (int) player.getmRecf().left,
                            (int) player.getmRecf().top, (int) player.getmRecf().width(), (int) player.getmRecf().height())) {//是否和主角碰撞
                        isDead = true;
                        if (vcBullet.size() <= SharedPreference.getSharedPreference().getArkanoidMaxBullet()) {//判断子弹是否超出限制
                            int size = vcBullet.size();
                            for (int i = 0; i < size; i++) {
                                switch (mFunction) {
                                    case doubleTimes:
                                        vcBullet.add(new Bullet(vcBullet.elementAt(i).getmInitX(), vcBullet.elementAt(i).getmInitY(), mScreenW, mScreenH, vcBullet.elementAt(i).getAngle() + 90, player));
                                        break;
                                    case threeTimes:
                                        vcBullet.add(new Bullet(vcBullet.elementAt(i).getmInitX(), vcBullet.elementAt(i).getmInitY(), mScreenW, mScreenH, vcBullet.elementAt(i).getAngle() + 90, player));
                                        vcBullet.add(new Bullet(vcBullet.elementAt(i).getmInitX(), vcBullet.elementAt(i).getmInitY(), mScreenW, mScreenH, vcBullet.elementAt(i).getAngle() + 180, player));
                                        break;
                                    case fourTimes:
                                        vcBullet.add(new Bullet(vcBullet.elementAt(i).getmInitX(), vcBullet.elementAt(i).getmInitY(), mScreenW, mScreenH, vcBullet.elementAt(i).getAngle() + 90, player));
                                        vcBullet.add(new Bullet(vcBullet.elementAt(i).getmInitX(), vcBullet.elementAt(i).getmInitY(), mScreenW, mScreenH, vcBullet.elementAt(i).getAngle() + 180, player));
                                        vcBullet.add(new Bullet(vcBullet.elementAt(i).getmInitX(), vcBullet.elementAt(i).getmInitY(), mScreenW, mScreenH, vcBullet.elementAt(i).getAngle() + 270, player));
                                        break;
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
