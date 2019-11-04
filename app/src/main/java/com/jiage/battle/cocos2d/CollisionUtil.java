package com.jiage.battle.cocos2d;

import android.graphics.Point;

/**
 * 作者：忻佳
 * 日期：2019-11-01
 * 描述：
 */
public class CollisionUtil {
    /**
     * 两个矩形判断是否碰撞
     * @param x1
     * @param y1
     * @param w1
     * @param h1
     * @param x2
     * @param y2
     * @param w2
     * @param h2
     * @return
     */
    public static boolean isRectangleAndRectangle(int x1, int y1, int w1, int h1, int x2, int y2, int w2, int h2){
        //当矩形1位于矩形2的左侧
        if(x1+w1<x2){
            return false;
        }
        //当矩形1位于矩形2的右侧
        if(x1>x2+w2){
            return false;
        }
        //当矩形1位于矩形2的上方
        if(y1>y2+h2){
            return false;
        }
        //当矩形1位于矩形2的下方
        if(y1+h1<y2){
            return false;
        }
        //所有不会发生碰撞都不满足，肯定就是碰撞了
        return true;
    }

    /**
     * 计算两点之间的距离
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    public static double gePointDistance(float x1 ,float y1,float x2 ,float y2){
        Point p1 = new Point((int)x1,(int)y1);// 定义第一个点的坐标(5,5),或者你自己设置x,y坐标
        Point p2 = new Point((int)x2,(int)y2);// 定义第一个点的坐标(5,5),或者你自己设置x,y坐标
        // 两点间距离
        return Math.sqrt(Math.abs((p1.x - p2.x) * (p1.x - p2.x)+(p1.y - p2.y) * (p1.y - p2.y)));
    }
    /**
     * 计算两点之间移动所需时间
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param steep  移动速度
     * @return
     */
    public static double geDistanceData(float x1 ,float y1,float x2 ,float y2,int steep){
        double distance = gePointDistance(x1, y1, x2, y2);
        return distance/steep;
    }
}
