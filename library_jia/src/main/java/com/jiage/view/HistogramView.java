package com.jiage.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

/**
 * 作者：李忻佳.
 * 时间：2016/11/17.
 * 说明：柱状图
 */

public class HistogramView extends BaseDrawGridView {
    /**
     * 是否设置点
     */
    private boolean isSpot = false;
    /**
     * 点的颜色
     */
    private int spotColor = Color.parseColor("#43b2e6");
    /**
     * 点的半径
     */
    private int spotRadius = 20;
    /**
     * 点击圆时的半径
     */
    private int spotClickRadius = 25;
    /**
     * 柱子的颜色
     */
    private int columnColor = Color.parseColor("#43b2e6");
    /**
     * 柱子的宽
     */
    private int columnWidth = 16;
    /**
     * 点击柱子时的宽
     */
    private int columnClickWidth = 25;
    /**
     * 是否动态加载柱子
     */
    private boolean isDynamic = true;
    /**
     * 动态加载柱子的增长值
     */
    private int dynamicValue = 20;
    /**
     * 动态加载柱子的毫秒数
     */
    private int dynamicTime = 10;
    /**
     * 是否绘制柱体上方的数据text
     */
    private boolean dataText = false;
    /**
     * 是否可以点击查看
     */
    private boolean dataClick = false;
    /**
     * 点击的是哪个柱
     */
    private int dataClickIndex = -1;
    /**
     * 柱体上方的数据text的颜色
     */
    private int dataTextColor = Color.parseColor("#000000");
    /**
     * 柱体上方的数据text的大小
     */
    private int dataTextSize = 30;

    private String[] XText;
    private String[] XData;
    private Boolean[] IsBrokenX;
    private int[] DataXY;
    private int[] XDataStartX;

    public HistogramView(Context context) {
        super(context);
    }

    public HistogramView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HistogramView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setGridSpecification(IsBrokenX);
        if(XText!=null){
            DataXY = calculationXCoordinate(XText);
            canvasColumn();
        }
    }

    /**
     * 绘制数据
     */
    private void canvasColumn() {
        if(DataXY != null && XData != null){
            for (int i = 0; i < DataXY.length; i++) {
                if(i<XData.length){
                    if(!XData[i].equals("")){
                        mPaint = new Paint();
                        mPaint.setColor(columnColor);
                        mPaint.setStyle(Paint.Style.FILL);//设置填满

                        int XdataStrat = getYValue(XData[i]);
                        //动态加载
                        if(isDynamic){
                            if(XDataStartX[i]>getYValue(XData[i])){
                                XDataStartX[i] -= dynamicValue;
                                XdataStrat = XDataStartX[i];
                                postInvalidateDelayed(dynamicTime);
                            }
                        }
                        //绘制长方形
                        if(i == dataClickIndex){
                            mCanvas.drawRect(DataXY[i]- adaptationTool.setCanvasAdaptation(columnClickWidth)/2,XdataStrat,DataXY[i]+ adaptationTool.setCanvasAdaptation(columnClickWidth)/2,Y,mPaint);// 长方形
                        }else{
                            mCanvas.drawRect(DataXY[i]- adaptationTool.setCanvasAdaptation(columnWidth)/2,XdataStrat,DataXY[i]+ adaptationTool.setCanvasAdaptation(columnWidth)/2,Y,mPaint);// 长方形
                        }

                        if(isSpot){
                            if(!XData[i].equals("")&&!XData[i].equals("0")){
                                //绘制圆形
                                if(i == dataClickIndex){
                                    mCanvas.drawCircle(DataXY[i], XdataStrat, adaptationTool.setCanvasAdaptation(spotClickRadius), mPaint);// 圆
                                }else{
                                    mCanvas.drawCircle(DataXY[i], XdataStrat, adaptationTool.setCanvasAdaptation(spotRadius), mPaint);// 圆
                                }
                            }
                        }
                        //绘制柱体上面的text
                        if(dataText || i == dataClickIndex){
                            if(!XData[i].equals("")&&Double.valueOf(XData[i])>0){
                                if(i == dataClickIndex){
                                    setDataText(DataXY[i],XdataStrat,XData[i], adaptationTool.setCanvasAdaptation(spotClickRadius),dataTextColor, adaptationTool.setCanvasAdaptation(dataTextSize));
                                }else{
                                    setDataText(DataXY[i],XdataStrat,XData[i], adaptationTool.setCanvasAdaptation(spotRadius),dataTextColor, adaptationTool.setCanvasAdaptation(dataTextSize));
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    @Override
    protected void onTouchDown(float downX, float downY) {
        if(dataClick && XDataStartX !=null) {
            for (int i = 0; i < XDataStartX.length; i++) {
                if (isSpot) {
                    if (isClickHistogramView(downX, downY, DataXY[i], getYValue(XData[i]), adaptationTool.setCanvasAdaptation(columnWidth), Y - XDataStartX[i]) || isClickCircularView(downX, downY, DataXY[i], getYValue(XData[i]), adaptationTool.setCanvasAdaptation(spotRadius))) {
                        dataClickIndex = i;
                        invalidate();
                    }
                } else {
                    if (isClickHistogramView(downX, downY, DataXY[i], getYValue(XData[i]), adaptationTool.setCanvasAdaptation(columnWidth), Y - XDataStartX[i])) {
                        dataClickIndex = i;
                        invalidate();
                    }
                }
            }
        }
    }

    @Override
    protected void onTouchUp(float upX, float upY) {
        if(dataClick) {
            dataClickIndex = -1;
            invalidate();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(XData!=null){
            XDataStartX = new int[XData.length];
            for (int i = 0; i < XData.length; i++) {
                XDataStartX[i] = Y;
            }
        }
    }

    /**
     * 设置数据
     * @param data
     */
    public void setXData(String[] data) {
        this.XData = data;
    }

    /**
     * 设置text
     * @param text
     */
    public void setXText(String[] text) {
        this.XText = text;
    }
    /**
     * 是否显示X轴对应的网格
     * @param isBrokenX
     */
    public void setIsBrokenX(Boolean[] isBrokenX) {
        this.IsBrokenX = isBrokenX;
    }

    public boolean isSpot() {
        return isSpot;
    }

    public void setSpot(boolean spot) {
        isSpot = spot;
    }

    public int getSpotColor() {
        return spotColor;
    }

    public void setSpotColor(int spotColor) {
        this.spotColor = spotColor;
    }

    public int getSpotRadius() {
        return spotRadius;
    }

    public void setSpotRadius(int spotRadius) {
        this.spotRadius = spotRadius;
    }

    public int getColumnColor() {
        return columnColor;
    }

    public void setColumnColor(int columnColor) {
        this.columnColor = columnColor;
    }

    public int getColumnWidth() {
        return columnWidth;
    }

    public void setColumnWidth(int columnWidth) {
        this.columnWidth = columnWidth;
    }

    public boolean isDynamic() {
        return isDynamic;
    }

    public void setDynamic(boolean dynamic) {
        isDynamic = dynamic;
    }

    public int getDynamicValue() {
        return dynamicValue;
    }

    public void setDynamicValue(int dynamicValue) {
        this.dynamicValue = dynamicValue;
    }

    public int getDynamicTime() {
        return dynamicTime;
    }

    public void setDynamicTime(int dynamicTime) {
        this.dynamicTime = dynamicTime;
    }

    public boolean isDataText() {
        return dataText;
    }

    public void setDataText(boolean dataText) {
        this.dataText = dataText;
    }

    public int getDataTextColor() {
        return dataTextColor;
    }

    public void setDataTextColor(int dataTextColor) {
        this.dataTextColor = dataTextColor;
    }

    public int getDataTextSize() {
        return dataTextSize;
    }

    public void setDataTextSize(int dataTextSize) {
        this.dataTextSize = dataTextSize;
    }

    public int getSpotClickRadius() {
        return spotClickRadius;
    }

    public void setSpotClickRadius(int spotClickRadius) {
        this.spotClickRadius = spotClickRadius;
    }

    public int getColumnClickWidth() {
        return columnClickWidth;
    }

    public void setColumnClickWidth(int columnClickWidth) {
        this.columnClickWidth = columnClickWidth;
    }

    public boolean isDataClick() {
        return dataClick;
    }

    public void setDataClick(boolean dataClick) {
        this.dataClick = dataClick;
    }
}
