package com.jiage.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;

import com.jiage.R;
import com.jiage.util.JiaUtil;


/**
 * 作者：李忻佳.
 * 时间：2016/12/1.
 * 说明：饼状图 指针
 */

public class PieChartPointerView extends BaseDrawCakeView {

    /**
     * 半径
     */
    private int radius = 200;
    /**
     * 圆环的宽
     */
    private int ringWidth = 50;
    /**
     * 圆环开口角度
     */
    private int openingAngle = 80;
    /**
     * text 间隔 环
      */
    private int textIntervalRing = 15;
    /**
     * text 字体颜色
     */
    private int textColor = Color.parseColor("#46B3E7");
    /**
     * text 字体大小
     */
    private int textSize = 40;
    /**
     * 指针数据字体大小
     */
    private int pointerDatatextSize = 55;
    /**
     * 数据描述字体大小
     */
    private int dataDescribeTextSize = 100;
    /**
     * 数据描述离圆点下方横线的距离
     */
    private int dataDescribeBotton = 20;
    /**
     * 数据描述下方的横线的长度
     */
    private int dataDescribeLenghtLineDescribe = 300;
    /**
     * 数据描述下方的横线离数圆点的距离
     */
    private int dataDescribeBottonLineDescribe = 200;
    /**
     * 数据描述的颜色
     */
    private int dataDescribeColor = Color.parseColor("#F2F2F2");
    /**
     * 指针开始旋转的数据
     */
    private double pointerStartData = 0;
    /**
     * 指针数据
     */
    private String pointerData = "0";
    /**
     * 是否动态加载
     */
    private boolean isDynamic = false;
    /**
     * 动态加载的增长值
     */
    private int dynamicValue = 1;
    /**
     * 动态加载的毫秒数
     */
    private int dynamicTime = 10;
    /**
     * title
     */
    private String title = "BMI";
    /**
     * title 字体颜色
     */
    private int titleColor =  Color.parseColor("#E6E6E6");
    /**
     * title 字体大小
     */
    private int titleSize = 80;
    /**
     * title2 离圆点的距离
     */
    private int titleOrigin = 120;
    /**
     * title2
     */
    private String title2 = "健康。体重";
    /**
     * title2 字体颜色
     */
    private int title2Color = Color.parseColor("#A5A5A5");
    /**
     * title2 字体大小
     */
    private int title2Size = 60;

    /**
     * title2 离横线的距离
     */
    private int title2Line = 10;


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
    /**
     * 设置title右边字体的颜色
     */
    private int titleRightTextColor = Color.GRAY;

    private String[] mData;
    private float dataTotal = 0;
    private int[] mDataColor;
    private float[] dataAngle;
    private String[] mDataDescribe;
    private String pointerDataString = "0";

    public PieChartPointerView(Context context) {
        super(context);
    }

    public PieChartPointerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PieChartPointerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init(Context context) {
        super.init(context);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setTextSize(adaptationTool.setCanvasAdaptation(textSize));
        if(mDataDescribe!=null){
            mPaint.setTextAlign(Paint.Align.RIGHT);
            int titleRightRightDistanceStart = getWidth() - adaptationTool.setCanvasAdaptation(titleRightRightDistance);
            for (int i = mDataDescribe.length - 1; i >= 0; i--) {
                mPaint.setColor(titleRightTextColor);
                mCanvas.drawText(mDataDescribe[i],titleRightRightDistanceStart,getTitleHeights()/2+ adaptationTool.setCanvasAdaptation(10),mPaint);
                titleRightRightDistanceStart -= getTextWH(mDataDescribe[i],mPaint).width()+ adaptationTool.setCanvasAdaptation(titleRightTextRadiusDistance);
                if(mDataColor!=null){
                    if(i<mDataColor.length){
                        mPaint.setColor(mDataColor[i]);
                    }
                }
                mCanvas.drawCircle(titleRightRightDistanceStart, getTitleHeights()/2, adaptationTool.setCanvasAdaptation(15), mPaint);// 圆
                titleRightRightDistanceStart -= adaptationTool.setCanvasAdaptation(20)+ adaptationTool.setCanvasAdaptation(titleRightTextRadiusDistance);
            }
        }

        if(pointerData.equals("0")){
            try {
                pointerData = pointerDataString.equals("0")?"0":pointerDataString;;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        //是否动态加载柱子
        if(!isDynamic){
            pointerStartData = Double.parseDouble(pointerData);
        }
        double jiao = ((360 - openingAngle) * (pointerStartData-Double.parseDouble(mData[0])) / dataTotal)+openingAngle/2;
        if(jiao<0){
            jiao = 0;
        }
        if(jiao>360-openingAngle){
            jiao = 360-openingAngle;
        }
        if(getOriginX()>getOriginY()){
            radius = getOriginY() - adaptationTool.setCanvasAdaptation(leftRightDistace)-getTitleHeights()/2+ adaptationTool.setCanvasAdaptation(20);
        }else{
            radius = getOriginX() - adaptationTool.setCanvasAdaptation(leftRightDistace)-getTitleHeights()/2+ adaptationTool.setCanvasAdaptation(20);
        }
        int x = getOriginX();
        int y = getOriginY()+getTitleHeights()/2;
        mPaint.setColor(Color.BLACK);
        // 设置个新的长方形，扫描测量
        RectF oval = new RectF(x-radius, y-radius, x+radius, y+radius);
        for (int i = 0; i < mData.length; i++) {
            if(mDataColor != null){
                if(i<mDataColor.length){
                    mPaint.setColor(mDataColor[i]);
                }
            }
            // 画弧，第一个参数是RectF：该类是第二个参数是角度的开始，第三个参数是多少度，第四个参数是真的时候画扇形，是假的时候画弧线
            if(i<mData.length-1){
                mCanvas.drawArc(oval, dataAngle[i], dataAngle[i+1]-dataAngle[i], true, mPaint);
            }
//            if(pointerStartData!=mData[i]){
                //画弧对应的text
                mPaint.setColor(textColor);
                mPaint.setTextSize(adaptationTool.setCanvasAdaptation(textSize));
                mPaint.setTextAlign(Paint.Align.CENTER);
                drawRotateText(mCanvas,mData[i]+"",(int)getCircleCoordinatesX(x,-(dataAngle[i]-90),radius+ adaptationTool.setCanvasAdaptation(textIntervalRing)),
                        (int)getCircleCoordinatesY(y,-(dataAngle[i]-90),radius+ adaptationTool.setCanvasAdaptation(textIntervalRing)),mPaint,(dataAngle[i]-90)+180);
//            }
        }
        //画中间白色的圆
        mPaint.setColor(Color.WHITE);
        mCanvas.drawCircle(x, y, radius- adaptationTool.setCanvasAdaptation(ringWidth), mPaint);// 小圆
        //画环开始和结尾的圆
        mPaint.setColor(mDataColor[0]);
        mCanvas.drawCircle((int)getCircleCoordinatesX(x,-openingAngle/2,(radius- adaptationTool.setCanvasAdaptation(ringWidth/2))),
                (int)getCircleCoordinatesY(y,-openingAngle/2,(radius- adaptationTool.setCanvasAdaptation(ringWidth/2))), adaptationTool.setCanvasAdaptation(ringWidth/2), mPaint);
        mPaint.setColor(mDataColor[mDataColor.length-1]);
        mCanvas.drawCircle((int)getCircleCoordinatesX(x,openingAngle/2,(radius- adaptationTool.setCanvasAdaptation(ringWidth/2))),
                (int)getCircleCoordinatesY(y,openingAngle/2,(radius- adaptationTool.setCanvasAdaptation(ringWidth/2))), adaptationTool.setCanvasAdaptation(ringWidth/2), mPaint);
        //title
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setColor(titleColor);
        mPaint.setTextSize(adaptationTool.setCanvasAdaptation(titleSize));
        mCanvas.drawText(title,x,y- adaptationTool.setCanvasAdaptation(titleOrigin),mPaint);

        //画图片，就是贴图
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_pointer);
        Double d_s=new Double(jiao);
        Bitmap bitmap1 = resizeAndRotateImage(bitmap,radius*2,radius*2,d_s.intValue());
        mCanvas.drawBitmap(bitmap1,x-bitmap1.getWidth()/2,y-bitmap1.getHeight()/2,mPaint);

        mPaint.setColor(dataDescribeColor);
        mPaint.setStrokeWidth(adaptationTool.setCanvasAdaptation(3));
        mCanvas.drawLine(x- adaptationTool.setCanvasAdaptation(dataDescribeLenghtLineDescribe)/2,y+ adaptationTool.setCanvasAdaptation(dataDescribeBottonLineDescribe),
                x+ adaptationTool.setCanvasAdaptation(dataDescribeLenghtLineDescribe)/2,y+ adaptationTool.setCanvasAdaptation(dataDescribeBottonLineDescribe),mPaint);

        mPaint.setTextAlign(Paint.Align.CENTER);
        for (int i = 0; i < dataAngle.length; i++) {
            if(dataAngle[i]>jiao+90) {
                if(mDataDescribe!=null&&i!=0) {
                    mPaint.setColor(mDataColor[i-1]);
                    mPaint.setTextSize(adaptationTool.setCanvasAdaptation(title2Size));
                    mCanvas.drawText(mDataDescribe[i-1], x, y + adaptationTool.setCanvasAdaptation(dataDescribeBottonLineDescribe) + adaptationTool.setCanvasAdaptation(title2Line) + getTextWH(mDataDescribe[i-1], mPaint).height(), mPaint);
                    mPaint.setTextSize(adaptationTool.setCanvasAdaptation(dataDescribeTextSize));
                    mCanvas.drawText(JiaUtil.deleteDecimal(pointerStartData),x,y+ adaptationTool.setCanvasAdaptation(dataDescribeBottonLineDescribe)- adaptationTool.setCanvasAdaptation(dataDescribeBotton),mPaint);
                }
                break;
            }
            if(dataAngle[i]==jiao+90) {
                if(mDataDescribe!=null) {
                    if(i == dataAngle.length-1&&i!=0){
                        mPaint.setColor(mDataColor[i-1]);
                        mPaint.setTextSize(adaptationTool.setCanvasAdaptation(title2Size));
                        mCanvas.drawText(mDataDescribe[i-1], x, y + adaptationTool.setCanvasAdaptation(dataDescribeBottonLineDescribe) + adaptationTool.setCanvasAdaptation(title2Line) + getTextWH(mDataDescribe[i-1], mPaint).height(), mPaint);
                        mPaint.setTextSize(adaptationTool.setCanvasAdaptation(dataDescribeTextSize));
                        mCanvas.drawText(JiaUtil.deleteDecimal(pointerStartData)+"",x,y+ adaptationTool.setCanvasAdaptation(dataDescribeBottonLineDescribe)- adaptationTool.setCanvasAdaptation(dataDescribeBotton),mPaint);
                    }else{
                        mPaint.setColor(mDataColor[i]);
                        mPaint.setTextSize(adaptationTool.setCanvasAdaptation(title2Size));
                        mCanvas.drawText(mDataDescribe[i], x, y + adaptationTool.setCanvasAdaptation(dataDescribeBottonLineDescribe) + adaptationTool.setCanvasAdaptation(title2Line) + getTextWH(mDataDescribe[i], mPaint).height(), mPaint);
                        mPaint.setTextSize(adaptationTool.setCanvasAdaptation(dataDescribeTextSize));
                        mCanvas.drawText(JiaUtil.deleteDecimal(pointerStartData)+"",x,y+ adaptationTool.setCanvasAdaptation(dataDescribeBottonLineDescribe)- adaptationTool.setCanvasAdaptation(dataDescribeBotton),mPaint);
                    }
                }
                break;
            }
        }

////        //画指针对应的字体
//        mPaint.setTextSize(adaptationTool.setCanvasAdaptation(pointerDatatextSize));
//        drawRotateText(mCanvas,pointerStartData+"",(int)getCircleCoordinatesX(x,-jiao,radius+adaptationTool.setCanvasAdaptation(textIntervalRing)+adaptationTool.setCanvasAdaptation(10)),
//                (int)getCircleCoordinatesY(y,-jiao,radius+adaptationTool.setCanvasAdaptation(textIntervalRing)),mPaint,jiao+180);
        //动态加载
        if(isDynamic){
            if(pointerStartData<Double.parseDouble(pointerData)){
                pointerStartData += dynamicValue;
                Log.d("PieChartPointer",pointerStartData+"");
                postInvalidateDelayed(dynamicTime);
            }
        }
    }

    /**
     * 设置数据
     * @param data
     */
    public void setData(String[] data){
        mData = data;
        dataAngle = new float[data.length];
        dataTotal = Float.parseFloat(data[data.length-1])-Float.parseFloat(data[0]);
        for (int i = 0; i < data.length; i++) {
            if(i != 0){
                dataAngle[i] = ((360 - openingAngle) * (Float.parseFloat(data[i])-Float.parseFloat(data[0])) / dataTotal)+(90+openingAngle/2);
            }else{
                dataAngle[i] = 90+openingAngle/2;
            }
        }
        pointerStartData = Double.parseDouble(mData[0]);
    }
    /**
     * 设置指针对应的数据
     * @param data
     */
    public void setPointerData(String data){
        pointerDataString = data;
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
     * @param dataDescribe
     */
    public void setDataDescribe(String[] dataDescribe){
        mDataDescribe = dataDescribe;
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

    public int getOpeningAngle() {
        return openingAngle;
    }

    public void setOpeningAngle(int openingAngle) {
        this.openingAngle = openingAngle;
    }

    public int getTextIntervalRing() {
        return textIntervalRing;
    }

    public void setTextIntervalRing(int textIntervalRing) {
        this.textIntervalRing = textIntervalRing;
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

    public boolean isDynamic() {
        return isDynamic;
    }

    public void setDynamic(boolean dynamic) {
        isDynamic = dynamic;
    }

    public int getPointerDatatextSize() {
        return pointerDatatextSize;
    }

    public void setPointerDatatextSize(int pointerDatatextSize) {
        this.pointerDatatextSize = pointerDatatextSize;
    }

    public int getDataDescribeTextSize() {
        return dataDescribeTextSize;
    }

    public void setDataDescribeTextSize(int dataDescribeTextSize) {
        this.dataDescribeTextSize = dataDescribeTextSize;
    }

    public int getDataDescribeBotton() {
        return dataDescribeBotton;
    }

    public void setDataDescribeBotton(int dataDescribeBotton) {
        this.dataDescribeBotton = dataDescribeBotton;
    }

    public int getDataDescribeLenghtLineDescribe() {
        return dataDescribeLenghtLineDescribe;
    }

    public void setDataDescribeLenghtLineDescribe(int dataDescribeLenghtLineDescribe) {
        this.dataDescribeLenghtLineDescribe = dataDescribeLenghtLineDescribe;
    }

    public int getDataDescribeBottonLineDescribe() {
        return dataDescribeBottonLineDescribe;
    }

    public void setDataDescribeBottonLineDescribe(int dataDescribeBottonLineDescribe) {
        this.dataDescribeBottonLineDescribe = dataDescribeBottonLineDescribe;
    }

    public int getDataDescribeColor() {
        return dataDescribeColor;
    }

    public void setDataDescribeColor(int dataDescribeColor) {
        this.dataDescribeColor = dataDescribeColor;
    }

    public int getDynamicTime() {
        return dynamicTime;
    }

    public void setDynamicTime(int dynamicTime) {
        this.dynamicTime = dynamicTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTitleColor() {
        return titleColor;
    }

    public void setTitleColor(int titleColor) {
        this.titleColor = titleColor;
    }

    public int getTitleSize() {
        return titleSize;
    }

    public void setTitleSize(int titleSize) {
        this.titleSize = titleSize;
    }

    public int getTitleOrigin() {
        return titleOrigin;
    }

    public void setTitleOrigin(int titleOrigin) {
        this.titleOrigin = titleOrigin;
    }

    public String getTitle2() {
        return title2;
    }

    public void setTitle2(String title2) {
        this.title2 = title2;
    }

    public int getTitle2Color() {
        return title2Color;
    }

    public void setTitle2Color(int title2Color) {
        this.title2Color = title2Color;
    }

    public int getTitle2Size() {
        return title2Size;
    }

    public void setTitle2Size(int title2Size) {
        this.title2Size = title2Size;
    }

    public int getTitle2Line() {
        return title2Line;
    }

    public void setTitle2Line(int title2Line) {
        this.title2Line = title2Line;
    }
}
