package com.jiage.battle.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import com.jiage.tool.AdaptationTool;

/**
 * 作者：李忻佳.
 * 时间：2016/11/24.
 * 说明：加载
 */

public class LoadingProgressView extends View {
    private int circularRadius = 100;

    private int mWidth;
    private int mHeight;
    private Paint mPaint;
    private int start = 0;
    private int progressMax = 100;
    private RectF oval;
    private Canvas mCanvas;
    private int startAlpha = 0;
    private AdaptationTool adaptationTool;
    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            invalidate();
        }
    };

    public LoadingProgressView(Context context) {
        super(context);
        init(context);
    }

    public LoadingProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LoadingProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        adaptationTool = new AdaptationTool(context);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mCanvas = canvas;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#f0f0f0"));
        mPaint.setAlpha(startAlpha);
        canvas.drawCircle(mWidth/2, mHeight/2 , adaptationTool.setCanvasAdaptation(circularRadius), mPaint);// 圆
        mPaint.setColor(Color.parseColor("#999999"));
        mPaint.setAlpha(200);
        oval = new RectF(mWidth/2- adaptationTool.setCanvasAdaptation(circularRadius), mHeight/2- adaptationTool.setCanvasAdaptation(circularRadius),mWidth/2+ adaptationTool.setCanvasAdaptation(circularRadius), mHeight/2+ adaptationTool.setCanvasAdaptation(circularRadius));// 设置个新的长方形，扫描测量
        canvas.drawArc(oval, 90, start*360/progressMax, true, mPaint);
        if(start>=progressMax){
            setVisibility(View.GONE);
        }
    }
    public void setProgress(int progress){
        start = progress;
        Message message = new Message();
        handler.sendMessage(message);
        startAlpha = 100;
    }
    public void setProgressMax(int max){
        progressMax = max;
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        mWidth = measureWidth(widthMeasureSpec);
        mHeight = measureHeight(heightMeasureSpec);
    }

    //根据xml的设定获取宽度
    private int measureWidth(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        //wrap_content
        if (specMode == MeasureSpec.AT_MOST){

        }
        //fill_parent或者精确值
        else if (specMode == MeasureSpec.EXACTLY){

        }
        return specSize;
    }
    //根据xml的设定获取高度
    private int measureHeight(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        //wrap_content
        if (specMode == MeasureSpec.AT_MOST){

        }
        //fill_parent或者精确值
        else if (specMode == MeasureSpec.EXACTLY){

        }
        return specSize;
    }
}
