package com.jiage.battle.cocos2d;

import org.cocos2d.types.CGPoint;

/**
 * 作者：忻佳
 * 日期：2019-11-01
 * 描述：
 */
public class CollisionUtil {
    /**
     * 点击的是否在矩形内
     * @param x
     * @param y
     * @param x1
     * @param y1
     * @param w1
     * @param h1
     * @return
     */
    public static boolean isClickRectangle(float x,float y,float x1, float y1, float w1, float h1){
        if(x>=x1&&x<=x1+w1&&y>=y1&&y<=y1+h1){
            return true;
        }
        return false;
    }
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
    public static boolean isRectangleAndRectangle(float x1, float y1, float w1, float h1, float x2, float y2, float w2, float h2){
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
    public static float gePointDistance(float x1 ,float y1,float x2 ,float y2){
        return (float) Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
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

    /**
     * 获取两点之间线上一点
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param distance
     * @return
     */
    public static CGPoint getPointDistance(float x1 , float y1, float x2 , float y2, int distance){
        CGPoint point = new CGPoint();
        if(x1 == x2){
            if(y1>y2){
                point.set(x1,y1-distance);
            }else if(y1<y2){
                point.set(x1,y1+distance);
            }else{
                point.set(x1,y1);
            }
        }else if(y1 == y2){
            if(x1>x2){
                point.set(x1-distance,y2);
            }else if(x1<x2){
                point.set(x1+distance,y2);
            }else{
                point.set(x1,y2);
            }
        }else{
            float hypotenuse = gePointDistance(x1, y1, x2, y2);
            float x = (x2 - x1) / hypotenuse * distance + x1;//横坐标
            float y = (y2 - y1) / hypotenuse * distance + y1;//纵坐标
            point.set(x,y);
        }
        return point;
    }

    /**
     * 根据两个坐标获取旋转的角度
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    public static float getRotationAngle(float x1, float y1, float x2, float y2){
        float angle = getAngle(x1, y1, x2, y2);
        if(x1 == x2){
            if(y1>y2){
                angle += 180;
            }
        }else if(y1 == y2){
            if(x1>x2){
                angle += 360;
            }
        }else if(x2>x1&&y2>y1){ //第一区间
        }else if(x1>x2&&y2>y1){//第二区间
            angle = 90-Math.abs(angle)+270;
        }else if(x2<x1&&y2<y1){//第三区间
            angle += 180;
        }else{//第四区间
            angle = 90-Math.abs(angle)+90;
        }
        return angle;
    }

    /**
     * 计算角度
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    public static float getAngle(float x1, float y1, float x2, float y2){
        return (float) Math.toDegrees(Math.atan((x2 - x1) / (y2 - y1)));
    }
}
