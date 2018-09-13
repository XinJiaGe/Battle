package com.jiage.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;

import java.io.InputStream;
import java.math.BigDecimal;

/**
 * 作者：李忻佳.
 * 时间：2016/11/17.
 * 说明：网格BaseView
 */

public class BaseDrawGridView extends BaseDrawView {
    /**
     * 坐标原点X
     */
    public int X;
    /**
     * 坐标原点Y
     */
    public int Y;
    /**
     * 是否绘制XY轴
     */
    private boolean isXY = false;
    /**
     * XY轴线条粗细
     */
    private int XYWidth = 3;
    /**
     * X轴颜色
     */
    private int XColor = Color.BLACK;
    /**
     * Y轴颜色
     */
    private int YColor = Color.BLACK;
    /**
     * X轴字体大小
     */
    private int XTextSize = 50;
    /**
     * X轴字体颜色
     */
    private int XTextColor = Color.GRAY;
    /**
     * Y轴字体大小
     */
    private int YTextSize = 40;
    /**
     * Y轴字体颜色
     */
    private int YTextColor = Color.GRAY;
    /**
     * Y轴最大值
     */
    private double YValueMax = 200;
    /**
     * Y轴最小值
     */
    private int YValueMin = 0;
    /**
     * 数据坐标离左边宽度
     */
    private int DataXLeftlength = 0;
    /**
     * 数据坐标离右边宽度
     */
    private int DataXRightlength = 0;
    /**
     * 是否显示X轴上的网格
     */
    private boolean isgridX = true;
    /**
     * 是否显示Y轴上的网格
     */
    private boolean isgridY = true;
    /**
     * 网格的类型（实线和虚线）
     */
    private GridType gridType = GridType.EMPTY;
    /**
     * 网格线条宽度
     */
    private int gridWidth = 1;
    /**
     * 网格颜色
     */
    private int gridColor = Color.GRAY;
    /**
     * 网格虚线向下延伸超过最上面的一条网格长度
     */
    private int gridUpExceedgridYFromTop = 20;
    /**
     * 网格虚线向上延伸超过X长度
     */
    private int gridDownExceedX = 5;
    /**
     * X轴上的text 里X轴距离
     */
    private int textXDistance = 0;
    /**
     * Y轴上的网格线条数，包含坐标轴
     */
    private int gridYNumber = 5;
    /**
     * Y轴上的网格的最上面的一条网格离上的距离
     */
    private int gridYFromTop = getTitleHeight() + 50;

    /**
     * 是否显示X轴上的text
     */
    private boolean isTextX = true;
    /**
     * 是否显示Y轴上的text
     */
    private boolean isTextY = true;
    /**
     * Y轴下的text右边的图片的宽度
     */
    private int YTextImgWidth = 40;
    /**
     * Y轴下的text右边的图片的高度
     */
    private int YTextImgHeight = 40;
    /**
     * 是否绘制网格类型的Y轴
     */
    private boolean isYGridType = true;
    /**
     * 空数据时的提示
     */
    private String nullText = "数据为空";
    /**
     * 空数据时的提示的字体颜色
     */
    private int nullTextColor = Color.RED;
    /**
     * 空数据时的提示的字体大小
     */
    private int nullTextSize = 50;
    /**
     * Y轴描述是否已double形式呈现
     */
    private boolean YisDouble = false;
    /**
     * 设置Y轴单位
     */
    private String YCompanyText = "";
    /**
     * 设置Y轴单位颜色
     */
    private int YCompanyColor = Color.GRAY;
    /**
     * 设置Y轴单位字体大小
     */
    private int YCompanySize = 40;

    private String TAG = "BaseDrawView";
    private int[] XTextX;
    public Context mContext;
    private Boolean[] GridSpecificationList;
    private int[] YtextHeight;
    private int[] textImages;
    private DashPathEffect pathEffect;
    private boolean isBeforeGrid = true;
    private int YZeroTextIndex = 0;
    private double YValueMaxDouble;


    public BaseDrawGridView(Context context) {
        super(context);
    }

    public BaseDrawGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseDrawGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvasNull();
        canvasCoordinate();
    }

    /**
     * 绘制空数据时的提示
     */
    private void canvasNull() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(adaptationTool.setCanvasAdaptation(nullTextSize));
        mPaint.setColor(nullTextColor);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mCanvas.drawText(nullText,mWidth/2,mHeight/2,mPaint);
    }

    /**
     * 绘制Y轴单位
     */
    private void setYCompany(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(adaptationTool.setCanvasAdaptation(YCompanySize));
        mPaint.setColor(YCompanyColor);
        mCanvas.drawText(getYCompanyText(), adaptationTool.setCanvasAdaptation(10), adaptationTool.setCanvasAdaptation(getTitleHeight())+ adaptationTool.setCanvasAdaptation(getTitleBotCompanyHeight()/2)+ adaptationTool.setCanvasAdaptation(20),mPaint);
    }
    /**
     * 设置XY轴上的text
     */
    private void setText(String[] xtext){
        if(isTextX){
            if(XTextX == null){
                Log.e(TAG,"请先执行 calculationXCoordinate 然后计算X轴的数据坐标");
            }else{
                mPaint = new Paint();
                mPaint.setAntiAlias(true);
                mPaint.setTextSize(adaptationTool.setCanvasAdaptation(XTextSize));
                mPaint.setColor(XTextColor);
                mPaint.setTextAlign(Paint.Align.CENTER);
                for (int i = 0; i < XTextX.length; i++) {
                    mCanvas.drawText(xtext[i],XTextX[i],Y+ adaptationTool.setCanvasAdaptation(gridDownExceedX)+ adaptationTool.setCanvasAdaptation(textXDistance)+getTextWH(xtext[i],mPaint).height(),mPaint);
                    if(textImages != null){
                        if(xtext.length == textImages.length){
                            if(textImages[i]!=0){
                                InputStream is = getResources().openRawResource(textImages[i]);
                                Bitmap mBitmap = BitmapFactory.decodeStream(is);
                                drawImage(mCanvas,mBitmap,(getTextWH(xtext[i],mPaint).width()/2)+XTextX[i]+ adaptationTool.setCanvasAdaptation(5),
                                        Y+ adaptationTool.setCanvasAdaptation(gridDownExceedX)- adaptationTool.setCanvasAdaptation(5), adaptationTool.setCanvasAdaptation(YTextImgWidth), adaptationTool.setCanvasAdaptation(YTextImgHeight),0,0);
                            }
                        }else{
                            Log.e(TAG,"图片数据不能不和text数据相同");
                        }
                    }
                }
            }
        }
        if(isTextY){
            if(XTextX == null){
                Log.e(TAG,"请先执行 calculationXCoordinate 计算X轴的数据坐标");
            }else{
                if(YtextHeight != null){
                    mPaint = new Paint();
                    mPaint.setAntiAlias(true);
                    mPaint.setTextSize(adaptationTool.setCanvasAdaptation(YTextSize));
                    mPaint.setColor(YTextColor);
                    double YMaxA = 0;

                    if(YValueMin<0){
                        YMaxA = (YValueMax + Math.abs(YValueMax))/(gridYNumber-1);
                    }else{
                        YMaxA = YValueMax/(gridYNumber-1);
                    }
                    double YMaxStart = YValueMin;
                    for (int i = 0; i < YtextHeight.length; i++) {
                        if(i!=0&&YMaxStart==0){
                            YZeroTextIndex = i;
                        }
                        if(i==0&&YMaxStart==0){

                        }else{
                            if(YisDouble){
                                BigDecimal b = new BigDecimal(YMaxStart);
                                double f1 = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
                                if(isgridY){
                                    mCanvas.drawText(String.valueOf(f1),X-getTextWH(String.valueOf(f1),mPaint).width()- adaptationTool.setCanvasAdaptation(15),YtextHeight[i]+getTextWH(String.valueOf(f1),mPaint).height()+5,mPaint);
                                }else{
                                    mCanvas.drawText(String.valueOf(f1),X-getTextWH(String.valueOf(f1),mPaint).width()- adaptationTool.setCanvasAdaptation(15),YtextHeight[i]+getTextWH(String.valueOf(f1),mPaint).height()/2,mPaint);
                                }
                            }else{
                                if(isgridY){
                                    mCanvas.drawText(String.valueOf((int)YMaxStart),X-getTextWH(String.valueOf((int)YMaxStart),mPaint).width()- adaptationTool.setCanvasAdaptation(15),YtextHeight[i]+getTextWH(String.valueOf((int)YMaxStart),mPaint).height()+5,mPaint);
                                }else{
                                    mCanvas.drawText(String.valueOf((int)YMaxStart),X-getTextWH(String.valueOf((int)YMaxStart),mPaint).width()- adaptationTool.setCanvasAdaptation(15),YtextHeight[i]+getTextWH(String.valueOf((int)YMaxStart),mPaint).height()/2,mPaint);
                                }
                            }
                        }
                        YMaxStart = YMaxStart + YMaxA;
                    }
                }
            }
        }
    }
    /**
     * 设置网格
     */
    public void setGrid() {
        if (isgridX) {
            if (XTextX == null) {
                Log.e(TAG, "请先执行 calculationXCoordinate 计算X轴的数据坐标");
            } else {
                mPaint = new Paint();
                if(gridType == GridType.REAL){
                    pathEffect = new DashPathEffect(new float[]{5, 0}, 1);
                }else if(gridType == GridType.EMPTY){
                    pathEffect = new DashPathEffect(new float[]{4, 7}, 1);
                }else{
                    pathEffect = new DashPathEffect(new float[]{4, 7}, 1);
                }
                mPaint.reset();
                mPaint.setStyle(Paint.Style.STROKE);
                mPaint.setStrokeWidth(adaptationTool.setCanvasAdaptation(gridWidth));
                mPaint.setColor(gridColor);
                mPaint.setAntiAlias(true);
                mPaint.setPathEffect(pathEffect);

                for (int i = 0; i < XTextX.length; i++) {
                    if (GridSpecificationList == null) {
                        Path path = new Path();
                        path.moveTo(XTextX[i], Y + adaptationTool.setCanvasAdaptation(gridDownExceedX));
                        path.lineTo(XTextX[i], adaptationTool.setCanvasAdaptation(gridYFromTop) - adaptationTool.setCanvasAdaptation(gridUpExceedgridYFromTop));
                        mCanvas.drawPath(path, mPaint);
                    } else {
                        if (GridSpecificationList.length != XTextX.length) {
                            Log.d(TAG, "网格规格长度和计算X轴的数据坐标长度不相等");
                            Path path = new Path();
                            path.moveTo(XTextX[i], Y + adaptationTool.setCanvasAdaptation(gridDownExceedX));
                            path.lineTo(XTextX[i], adaptationTool.setCanvasAdaptation(gridYFromTop) - adaptationTool.setCanvasAdaptation(gridUpExceedgridYFromTop));
                            mCanvas.drawPath(path, mPaint);
                        } else {
                            if (GridSpecificationList[i]) {
                                Path path = new Path();
                                path.moveTo(XTextX[i], Y + adaptationTool.setCanvasAdaptation(gridDownExceedX));
                                path.lineTo(XTextX[i], adaptationTool.setCanvasAdaptation(gridYFromTop) - adaptationTool.setCanvasAdaptation(gridUpExceedgridYFromTop));
                                mCanvas.drawPath(path, mPaint);
                            }
                        }
                    }
                }
            }
        }
        if (XTextX == null) {
            Log.e(TAG, "请先执行 calculationXCoordinate 计算X轴的数据坐标");
        } else {
            int start = 0;
            int end = mWidth;
            int yGridLengthA = (Y - adaptationTool.setCanvasAdaptation(gridYFromTop)) / (gridYNumber - 1);
            int startHeight = Y;
            YtextHeight = new int[gridYNumber];
            for (int i = 0; i < gridYNumber; i++) {
                YtextHeight[i] = startHeight;
                if (isgridY) {
                    Path path = new Path();
                    path.moveTo(start, startHeight);
                    path.lineTo(end, startHeight);
                    mCanvas.drawPath(path, mPaint);
                }
                if(YZeroTextIndex!=0){
                    if(i == YZeroTextIndex){
                        Path path = new Path();
                        path.moveTo(start+X, startHeight);
                        path.lineTo(end, startHeight);
                        mCanvas.drawPath(path, mPaint);
                    }
                }
                startHeight = startHeight - yGridLengthA;
            }
        }
        if(isYGridType){
            Path path = new Path();
            path.moveTo(X, Y + adaptationTool.setCanvasAdaptation(gridDownExceedX));
            path.lineTo(X, adaptationTool.setCanvasAdaptation(gridYFromTop) - adaptationTool.setCanvasAdaptation(gridUpExceedgridYFromTop));
            mCanvas.drawPath(path, mPaint);
        }
    }


    /**
     * 计算X轴的数据坐标
     * @param xtext     String[] 类型的数组，一般是X轴上的描述文字数组
     * @return
     */
    public int[] calculationXCoordinate(String[] xtext){
        int start;
        if(YValueMax>10){
            YisDouble = false;
        }
        if(YisDouble){
            mPaint = new Paint();
            mPaint.setTextSize(adaptationTool.setCanvasAdaptation(YTextSize));
            start = getTextWH("9.99",mPaint).width()+ adaptationTool.setCanvasAdaptation(35);
        }else{
            start = getYMaxTextStr();
        }

        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);//设置填满
        mCanvas.drawRect(mWidth/2-mWidth/5,mHeight/2-mHeight/6,mWidth/2+mWidth/5,mHeight/2+mHeight/6,mPaint);// 长方形
        int length = xtext.length;
        X = start;
        start += adaptationTool.setCanvasAdaptation(DataXLeftlength);
        int end = mWidth- adaptationTool.setCanvasAdaptation(DataXRightlength);
        int xTextLengthA = 0;
        if(length<2){
            xTextLengthA = length;
        }else{
            xTextLengthA = (end - start)/(length-1);
        }

        XTextX = new int[length];
        for (int i = 0; i < length; i++) {
            XTextX[i] = start;
            start = start+xTextLengthA;
        }
        if(isBeforeGrid){
            setGrid();
        }
        setText(xtext);
        setYCompany();
        return XTextX;
    }
    /**
     * 绘制数据上方的text
     * @param x
     * @param y
     * @param text
     */
    public void setDataText(int x ,float y , double text,int top,int color,int size){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(size);
        mPaint.setColor(color);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mCanvas.drawText(String.valueOf(text), x,y-top,mPaint);
    }
    /**
     * 绘制数据上方的text
     * @param x
     * @param y
     * @param text
     */
    public void setDataText(int x ,int y , int text,int top,int color,int size){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(size);
        mPaint.setColor(color);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mCanvas.drawText(text+"", x,y-top,mPaint);
    }
    /**
     * 绘制数据上方的text
     * @param x
     * @param y
     * @param text
     */
    public void setDataText(int x , int y , String text, int top, int color, int size){
        mPaint = new Paint();
        mPaint.setTextSize(size);
        mPaint.setAntiAlias(true);
        mPaint.setColor(color);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mCanvas.drawText(text, x,y-top,mPaint);
    }

    /**
     * 获取X轴坐标对应值的Y值
     * @param Xdata X轴对应的Y轴的数据
     * @return  X轴对应的Y轴的坐标
     */
    public int getYValue(int Xdata){
        //算出值在屏幕的Y轴大小
        int heightValue = Y - adaptationTool.setCanvasAdaptation(getGridYFromTop());
        if(YValueMin != 0){
            heightValue = heightValue/2;
        }
        if(Xdata<0){
            return (heightValue + (Math.abs(Xdata) * heightValue) / Math.abs(getYValueMin()) + adaptationTool.setCanvasAdaptation(getGridYFromTop()));
        }else{
//            double aaa = (Xdata * heightValue) /getYValueMax();
//            double bbb = heightValue - (Xdata * heightValue) /getYValueMax();
//            double ccc = heightValue - (Xdata * heightValue) /getYValueMax()+adaptationTool.setCanvasAdaptation(getGridYFromTop());
            return (int)(heightValue - (Xdata * heightValue) /getYValueMax() + adaptationTool.setCanvasAdaptation(getGridYFromTop()));
        }
    }
    /**
     * 获取X轴坐标对应值的Y值
     * @param Xdata X轴对应的Y轴的数据
     * @return  X轴对应的Y轴的坐标
     */
    public float getYValue(double Xdata){
        //算出值在屏幕的Y轴大小
        int heightValue = Y - adaptationTool.setCanvasAdaptation(getGridYFromTop());
        if(YValueMin != 0){
            heightValue = heightValue/2;
        }
        if(Xdata<0){
            return (float) ((heightValue + (Math.abs(Xdata) * heightValue) / Math.abs(getYValueMin()) + adaptationTool.setCanvasAdaptation(getGridYFromTop())));
        }else{
//            double aaa = (Xdata * heightValue) /getYValueMax();
//            double bbb = heightValue - (Xdata * heightValue) /getYValueMax();
//            double ccc = heightValue - (Xdata * heightValue) /getYValueMax()+adaptationTool.setCanvasAdaptation(getGridYFromTop());
            return (float) (heightValue - (Xdata * heightValue) /getYValueMax() + adaptationTool.setCanvasAdaptation(getGridYFromTop()));
        }
    }
    /**
     * 获取X轴坐标对应值的Y值
     * @param Xdata X轴对应的Y轴的数据
     * @return  X轴对应的Y轴的坐标
     */
    public int getYValue(String Xdata){
        if(Xdata.equals("")){
            return 0;
        }else{
            //算出值在屏幕的Y轴大小
            int heightValue = Y - adaptationTool.setCanvasAdaptation(getGridYFromTop());
            return (int)(heightValue - (Double.valueOf(Xdata) * heightValue) /(int)getYValueMax() + adaptationTool.setCanvasAdaptation(getGridYFromTop()));
        }
    }
    /**
     * 绘制坐标轴
     */
    public void canvasCoordinate(){
        if(isXY){
            mPaint = new Paint();
            //设置线条粗细
            mPaint.setStrokeWidth(adaptationTool.setCanvasAdaptation(XYWidth));
            //Y
            mPaint.setColor(XColor);
            mCanvas.drawLine(X,Y,X, adaptationTool.setCanvasAdaptation(gridYFromTop) - adaptationTool.setCanvasAdaptation(gridUpExceedgridYFromTop), mPaint);
            //X
            mPaint.setColor(YColor);
            mCanvas.drawLine(X,Y,mWidth, Y,mPaint);
        }
    }
    /**
     * 设置网格规格  false 表示不显示这个数据的网格
     * @param specification
     */
    public void setGridSpecification(Boolean[] specification){
        GridSpecificationList = specification;
    }
    /**
     * 是否显示网格
     * @param isX   是否显示X轴上的网格
     * @param isY   是否显示Y轴上的网格
     */
    public void isGrid(Boolean isX, Boolean isY){
        this.isgridX = isX;
        this.isgridY = isY;
    }
    /**
     * 是否显示Text
     * @param isX   是否显示X轴上的Text
     * @param isY   是否显示Y轴上的Text
     */
    public void isText(Boolean isX, Boolean isY){
        this.isTextX = isX;
        this.isTextY = isY;
    }
    /**
     * 设置text右边的图片
     * @param imgs  图片的集合     图片id为0时图片不显示
     */
    public void setTextRightImage(int[] imgs){
        this.textImages = imgs;
    }

    /**
     * 获取Y轴最大值的String类型的宽度
     * @return
     */
    private int getYMaxTextStr(){
        mPaint = new Paint();
        mPaint.setTextSize(adaptationTool.setCanvasAdaptation(YTextSize));
        return getTextWH(YValueMax+"",mPaint).width()+ adaptationTool.setCanvasAdaptation(10);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        X = adaptationTool.setCanvasAdaptation(20);
        Y = mHeight - adaptationTool.setCanvasAdaptation(100);
    }

    /**
     * 设置网格Type
     */
    public static enum GridType {
        REAL,//实线
        EMPTY;//虚线
        private GridType() {
        }
    }
    public GridType getGridType() {
        return gridType;
    }

    public void setGridType(GridType gridType) {
        this.gridType = gridType;
    }

    public boolean isgridX() {
        return isgridX;
    }

    public void setIsgridX(boolean isgridX) {
        this.isgridX = isgridX;
    }

    public boolean isgridY() {
        return isgridY;
    }

    public void setIsgridY(boolean isgridY) {
        this.isgridY = isgridY;
    }

    public int getXYWidth() {
        return XYWidth;
    }

    public void setXYWidth(int XYWidth) {
        this.XYWidth = XYWidth;
    }

    public int getXColor() {
        return XColor;
    }

    public void setXColor(int XColor) {
        this.XColor = XColor;
    }

    public int getXTextSize() {
        return XTextSize;
    }

    public void setXTextSize(int XTextSize) {
        this.XTextSize = XTextSize;
    }

    public int getXTextColor() {
        return XTextColor;
    }

    public void setXTextColor(int XTextColor) {
        this.XTextColor = XTextColor;
    }

    public int getYColor() {
        return YColor;
    }

    public void setYColor(int YColor) {
        this.YColor = YColor;
    }

    public int getYTextSize() {
        return YTextSize;
    }

    public void setYTextSize(int YTextSize) {
        this.YTextSize = YTextSize;
    }

    public int getYTextColor() {
        return YTextColor;
    }

    public void setYTextColor(int YTextColor) {
        this.YTextColor = YTextColor;
    }

    public double getYValueMax() {
        return YValueMax;
    }

    public void setYValueMax(double YValueMax) {
        this.YValueMax = YValueMax;
    }

    public int getDataXRightlength() {
        return DataXRightlength;
    }

    public void setDataXRightlength(int dataXRightlength) {
        DataXRightlength = dataXRightlength;
    }

    public int getGridWidth() {
        return gridWidth;
    }

    public void setGridWidth(int gridWidth) {
        this.gridWidth = gridWidth;
    }

    public int getGridColor() {
        return gridColor;
    }

    public void setGridColor(int gridColor) {
        this.gridColor = gridColor;
    }

    public int getGridUpExceedgridYFromTop() {
        return gridUpExceedgridYFromTop;
    }

    public void setGridUpExceedgridYFromTop(int gridUpExceedgridYFromTop) {
        this.gridUpExceedgridYFromTop = gridUpExceedgridYFromTop;
    }

    public int getGridDownExceedX() {
        return gridDownExceedX;
    }

    public void setGridDownExceedX(int gridDownExceedX) {
        this.gridDownExceedX = gridDownExceedX;
    }

    public int getGridYNumber() {
        return gridYNumber;
    }

    public void setGridYNumber(int gridYNumber) {
        this.gridYNumber = gridYNumber;
    }

    public int getGridYFromTop() {
        return gridYFromTop;
    }

    public void setGridYFromTop(int gridYFromTop) {
        this.gridYFromTop = gridYFromTop;
    }

    public int getYTextImgWidth() {
        return YTextImgWidth;
    }

    public void setYTextImgWidth(int YTextImgWidth) {
        this.YTextImgWidth = YTextImgWidth;
    }

    public int getYTextImgHeight() {
        return YTextImgHeight;
    }

    public void setYTextImgHeight(int YTextImgHeight) {
        this.YTextImgHeight = YTextImgHeight;
    }

    public int getTitleHeight() {
        return getTitleHeights();
    }

    public void setTitleHeight(int titleHeight) {
        setTitleHeights(titleHeight);
        setGridYFromTop(getTitleBotCompanyHeight()+titleHeight+50);
    }

    public void setYCompanyText(String YCompanyText) {
        setTitleBotCompanyHeight(30);
        setGridYFromTop(getGridYFromTop()+40);
        this.YCompanyText = YCompanyText;
    }

    public boolean isXY() {
        return isXY;
    }

    public void setXY(boolean XY) {
        isXY = XY;
    }

    public int getmWidth() {
        return mWidth;
    }

    public void setmWidth(int mWidth) {
        this.mWidth = mWidth;
    }

    public int getmHeight() {
        return mHeight;
    }

    public void setmHeight(int mHeight) {
        this.mHeight = mHeight;
    }

    public boolean isBeforeGrid() {
        return isBeforeGrid;
    }

    public void setBeforeGrid(boolean beforeGrid) {
        isBeforeGrid = beforeGrid;
    }

    public int getYValueMin() {
        return YValueMin;
    }

    public void setYValueMin(int YValueMin) {
        this.YValueMin = YValueMin;
    }

    public int getDataXLeftlength() {
        return DataXLeftlength;
    }

    public void setDataXLeftlength(int dataXLeftlength) {
        DataXLeftlength = dataXLeftlength;
    }

    public boolean isYGridType() {
        return isYGridType;
    }

    public void setYGridType(boolean YGridType) {
        isYGridType = YGridType;
    }

    public int getTextXDistance() {
        return textXDistance;
    }

    public void setTextXDistance(int textXDistance) {
        this.textXDistance = textXDistance;
    }

    public boolean isYisDouble() {
        return YisDouble;
    }

    public void setYisDouble(boolean yisDouble) {
        YisDouble = yisDouble;
    }

    public String getYCompanyText() {
        return YCompanyText;
    }
}
