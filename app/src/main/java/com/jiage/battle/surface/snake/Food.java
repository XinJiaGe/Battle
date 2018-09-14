package com.jiage.battle.surface.snake;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import com.jiage.battle.util.SurfaceViewUtil;

/**
 * 作者：李忻佳
 * 日期：2018/9/13
 * 说明：食物
 */

public class Food {
    private int mScreenW,mScreenH;
    private int foodx=-20,foody=-20;
    private int w=20,h=20;//宽高
    private Rect rect;
    private Canvas mCanvas;
    private Paint mPaint;

    public Food(int mScreenW, int mScreenH){
        this.mScreenW = mScreenW;
        this.mScreenH = mScreenH;
        UpdataFood();
    }
    public void draw(Canvas canvas, Paint paint) {
        this.mCanvas = canvas;
        this.mPaint = paint;
        paint.setColor(Color.GREEN);
        rect = new Rect(foodx,foody,foodx+w,foody+h);
        canvas.drawRect(rect,paint);
    }

    /**
     * 更新xy轴
     */
    public void UpdataFood(){
        int randomX = SurfaceViewUtil.getRandomEnd(mScreenW - 20);
        foodx = is20(randomX);
        int randomY = SurfaceViewUtil.getRandomEnd(mScreenH - 520);
        foody = is20(randomY);
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
                random--;
                if(random%20==0){
                    isWhere = false;
                    return random;
                }
            }else{
                random++;
                if(random%20==0){
                    isWhere = false;
                    return random;
                }
            }
        }
        return -20;
    }

    public Rect getRect() {
        return rect;
    }

    public void setFoodx(int foodx) {
        this.foodx = foodx;
    }

    public int getFoodx() {
        return foodx;
    }

    public int getFoody() {
        return foody;
    }
}
