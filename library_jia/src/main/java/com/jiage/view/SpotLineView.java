package com.jiage.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 作者：李忻佳.
 * 时间：2016/11/17.
 * 说明：点连接直线图
 */

public class SpotLineView extends BaseDrawGridView {
    /**
     * 圆环颜色
     */
    private int circularColor = Color.RED;
    /**
     * 圆环内颜色
     */
    private int circularInsideColor = Color.BLACK;
    /**
     * 圆环宽
     */
    private int circularWight = 5;
    /**
     * 圆环半径
     */
    private int circularRadius = 20;
    /**
     * 线的宽度
     */
    private int lineWidth = 5;
    /**
     * 是否动态加载柱子
     */
    private boolean isDynamic = true;
    /**
     * 动态加载柱子的增长值
     */
    private int dynamicValue = 10;
    /**
     * 动态加载柱子的毫秒数
     */
    private int dynamicTime = 10;
    /**
     * 是否绘制柱体上方的数据text
     */
    private boolean dataText = false;
    /**
     * 柱体上方的数据text的颜色
     */
    private int dataTextColor = Color.parseColor("#000000");
    /**
     * 柱体上方的数据text的大小
     */
    private int dataTextSize = 30;
    /**
     * 设置目标范围的透明度 0-255
     */
    private int targetRangeAlpha = 100;
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
    //目标范围(矩形)最大值
    private int targetRangeRectangleMax = 0;
    //目标范围(矩形)最小值
    private int targetRangeRectangleMin = 0;
    //目标范围(矩形)颜色
    private int targetRangeRectangleColor = Color.BLUE;
    //目标范围(矩形)透明度 0-255
    private int targetRangeRectangleAlpha = 100;

    private List<int[]> Xdata;
    private String[] XText;
    private int[] DataXY;
    private List<int[]> dataPointColor;
    private List<int[]> dataPointLineColor;
    private List<Boolean[]> dataPointIsHollow;
    private List<int[]> dataStatHeightList;
    private List<int[]> targetRange;
    private int[] targetRangeColor;
    private List<HashMap<String, Object>> titleRightList;


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(XText!=null){
            DataXY = calculationXCoordinate(XText);
            canvasCirculars();
        }
    }

    /**
     * 画圆环和线
     */
    private void canvasCirculars(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL);//设置填满
        mPaint.setAntiAlias(true);// 设置画笔的锯齿效果。 true是去除，大家一看效果就明白了
//        //绘制目标范围（矩形）
//        if(targetRangeRectangleMax != targetRangeRectangleMin){
//            mPaint.setColor(targetRangeRectangleColor);
//            mPaint.setAlpha(targetRangeRectangleAlpha);
//            Path path = new Path();
//            path.moveTo(0,getYValue(targetRangeRectangleMin));
//            path.lineTo(0,getYValue(targetRangeRectangleMax));
//            path.lineTo(mWidth-X,getYValue(targetRangeRectangleMax));
//            path.lineTo(mWidth-X,getYValue(targetRangeRectangleMin));
//            path.close();
//            mCanvas.drawPath(path, mPaint);
//        }
        //绘制目标范围（线）
        if(targetRange!=null){
            for (int i = 0; i < targetRange.size(); i++) {
                if(targetRangeColor!=null){
                    mPaint.setColor(targetRangeColor[i]);
                    mPaint.setAlpha(targetRangeAlpha);
                }
                mCanvas.drawRect(X,getYValue(targetRange.get(i)[0]),DataXY[DataXY.length-1],getYValue(targetRange.get(i)[1]),mPaint);
            }
        }
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);//设置填满
        mPaint.setAntiAlias(true);// 设置画笔的锯齿效果。 true是去除，大家一看效果就明白了
        mPaint.setColor(circularColor);
        //绘制线
        if(Xdata!=null) {
            for (int i = 0; i < Xdata.size(); i++) {
                for (int i1 = 0; i1 < Xdata.get(i).length; i1++) {
                    //判断值是否为0，为0就不绘制
//                if(Xdata.get(i)[i1]!=0){
                    //绘制圆环之间的线，从第二个环开始绘制
                    if (i1 > 1&&i1 != Xdata.get(i).length-1) {
                        int XdataStrat = getYValue(Xdata.get(i)[i1]);
                        //动态加载
                        if (isDynamic) {
                            if (dataStatHeightList.get(i)[i1] > getYValue(Xdata.get(i)[i1])) {
                                dataStatHeightList.get(i)[i1] -= dynamicValue;
                                XdataStrat = dataStatHeightList.get(i)[i1];
                                postInvalidateDelayed(dynamicTime);
                            }
                        }
                        if(dataPointLineColor!=null){
                            mPaint.setColor(dataPointLineColor.get(i)[i1 - 1]);
                        }
                        mPaint.setStrokeWidth(adaptationTool.setCanvasAdaptation(lineWidth));
                        int XdataStrat2 = getYValue(Xdata.get(i)[i1 - 1]);
                        //动态加载
                        if (isDynamic) {
                            if (dataStatHeightList.get(i)[i1 - 1] > getYValue(Xdata.get(i)[i1 - 1])) {
                                dataStatHeightList.get(i)[i1 - 1] -= dynamicValue;
                                XdataStrat2 = dataStatHeightList.get(i)[i1 - 1];
                                postInvalidateDelayed(dynamicTime);
                            }
                        }

                        mCanvas.drawLine(DataXY[i1 - 1], XdataStrat2, DataXY[i1], XdataStrat, mPaint);
                    }
//                }
                }
            }
        }
        //绘制圆
        if(Xdata!=null) {
            for (int i = 0; i < Xdata.size(); i++) {
                for (int i1 = 0; i1 < Xdata.get(i).length; i1++) {
                    //判断值是否为0，为0就不绘制
                    if (i1 != 0 && Xdata.get(i)[i1] != 0) {
                        int XdataStrat = getYValue(Xdata.get(i)[i1]);
                        //动态加载
                        if (isDynamic) {
                            if (dataStatHeightList.get(i)[i1] > getYValue(Xdata.get(i)[i1])) {
                                dataStatHeightList.get(i)[i1] -= dynamicValue;
                                XdataStrat = dataStatHeightList.get(i)[i1];
                                postInvalidateDelayed(dynamicTime);
                            }
                        }

                        //是否传入圆环的颜色集合
                        if (dataPointColor != null && dataPointColor.get(i).length > 0) {
                            //设置圆环的颜色
                            mPaint.setColor(dataPointColor.get(i)[i1]);
                            //判断圆环是否为空心
                            if (dataPointIsHollow != null && dataPointIsHollow.get(i).length > 0) {
                                if (dataPointIsHollow.get(i)[i1]) {
                                    mCanvas.drawCircle(DataXY[i1], XdataStrat, adaptationTool.setCanvasAdaptation(circularRadius), mPaint);// 圆
                                    mPaint.setColor(Color.WHITE);
                                    //绘制白色的空心
                                    mCanvas.drawCircle(DataXY[i1], XdataStrat, adaptationTool.setCanvasAdaptation(circularRadius) - adaptationTool.setCanvasAdaptation(circularWight), mPaint);
                                } else {
                                    mCanvas.drawCircle(DataXY[i1], XdataStrat, adaptationTool.setCanvasAdaptation(circularRadius), mPaint);
                                }
                            } else {
                                mCanvas.drawCircle(DataXY[i1], XdataStrat, adaptationTool.setCanvasAdaptation(circularRadius), mPaint);
                            }
                        } else {
                            mCanvas.drawCircle(DataXY[i1], XdataStrat, adaptationTool.setCanvasAdaptation(circularRadius), mPaint);
                        }
                        //绘制圆上面的text
                        if (dataText) {
                            setDataText(DataXY[i1], XdataStrat, Xdata.get(i)[i1], adaptationTool.setCanvasAdaptation(circularRadius), dataTextColor, adaptationTool.setCanvasAdaptation(dataTextSize));
                        }
                    }
                }
            }
        }
        //绘制title
        if(titleRight){
            if(titleRightList!=null){
//                mPaint = new Paint();
//                mPaint.setColor(Color.RED);
//                mCanvas.drawLine(0,adaptationTool.setCanvasAdaptation(getTitleHeight()/2),mWidth,adaptationTool.setCanvasAdaptation(getTitleHeight()/2),mPaint);
//                mPaint.setStyle(Paint.Style.STROKE);
//                Path path = new Path();
//                path.moveTo(0,0);
//                path.lineTo(mWidth,0);
//                path.lineTo(mWidth,adaptationTool.setCanvasAdaptation(getTitleHeight()));
//                path.lineTo(0,adaptationTool.setCanvasAdaptation(getTitleHeight()));
//                path.close();
//                mCanvas.drawPath(path, mPaint);
                int titleRightRightDistanceStart = getmWidth() - adaptationTool.setCanvasAdaptation(titleRightRightDistance);
                for (HashMap<String, Object> stringObjectHashMap : titleRightList) {
                    String text = String.valueOf(stringObjectHashMap.get("text"));
                    String hollow = String.valueOf(stringObjectHashMap.get("hollow"));
                    int color = (Integer)stringObjectHashMap.get("color");
                    mPaint = new Paint();
                    mPaint.setAntiAlias(true);
                    mPaint.setTextSize(adaptationTool.setCanvasAdaptation(titleRightTextSize));
                    mPaint.setColor(titleRightTextColor);
                    titleRightRightDistanceStart -= getTextWH(text,mPaint).width();
                    mCanvas.drawText(text, titleRightRightDistanceStart, adaptationTool.getCanvasTextHeight(mPaint)/2+ adaptationTool.setCanvasAdaptation(getTitleHeights())/2,mPaint);
                    titleRightRightDistanceStart -= adaptationTool.setCanvasAdaptation(titleRightTextRadiusDistance)+ adaptationTool.setCanvasAdaptation(circularRadius);

                    mPaint = new Paint();
                    mPaint.setAntiAlias(true);
                    mPaint.setStyle(Paint.Style.FILL);//设置填满
                    mPaint.setAntiAlias(true);// 设置画笔的锯齿效果。 true是去除，大家一看效果就明白了
                    mPaint.setColor(color);
                    mCanvas.drawCircle(titleRightRightDistanceStart+ adaptationTool.setCanvasAdaptation(circularRadius), adaptationTool.setCanvasAdaptation(getTitleHeight())/2, adaptationTool.setCanvasAdaptation(circularRadius), mPaint);
                    if(hollow.equals("true")){
                        mPaint.setColor(Color.WHITE);
                        mCanvas.drawCircle(titleRightRightDistanceStart+ adaptationTool.setCanvasAdaptation(circularRadius), adaptationTool.setCanvasAdaptation(getTitleHeight())/2, adaptationTool.setCanvasAdaptation(circularRadius)- adaptationTool.setCanvasAdaptation(circularWight), mPaint);
                    }
                    titleRightRightDistanceStart -= adaptationTool.setCanvasAdaptation(circularRadius)+ adaptationTool.setCanvasAdaptation(titleRightTextRadiusDistance);
                }
            }
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(Xdata!=null){
            dataStatHeightList = new ArrayList<>();
            for (int i = 0; i < Xdata.size(); i++) {
                int[] dataStatHeight = new int[Xdata.get(i).length];
                for (int i1 = 0; i1 < Xdata.get(i).length; i1++) {
                    dataStatHeight[i1] = Y;
                }
                dataStatHeightList.add(dataStatHeight);
            }
        }
    }

    public SpotLineView(Context context) {
        super(context);
    }

    public SpotLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SpotLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 设置title右边的数据
     * @param titleRightList
     */
    public void setTitleRightData(List<HashMap<String, Object>> titleRightList){
        this.titleRightList = titleRightList;
    }
    /**
     * 设置数据点是否为空心集合
     * @param listDataPointIsHollow
     */
    public void setDataPointIsHollow(List<Boolean[]> listDataPointIsHollow){
        dataPointIsHollow = listDataPointIsHollow;
    }
    /**
     * 设置数据点后面的线的颜色集合
     * @param listDataPointLineColor
     */
    public void setDataPointLineColor(List<int[]> listDataPointLineColor){
        dataPointLineColor = listDataPointLineColor;
    }
    /**
     * 设置数据点颜色集合
     * @param listDataPointColor
     */
    public void setDataPointColor(List<int[]> listDataPointColor){
        dataPointColor = listDataPointColor;
    }
    /**
     * 设置X轴的数据
     * @param data
     */
    public void setXData(List<int[]> data){
        Xdata = data;
    }
    /**
     * 设置目标范围
     * @param range
     */
    public void setTargetRange(List<int[]> range){
        targetRange = range;
    }
    /**
     * 设置目标范围颜色
     * @param color
     */
    public void setTargetRangeColor(int[] color){
        targetRangeColor = color;
    }

    /**
     * 设置X轴的text
     * @param text
     */
    public void setXText(String[] text){
        XText = text;
    }

    public int getCircularColor() {
        return circularColor;
    }

    public void setCircularColor(int circularColor) {
        this.circularColor = circularColor;
    }

    public int getCircularInsideColor() {
        return circularInsideColor;
    }

    public void setCircularInsideColor(int circularInsideColor) {
        this.circularInsideColor = circularInsideColor;
    }

    public int getCircularWight() {
        return circularWight;
    }

    public void setCircularWight(int circularWight) {
        this.circularWight = circularWight;
    }

    public int getCircularRadius() {
        return circularRadius;
    }

    public void setCircularRadius(int circularRadius) {
        this.circularRadius = circularRadius;
    }

    public int getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
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

    public int getTargetRangeAlpha() {
        return targetRangeAlpha;
    }

    public void setTargetRangeAlpha(int targetRangeAlpha) {
        this.targetRangeAlpha = targetRangeAlpha;
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

    public int getTargetRangeRectangleMax() {
        return targetRangeRectangleMax;
    }

    public void setTargetRangeRectangleMax(int targetRangeRectangleMax) {
        this.targetRangeRectangleMax = targetRangeRectangleMax;
    }

    public int getTargetRangeRectangleMin() {
        return targetRangeRectangleMin;
    }

    public void setTargetRangeRectangleMin(int targetRangeRectangleMin) {
        this.targetRangeRectangleMin = targetRangeRectangleMin;
    }

    public int getTargetRangeRectangleColor() {
        return targetRangeRectangleColor;
    }

    public void setTargetRangeRectangleColor(int targetRangeRectangleColor) {
        this.targetRangeRectangleColor = targetRangeRectangleColor;
    }

    public int getTargetRangeRectangleAlpha() {
        return targetRangeRectangleAlpha;
    }

    public void setTargetRangeRectangleAlpha(int targetRangeRectangleAlpha) {
        this.targetRangeRectangleAlpha = targetRangeRectangleAlpha;
    }
}
