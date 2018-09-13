package com.jiage.battle.surface.snake;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.jiage.battle.util.SurfaceViewUtil;

/**
 * 作者：李忻佳
 * 日期：2018/9/13
 * 说明：矩形键盘
 */

public class RectangleKeyboard {
    private int x;
    private int y;
    private int width;
    private int height;
    private Rect rectTp;
    private Rect rectBt;
    private Rect rectLt;
    private Rect rectRt;
    private String clickColor = "#006600";
    private String noClickColor = "#00cc00";
    private String colorTp = noClickColor;
    private String colorBt = noClickColor;
    private String colorLt = noClickColor;
    private String colorRt = noClickColor;
    private onClickListener clickDirectionListener;

    public RectangleKeyboard(int left, int top, int width, int height) {
        this.x = left;
        this.y = top;
        this.width = width;
        this.height = height;
    }

    public void draw(Canvas canvas, Paint paint) {
        rectTp = new Rect(x + width / 3, y, x + width / 3 * 2, y + height / 3);
        rectBt = new Rect(x + width / 3, y + height / 3 * 2, x + width / 3 * 2, y + height);
        rectLt = new Rect(x, y + height / 3, x + width / 3, y + height / 3 * 2);
        rectRt = new Rect(x + width / 3 * 2, y + height / 3, x + width, y + height / 3 * 2);
        paint.setColor(Color.parseColor(colorTp));
        canvas.drawRect(rectTp, paint);
        paint.setColor(Color.parseColor(colorBt));
        canvas.drawRect(rectBt, paint);
        paint.setColor(Color.parseColor(colorLt));
        canvas.drawRect(rectLt, paint);
        paint.setColor(Color.parseColor(colorRt));
        canvas.drawRect(rectRt, paint);
    }

    public void logic() {

    }

    public void setClickXY(float rawX, float rawY) {
        if (SurfaceViewUtil.isCollsionClick(rectTp, rawX, rawY)) {
            colorTp = clickColor;
            if (clickDirectionListener != null)
                clickDirectionListener.clickDirection(Direction.TOP);
        } else {
            colorTp = noClickColor;
            if (SurfaceViewUtil.isCollsionClick(rectBt, rawX, rawY)) {
                colorBt = clickColor;
                if (clickDirectionListener != null)
                    clickDirectionListener.clickDirection(Direction.BOTTOM);
            } else {
                colorBt = noClickColor;
                if (SurfaceViewUtil.isCollsionClick(rectLt, rawX, rawY)) {
                    colorLt = clickColor;
                    if (clickDirectionListener != null)
                        clickDirectionListener.clickDirection(Direction.LEFT);
                } else {
                    colorLt = noClickColor;
                    if (SurfaceViewUtil.isCollsionClick(rectRt, rawX, rawY)) {
                        colorRt = clickColor;
                        if (clickDirectionListener != null)
                            clickDirectionListener.clickDirection(Direction.RIGHT);
                    } else {
                        colorRt = noClickColor;
                    }
                }
            }
        }
    }

    public void setClickDirectionListener(onClickListener listener) {
        this.clickDirectionListener = listener;
    }

    public interface onClickListener {
        void clickDirection(Direction direction);
    }

    public enum Direction {
        LEFT,
        BOTTOM,
        TOP,
        RIGHT,
    }
}
