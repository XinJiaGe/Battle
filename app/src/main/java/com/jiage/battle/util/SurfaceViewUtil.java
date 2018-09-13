package com.jiage.battle.util;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Region;

import java.util.Random;

/**
 * 作者：李忻佳.
 * 时间：2016/12/24.
 * 说明：
 */

public class SurfaceViewUtil {
    /**
     * 获取随机数   0 - end
     * @param end
     * @return
     */
    public static int getRandomEnd(int end){
        Random random=new Random();// 定义随机类
        int result=random.nextInt(end);// 返回[0,10)集合中的整数，注意不包括10
        return result+1;
    }
    /**
     *  根据圆获取圆上的点X轴坐标
     * @param originX   圆的原点X坐标
     * @param angle     角度  0 是正下方，逆时针旋转
     * @param radius    圆的半径
     * @return          圆上的点的X坐标
     */
    public static double getCircleCoordinatesX(int originX , int angle, int radius){
        return originX + Math.sin(2* Math.PI / 360 * angle) * radius;
    }

    /**
     *  根据圆获取圆上的点Y轴坐标
     * @param originY   圆的原点Y坐标
     * @param angle     角度  0 是正下方，逆时针旋转
     * @param radius    圆的半径
     * @return          圆上的点的Y坐标
     */
    public static double getCircleCoordinatesY(int originY , int angle, int radius){
        return originY + Math.cos(2* Math.PI / 360 * angle) * radius;
    }

    /**
     * 计算两点之间的距离
     * @param p1
     * @param p2
     * @return
     */
    public static double gePointDistance(Point p1, Point p2){
        double _x = Math.abs(p2.x - p1.x);
        double _y = Math.abs(p2.y - p1.y);
        return Math.sqrt(_x*_x+_y*_y);
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
     * 使用Bitmap加Matrix来缩放和旋转图片
     * @param bitmap
     * @param w         设置宽度
     * @param h         设置高度
     * @param angle     设置旋转角度
     * @return
     */
    public static Bitmap resizeAndRotateImage(Bitmap bitmap, int w, int h, int angle){
        Bitmap BitmapOrg = bitmap;
        int width = BitmapOrg.getWidth();
        int height = BitmapOrg.getHeight();
        int newWidth = w;
        int newHeight = h;

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);//缩放
        matrix.postScale(1, -1);   //镜像垂直翻转
        // if you want to rotate the Bitmap
        matrix.postRotate(angle);
        Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,height, matrix, true);
        return resizedBitmap;
    }

    /**
     * 缩放Bitmap
     * @param bitmap
     * @param zoomW     缩放后的宽度
     * @param zoomH     缩放后的高度
     */
    public static Bitmap zoomBitmap(Bitmap bitmap, int zoomW, int zoomH){
        Bitmap BitmapOrg = bitmap;
        int width = BitmapOrg.getWidth();
        int height = BitmapOrg.getHeight();
        int newWidth = zoomW;
        int newHeight = zoomH;

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        //首先创建一个矩阵实例
        Matrix matrix = new Matrix();
        //然后对矩阵进行旋转缩放
        matrix.postScale(scaleWidth,scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,height, matrix, true);
        return resizedBitmap;
    }
    /**
     * 旋转Bitmap
     * @param angle     旋转角度
     * @param coreX     旋转中心X
     * @param coreY     旋转中心Y
     */
    public static Matrix rotateBitmap(int angle, int coreX, int coreY){
        //首先创建一个矩阵实例
        Matrix mx = new Matrix();
        //然后对矩阵进行旋转缩放
        mx.postRotate(angle,coreX,coreY);
        return mx;
    }
    /**
     * 平移Bitmap
     * @param translationX     平移X
     * @param translationY     平移Y
     */
    public static Matrix translationBitmap(int translationX, int translationY){
        //首先创建一个矩阵实例
        Matrix mx = new Matrix();
        //然后对矩阵进行旋转缩放
        mx.postTranslate(translationX,translationY);
        return mx;
    }
    /**
     * 根据两个坐标获取旋转的角度
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    public static int getRotationAngle(int x1, int y1, int x2, int y2){
        int angle = 0;
        if(x2>x1&&y2>y1){//右下
            angle = getAngle(x1, y1, x2, y2);
            angle -= 90;
        }else if(x2>x1&&y2<y1){//右上
            angle = -getAngle(x1, y1, x2, y2);
            angle += 270;
        }else if(x2<x1&&y2>y1){//左下
            angle = -getAngle(x1, y1, x2, y2);
            angle += 90;
        }else if(x2<x1&&y2<y1){//左上
            angle = getAngle(x1, y1, x2, y2);
            angle += 90;
        }else{
            angle = getAngle(x1, y1, x2, y2);
        }
        return angle;
    }
    /**
     * 根据两点坐标获取角度
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    public static int getAngle(int x1, int y1, int x2, int y2){
        int x= Math.abs(x1-x2);
        int y= Math.abs(y1-y2);
        double z= Math.sqrt(x*x+y*y);
        return Math.round((float)(Math.asin(y/z)/ Math.PI*180));//最终角度
    }

    /**
     * 判定用户触屏的坐标点是否在碰撞矩形内
     * @param region
     * @param clickX
     * @param clickY
     * @return
     */
    // 定义碰撞矩形
    //private Rect rect = new Rect(0, 0, 50, 50);
    // 定义Region类实例
    //private Region region = new Region(rect);
    public static boolean isCollsionClick(Region region, float clickX, float clickY){
        boolean isInclude = false;
        if (region.contains((int)clickX, (int) clickY)) {
            isInclude = true;
        } else {
            isInclude = false;
        }
        return isInclude;
    }
    public static boolean isCollsionClick(Rect rect, float clickX, float clickY){
        Region region = new Region(rect);
        boolean isInclude = false;
        if (region.contains((int)clickX, (int) clickY)) {
            isInclude = true;
        } else {
            isInclude = false;
        }
        return isInclude;
    }

    /**
     * 判断点击位置是否在园内
     * @param clickX
     * @param clickY
     * @param gardenX
     * @param gardenY
     * @param gardenR
     * @return
     */
    public static boolean isGradenClick(int clickX,int clickY,int gardenX,int gardenY,int gardenR){
        //点击位置x坐标与圆心的x坐标的距离d
        int distanceX = Math.abs(clickX-gardenX);
        //点击位置y坐标与圆心的y坐标的距离
        int distanceY = Math.abs(clickY-gardenY);
        //点击位置与圆心的直线距离
        int distanceZ = (int) Math.sqrt(Math.pow(distanceX,2)+ Math.pow(distanceY,2));

        //如果点击位置与圆心的距离大于圆的半径，证明点击位置没有在圆内
        if(distanceZ > gardenR){
            return false;
        }
        return true;
    }
    /**
     * 矩形的碰撞情况判断
     * @param x1 第一个矩形的X坐标
     * @param y1 第一个矩形的Y坐标
     * @param w1 第一个矩形的宽
     * @param h1 第一个矩形的高
     * @param x2 第二个矩形的X坐标
     * @param y2 第二个矩形的Y坐标
     * @param w2 第二个矩形的宽
     * @param h2 第二个矩形的高
     * @return
     */
    public static boolean isCollsionWithRect(int x1, int y1, int w1, int h1, int x2, int y2, int w2, int h2) {
        //当矩形1位于矩形2的左侧
        if (x1 >= x2 && x1 >= x2 + w2) {
            return false;
        }
        //当矩形1位于矩形2的右侧
        else if (x1 <= x2 && x1 + w1 <= x2) {
            return false;
        }
        //当矩形1位于矩形2的上方
        else if (y1 >= y2 && y1 >= y2 + h2) {
            return false;
        }
        //当矩形1位于矩形2的下方
        else if (y1 <= y2 && y1 + h1 <= y2) {
            return false;
        }
        //所有不会发生碰撞都不满足，肯定就是碰撞了
        return true;
    }

    /**
     * 判断矩形和圆形是否碰撞
     * @param rect
     * @param x
     * @param y
     * @param r
     * @return
     */
    public static boolean isCollsionWithRectToCircle(Rect rect, int x, int y, int r) {
        if(x+r>=rect.left&&y+r>=rect.top){
            return true;
        }
        if(x+r>=rect.left&&y-r>=rect.bottom){
            return true;
        }
        if(x-r<=rect.right&&y-r<=rect.bottom){
            return true;
        }
        if(x-r<=rect.right&&y+r>=rect.bottom){
            return true;
        }
        return false;
    }
    /**
     * 圆形碰撞
     * @param x1    圆形1的圆心X坐标
     * @param y1    圆形2的圆心X坐标
     * @param x2    圆形1的圆心Y坐标
     * @param y2    圆形2的圆心Y坐标
     * @param r1    圆形1的半径
     * @param r2    圆形2的半径
     * @return
     */
    public static boolean isCollisionWithCircle(int x1, int y1, int x2, int y2, int r1, int r2) {
        //Math.sqrt:开平方
        //Math.pow(double x, double y): X的Y次方
        if (Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2)) <= r1 + r2) {
            //如果两圆的圆心距小于或等于两圆半径则认为发生碰撞
            return true;
        }
        return false;
    }
    /**
     *
     * @param rectArray
     * @param rect2Array
     * @return
     */
    //// 定义两个矩形图形的宽高坐标
    private static int rectX1 = 10, rectY1 = 10, rectW1 = 40, rectH1 = 40;
    private static int rectX2 = 100, rectY2 = 110, rectW2 = 40, rectH2 = 40;
    //// 定义第一个矩形的矩形碰撞数组
    //private Rect clipRect1 = new Rect(0, 0, 15, 15);
    //private Rect clipRect2 = new Rect(rectW1 - 15, rectH1 - 15, rectW1, rectH1);
    //private Rect[] arrayRect1 = new Rect[] { clipRect1, clipRect2 };
    //// 定义第二个矩形的矩形碰撞数组
    //private Rect clipRect3 = new Rect(0, 0, 15, 15);
    //private Rect clipRect4 = new Rect(rectW2 - 15, rectH2 - 15, rectW2, rectH2);
    //private Rect[] arrayRect2 = new Rect[] { clipRect3, clipRect4 };
    // Rect 类中的四个属性 top bottom left right
    // 分别表示这个矩形的 上 下 左 右
    public static boolean isCollsionWithRect(Rect[] rectArray, Rect[] rect2Array) {
        Rect rect = null;
        Rect rect2 = null;
        for (int i = 0; i < rectArray.length; i++) {
            // 依次取出第一个矩形数组的每个矩形实例
            rect = rectArray[i];
            // 获取到第一个矩形数组中每个矩形元素的属性值
            int x1 = rect.left + rectX1;
            int y1 = rect.top + rectY1;
            int w1 = rect.right - rect.left;
            int h1 = rect.bottom - rect.top;
            for (int j = 0; j < rect2Array.length; j++) {
                // 依次取出第二个矩形数组的每个矩形实例
                rect2 = rect2Array[j];
                // 获取到第二个矩形数组中每个矩形元素的属性值
                int x2 = rect2.left + rectX2;
                int y2 = rect2.top + rectY2;
                int w2 = rect2.right - rect2.left;
                int h2 = rect2.bottom - rect2.top;
                // 进行循环遍历两个矩形碰撞数组所有元素之间的位置关系
                if (x1 >= x2 && x1 >= x2 + w2) {
                } else if (x1 <= x2 && x1 + w1 <= x2) {
                } else if (y1 >= y2 && y1 >= y2 + h2) {
                } else if (y1 <= y2 && y1 + h1 <= y2) {
                } else {
                    // 只要有一个碰撞矩形数组与另一碰撞矩形数组发生碰撞则认为碰撞
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * 获取字符串的宽高
     * @param text
     * @return
     */
    public static Rect getTextWH(String text, Paint paint){
        Rect bounds = new Rect();
        if(text == null){
            paint.getTextBounds("",0,0,bounds);
        }else{
            paint.getTextBounds(text,0,text.length(),bounds);
        }
        return bounds;
    }
}
