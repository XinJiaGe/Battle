package com.jiage.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 作者：李忻佳.
 * 时间：2016/12/20.
 * 说明：可旋转的饼图
 */

public class PieChartView extends BaseDrawCakeView {
    /**
     * 最大值
     */
    private float maxValue = 100;
    /**
     * 半径
     */
    private int radius = 300;
    /**
     * 环宽
     */
    private int ringWidth = 250;
    /**
     * 起始角度
     */
    private float startAngle = 0;
    /**
     * 字体颜色
     */
    private int textColor = Color.WHITE;
    /**
     * 字体大小
     */
    private int textSize = 40;
    /**
     * 是否绘制title右边区域
     */
    private boolean titleRight = false;
    /**
     * 设置title右边字体的大小
     */
    private int titleRightTextSize = 45;
    /**
     * 设置title右边字体的颜色
     */
    private int titleRightTextColor = Color.GRAY;
    /**
     * 设置title右边离屏幕右边距离
     */
    private int titleRightRightDistance = 30;
    /**
     * 设置title右边字体离圆的距离
     */
    private int titleRightTextRadiusDistance = 30;
    /**
     * 左右缩小多少距离
     */
    private int leftRightDistace = 20;


    private String[] mData;
    private float[] dataAngle;
    private int[] mDataColor;
    private float downX;
    private float downY;
    private String[] mDataText;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.RED);
        if(getOriginX()>getOriginY()){
            radius = getOriginY() - adaptationTool.setCanvasAdaptation(leftRightDistace)-getTitleHeights()/2;
        }else{
            radius = getOriginX() - adaptationTool.setCanvasAdaptation(leftRightDistace)-getTitleHeights()/2;
        }
        int x = getOriginX();
        int y = getOriginY()+getTitleHeights()/2;
        if(mData!=null){
            // 设置个新的长方形，扫描测量
            RectF oval = new RectF(x-radius, y-radius, x+radius, y+radius);
            for (int i = 0; i < mData.length; i++) {
                if(mDataColor!=null){
                    if(i<mDataColor.length){
                        mPaint.setColor(mDataColor[i]);
                    }
                }
                // 画弧，第一个参数是RectF：该类是第二个参数是角度的开始，第三个参数是多少度，第四个参数是真的时候画扇形，是假的时候画弧线
                mCanvas.drawArc(oval, startAngle, dataAngle[i], true, mPaint);
                mPaint.setColor(textColor);
                mPaint.setTextAlign(Paint.Align.CENTER);
                mPaint.setTextSize(adaptationTool.setCanvasAdaptation(textSize));
                if(Float.parseFloat(mData[i])>5){
                    String text = null;
                    try {
                        text = (Float.parseFloat(mData[i])==Integer.parseInt(mData[i])?Integer.parseInt(mData[i]):mData[i])+"%";
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        text = mData[i]+"%";
                    }
                    setCircleCoordinates(text,x,y,startAngle-90+dataAngle[i]/2,radius- adaptationTool.setCanvasAdaptation(ringWidth)/2,mCanvas,mPaint);
                }
                startAngle += dataAngle[i];
            }
            //画中间白色的圆
            mPaint.setColor(Color.WHITE);
            mCanvas.drawCircle(x, y, radius- adaptationTool.setCanvasAdaptation(ringWidth), mPaint);// 小圆

            if(mDataText!=null){
                mPaint.setTextAlign(Paint.Align.RIGHT);
                int titleRightRightDistanceStart = getWidth() - adaptationTool.setCanvasAdaptation(titleRightRightDistance);
                for (int i = 0; i < mDataText.length; i++) {
                    mPaint.setColor(titleRightTextColor);
                    mCanvas.drawText(mDataText[i],titleRightRightDistanceStart,getTitleHeights()/2+ adaptationTool.setCanvasAdaptation(10),mPaint);
                    titleRightRightDistanceStart -= getTextWH(mDataText[i],mPaint).width()+ adaptationTool.setCanvasAdaptation(titleRightTextRadiusDistance);
                    if(mDataColor!=null){
                        if(i<mDataColor.length){
                            mPaint.setColor(mDataColor[i]);
                        }
                    }
                    mCanvas.drawCircle(titleRightRightDistanceStart, getTitleHeights()/2, adaptationTool.setCanvasAdaptation(15), mPaint);// 圆
                    titleRightRightDistanceStart -= adaptationTool.setCanvasAdaptation(20)+ adaptationTool.setCanvasAdaptation(titleRightTextRadiusDistance);
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getRawX();
                downY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return true;
    }

    /**
     * 设置数据
     * @param data
     */
    public void setData(String[] data){
        if(data != null) {
            mData = data;
            dataAngle = new float[data.length];
            for (int i = 0; i < data.length; i++) {
                dataAngle[i] = (360 * Float.parseFloat(data[i])/ maxValue);
            }
        }
    }
    /**
     * 设置数据颜色
     * @param color
     */
    public void setDataColor(int[] color){
        mDataColor = color;
    }

    /**
     * 设置数据描述
     * @param text
     */
    public void setDataText(String[] text){
        mDataText = text;
    }

    public PieChartView(Context context) {
        super(context);
    }

    public PieChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PieChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public float getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(float maxValue) {
        this.maxValue = maxValue;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getRingWidth() {
        return ringWidth;
    }

    public void setRingWidth(int ringWidth) {
        this.ringWidth = ringWidth;
    }

    public float getStartAngle() {
        return startAngle;
    }

    public void setStartAngle(float startAngle) {
        this.startAngle = startAngle;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public boolean isTitleRight() {
        return titleRight;
    }

    public void setTitleRight(boolean titleRight) {
        this.titleRight = titleRight;
    }

    public int getTitleRightTextSize() {
        return titleRightTextSize;
    }

    public void setTitleRightTextSize(int titleRightTextSize) {
        this.titleRightTextSize = titleRightTextSize;
    }

    public int getTitleRightTextColor() {
        return titleRightTextColor;
    }

    public void setTitleRightTextColor(int titleRightTextColor) {
        this.titleRightTextColor = titleRightTextColor;
    }

    public int getTitleRightRightDistance() {
        return titleRightRightDistance;
    }

    public void setTitleRightRightDistance(int titleRightRightDistance) {
        this.titleRightRightDistance = titleRightRightDistance;
    }

    public int getTitleRightTextRadiusDistance() {
        return titleRightTextRadiusDistance;
    }

    public void setTitleRightTextRadiusDistance(int titleRightTextRadiusDistance) {
        this.titleRightTextRadiusDistance = titleRightTextRadiusDistance;
    }

    public int getLeftRightDistace() {
        return leftRightDistace;
    }

    public void setLeftRightDistace(int leftRightDistace) {
        this.leftRightDistace = leftRightDistace;
    }
}
