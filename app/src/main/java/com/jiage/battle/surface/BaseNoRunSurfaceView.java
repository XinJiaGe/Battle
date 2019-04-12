package com.jiage.battle.surface;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

import com.jiage.battle.sound.GameSoundPool;

/**
 * 作者：李忻佳.
 * 时间：2016/12/21.
 * 说明：BaseDrawSurfaceView
 */

public abstract class BaseNoRunSurfaceView extends SurfaceView implements Callback{
    protected Context mContext;
    //用于控制SurfaceView
    private SurfaceHolder sfh;
    //声明一个画笔
    protected Paint mPaint;
    //声明一个画布
    protected Canvas mCanvas;
    //声明屏幕的宽高
    protected int mScreenW, mScreenH;
    //设置画布绘图无锯齿
    private PaintFlagsDrawFilter pfd = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
    //点击的XY点
    private float downRawX, downRawY;
    protected GameSoundPool soundPool;
    private int bgColor = Color.WHITE;

    /**
     * SurfaceView初始化函数
     */
    protected void init(Context context) {
        this.mContext = context;
        soundPool = GameSoundPool.getInstance(mContext);//声音类
        //实例SurfaceHolder
        sfh = this.getHolder();
        //为SurfaceView添加状态监听
        sfh.addCallback(this);
        //实例一个画笔
        mPaint = new Paint();
        //设置画笔颜色为白色
        mPaint.setColor(Color.WHITE);
        //设置焦点
        setFocusable(true);
    }

    /**
     * 视图创建
     */
    public abstract void created();

    /**
     * 游戏绘图内容
     */
    public abstract void draw();

    /**
     * 游戏逻辑
     */
    public abstract void logic();

    /**
     * 游戏绘图
     */
    public void myDraws() {
        try {
            mCanvas = sfh.lockCanvas();
            if (mCanvas != null) {
                //----设置画布绘图无锯齿
                mCanvas.setDrawFilter(pfd);
                mCanvas.drawColor(bgColor);
                draw();
            }
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            if (mCanvas != null)
                sfh.unlockCanvasAndPost(mCanvas);
        }
        logic();
    }

    /**
     * 触屏事件监听
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downRawX = event.getX();
                downRawY = event.getY();
                onTouchDown(event.getPointerId(event.getPointerCount() - 1) + 1, downRawX, downRawY);
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                downRawX = event.getX();
                downRawY = event.getY();
                onTouchDown(event.getPointerId(event.getPointerCount() - 1) + 1, downRawX, downRawY);
                break;
            case MotionEvent.ACTION_MOVE:
                for (int i = 0; i < event.getPointerCount(); i++) {
                    onTouchMove(event.getPointerId(i) + 1, downRawX, downRawY, event.getX(i), event.getY(i));
                }
                break;
            case MotionEvent.ACTION_UP:
                onTouchUp(event.getPointerId(event.getPointerCount() - 1) + 1, event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_POINTER_UP:
                onTouchUp(event.getPointerId(event.getPointerCount() - 1) + 1, event.getX(), event.getY());
                break;
        }
        return true;
    }

    /**
     * 点击按下事件
     *
     * @param rawX
     * @param rawY
     */
    protected void onTouchDown(int id, float rawX, float rawY) {
    }

    /**
     * 点击按下移动事件
     *
     * @param downRawX
     * @param downRawY
     * @param rawX
     * @param rawY
     */
    protected void onTouchMove(int id, float downRawX, float downRawY, float rawX, float rawY) {
    }

    /**
     * 点击抬起事件
     *
     * @param rawX
     * @param rawY
     */
    protected void onTouchUp(int id, float rawX, float rawY) {
    }

    /**
     * SurfaceView视图创建，响应此函数
     */
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mScreenW = this.getWidth();
        mScreenH = this.getHeight();
        created();
    }

    /**
     * SurfaceView视图状态发生改变，响应此函数
     */
    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    /**
     * SurfaceView视图消亡时，响应此函数
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
    }


    public BaseNoRunSurfaceView(Context context) {
        super(context);
        init(context);
    }

    public BaseNoRunSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BaseNoRunSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }
}
