package com.jiage.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


import com.jiage.tool.AdaptationTool;

import java.io.InputStream;

/**
 * 作者：李忻佳.
 * 时间：2016/12/22.
 * 说明：BaseDrawView
 */

public class BaseDrawView extends View {
    /**
     * title高度
     */
    private int titleHeights = 0;
    /**
     * title下方Y轴字体单位大高度
     */
    private int titleBotCompanyHeight = 0;
    /**
     * title左边的文字描述
     */
    private String titleLeftText = "";
    /**
     * 是否显示title左边的文字左边的图片
     */
    private boolean isTitleLeftImag = false;
    /**
     * title左边的文字左边的图片id
     */
    private int titleLeftImgSrc = 0;
    /**
     * title左边的文字左边的图片宽
     */
    private int titleLeftImgWidth = 60;
    /**
     * title左边的文字左边的图片高
     */
    private int titleLeftImgheight = 60;
    /**
     * title左边的文字离图片的距离
     */
    private int titleLeftImgTextDistance = 10;
    /**
     * title左边的文字左边的图片离左边距离
     */
    private int titleLeftImgLeftDistance = 0;
    /**
     * title左边的文字的颜色
     */
    private int titleLeftTextColor = Color.parseColor("#000000");
    /**
     * title左边的文字的字体大小
     */
    private int titleLeftTextSize = 60;
    /**
     * 是否显示Title
     */
    private boolean isTitle = false;
    /**
     * 是否显示Title下的分割线
     */
    private boolean isSplitLine = false;
    /**
     * Title下的分割线的颜色
     */
    private int splitLineColor = Color.parseColor("#e5e5e5");
    /**
     * Title下的分割线的宽度
     */
    private int splitLineWidth = 3;


    public AdaptationTool adaptationTool;
    protected Canvas mCanvas;
    protected Paint mPaint;
    protected int mWidth;
    protected int mHeight;

    public BaseDrawView(Context context) {
        super(context);
        init(context);
    }

    public BaseDrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BaseDrawView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    protected void init(Context context) {
        adaptationTool = new AdaptationTool(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.mCanvas = canvas;
        canvas.drawColor(Color.WHITE);
        mPaint = new Paint();
        //去锯齿
        mPaint.setAntiAlias(true);
        canvasTitle();
    }
    /**
     * 绘制title
     */
    private void canvasTitle() {
        if(isTitle){
            if(isTitleLeftImag){
                //绘制title左边图片
                if(getTitleLeftImgSrc()!=0){
                    int imgwidth = adaptationTool.setCanvasAdaptation(titleLeftImgWidth);
                    int imgHeight = adaptationTool.setCanvasAdaptation(titleLeftImgheight);
                    InputStream is = getResources().openRawResource(getTitleLeftImgSrc());
                    Bitmap mBitmap = BitmapFactory.decodeStream(is);
                    drawImage(mCanvas,mBitmap, adaptationTool.setCanvasAdaptation(titleLeftImgLeftDistance),(adaptationTool.setCanvasAdaptation(getTitleHeights())-imgHeight)/2,imgwidth,imgHeight,0,0);
                }
            }
            //绘制title左边text
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setTextSize(adaptationTool.setCanvasAdaptation(titleLeftTextSize));
            mPaint.setColor(titleLeftTextColor);
            int leftlenght = adaptationTool.setCanvasAdaptation(titleLeftImgLeftDistance);
            if(isTitleLeftImag){
                leftlenght = adaptationTool.setCanvasAdaptation(titleLeftImgWidth) + leftlenght+ adaptationTool.setCanvasAdaptation(10)+ adaptationTool.setCanvasAdaptation(titleLeftImgTextDistance);
            }
            mCanvas.drawText(titleLeftText,leftlenght, adaptationTool.getCanvasTextHeight(mPaint)/2+ adaptationTool.setCanvasAdaptation(getTitleHeights())/2,mPaint);

            //绘制title下的分割线
            if(isSplitLine){
                mPaint = new Paint();
                mPaint.setColor(splitLineColor);
                mPaint.setStrokeWidth(adaptationTool.setCanvasAdaptation(splitLineWidth));
                mCanvas.drawLine(0, adaptationTool.setCanvasAdaptation(getTitleHeights()),mWidth, adaptationTool.setCanvasAdaptation(getTitleHeights()),mPaint);
            }
        }
    }
    /**
     * 触屏事件监听
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                onTouchDown(event.getX(),event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                onTouchMove(event.getX(),event.getY());
                break;
            case MotionEvent.ACTION_UP:
                onTouchUp(event.getX(),event.getY());
                break;
        }
        return true;
    }
    /**
     * 点击按下事件
     * @param downX
     * @param downY
     */
    protected void onTouchDown(float downX, float downY){}

    /**
     * 点击按下移动事件
     * @param moveX
     * @param moveY
     */
    protected void onTouchMove(float moveX, float moveY){}
    /**
     * 点击抬起事件
     * @param upX
     * @param upY
     */
    protected void onTouchUp(float upX, float upY){}

    /**
     * 获取字符串的宽高
     * @param text
     * @return
     */
    protected Rect getTextWH(String text, Paint paint){
        Rect bounds = new Rect();
        if(text == null){
            paint.getTextBounds("",0,0,bounds);
        }else{
            paint.getTextBounds(text,0,text.length(),bounds);
        }
        return bounds;
    }

    /**
     * 判断矩形是否被点击了
     * @param touchX        点击时的X
     * @param touchY        点击时的Y
     * @param histogramX    矩形的X
     * @param histogramY    矩形的Y
     * @param histogramWidth   矩形的宽
     * @param histogramHeight   矩形的高
     * @return
     */
    protected boolean isClickHistogramView(float touchX,float touchY,int histogramX,int histogramY,int histogramWidth,int histogramHeight){
        histogramWidth += adaptationTool.setCanvasAdaptation(10);
        if(touchX>=histogramX-histogramWidth*2&&touchX<=histogramX+histogramWidth/2&&touchY>=histogramY&&touchY<=histogramY+histogramHeight){
            return true;
        }
        return false;
    }

    /**
     * 判断圆是否被点击了
     * @param touchX            点击时的X
     * @param touchY            点击时的Y
     * @param circularX         圆心的X
     * @param circularY         圆心的Y
     * @param circularRadius    半径
     * @return
     */
    protected boolean isClickCircularView(float touchX,float touchY,int circularX,int circularY,int circularRadius){
        circularRadius += adaptationTool.setCanvasAdaptation(10);
        if(touchX>=circularX-circularRadius*2&&touchX<=circularX+circularRadius/2&&touchY>=circularY-circularRadius*2&&touchY<=circularY+circularRadius/2){
            return true;
        }
        return false;
    }
    /*---------------------------------
         * 绘制图片
         * @param       x屏幕上的x坐标
         * @param       y屏幕上的y坐标
         * @param       w要绘制的图片的宽度
         * @param       h要绘制的图片的高度
         * @param       bx图片上的x坐标
         * @param       by图片上的y坐标
         *
         * @return      null
         ------------------------------------*/
    protected void drawImage(Canvas canvas, Bitmap blt, int x, int y, int w, int h, int bx, int by) {
        Rect src = new Rect();// 图片 >>原矩形
        Rect dst = new Rect();// 屏幕 >>目标矩形

        src.left = bx;
        src.top = by;
        src.right = bx + w;
        src.bottom = by + h;

        dst.left = x;
        dst.top = y;
        dst.right = x + w;
        dst.bottom = y + h;
        // 画出指定的位图，位图将自动--》缩放/自动转换，以填补目标矩形
        // 这个方法的意思就像 将一个位图按照需求重画一遍，画后的位图就是我们需要的了
        canvas.drawBitmap(blt, null, dst, null);
        src = null;
        dst = null;
    }
    /**
     * 使用Bitmap加Matrix来缩放和旋转图片
     * @param bitmap
     * @param w         设置宽度
     * @param h         设置高度
     * @param angle     设置旋转角度
     * @return
     */
    protected Bitmap resizeAndRotateImage(Bitmap bitmap, int w, int h, int angle){
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
     * 旋转text
     * @param canvas
     * @param text
     * @param x
     * @param y
     * @param paint
     * @param angle     设置旋转角度
     */
    protected void drawRotateText(Canvas canvas , String text , float x , float y, Paint paint , float angle){
        if(angle != 0){
            canvas.rotate(angle, x, y);
        }
        canvas.drawText(text, x, y, paint);
        if(angle != 0){
            canvas.rotate(-angle, x, y);
        }
    }

    /**
     * 绘制圆上的text
     * @param text
     * @param originX   圆的原点X坐标
     * @param originY   圆的原点Y坐标
     * @param angle     角度  0 是正下方，逆时针旋转
     * @param radius     圆的半径
     * @param canvas
     * @param paint
     */
    protected void setCircleCoordinates(String text , int originX, int originY, float angle, int radius, Canvas canvas, Paint paint){
        canvas.drawText(text,(int)getCircleCoordinatesX(originX,-angle,radius),(int)getCircleCoordinatesY(originY,-angle,radius)+getTextWH(text,paint).height()/2,paint);
    }

    /**
     *  根据圆获取圆上的点X轴坐标
     * @param originX   圆的原点X坐标
     * @param angle     角度  0 是正下方，逆时针旋转
     * @param radius    圆的半径
     * @return          圆上的点的X坐标
     */
    protected double getCircleCoordinatesX(int originX , int angle, int radius){
        return originX + Math.sin(2* Math.PI / 360 * angle) * radius;
    }
    /**
     *  根据圆获取圆上的点X轴坐标
     * @param originX   圆的原点X坐标
     * @param angle     角度  0 是正下方，逆时针旋转
     * @param radius    圆的半径
     * @return          圆上的点的X坐标
     */
    protected double getCircleCoordinatesX(int originX , float angle, int radius){
        return originX + Math.sin(2* Math.PI / 360 * angle) * radius;
    }

    /**
     *  根据圆获取圆上的点Y轴坐标
     * @param originY   圆的原点Y坐标
     * @param angle     角度  0 是正下方，逆时针旋转
     * @param radius    圆的半径
     * @return          圆上的点的Y坐标
     */
    protected double getCircleCoordinatesY(int originY , int angle, int radius){
        return originY + Math.cos(2* Math.PI / 360 * angle) * radius;
    }
    /**
     *  根据圆获取圆上的点Y轴坐标
     * @param originY   圆的原点Y坐标
     * @param angle     角度  0 是正下方，逆时针旋转
     * @param radius    圆的半径
     * @return          圆上的点的Y坐标
     */
    protected double getCircleCoordinatesY(int originY , float angle, int radius){
        return originY + Math.cos(2* Math.PI / 360 * angle) * radius;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = measureWidth(widthMeasureSpec);
        mHeight = measureHeight(heightMeasureSpec);
    }

    //根据xml的设定获取宽度
    protected int measureWidth(int measureSpec) {
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
    protected int measureHeight(int measureSpec) {
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

    public int getTitleHeights() {
        return titleHeights;
    }

    public void setTitleHeights(int titleHeights) {
        this.titleHeights = titleHeights;
    }

    public String getTitleLeftText() {
        return titleLeftText;
    }

    public void setTitleLeftText(String titleLeftText) {
        this.titleLeftText = titleLeftText;
    }

    public boolean isTitleLeftImag() {
        return isTitleLeftImag;
    }

    public void setTitleLeftImag(boolean titleLeftImag) {
        isTitleLeftImag = titleLeftImag;
    }

    public int getTitleLeftImgSrc() {
        return titleLeftImgSrc;
    }

    public void setTitleLeftImgSrc(int titleLeftImgSrc) {
        this.titleLeftImgSrc = titleLeftImgSrc;
    }

    public int getTitleLeftImgWidth() {
        return titleLeftImgWidth;
    }

    public void setTitleLeftImgWidth(int titleLeftImgWidth) {
        this.titleLeftImgWidth = titleLeftImgWidth;
    }

    public int getTitleLeftImgheight() {
        return titleLeftImgheight;
    }

    public void setTitleLeftImgheight(int titleLeftImgheight) {
        this.titleLeftImgheight = titleLeftImgheight;
    }

    public int getTitleLeftImgTextDistance() {
        return titleLeftImgTextDistance;
    }

    public void setTitleLeftImgTextDistance(int titleLeftImgTextDistance) {
        this.titleLeftImgTextDistance = titleLeftImgTextDistance;
    }

    public int getTitleLeftImgLeftDistance() {
        return titleLeftImgLeftDistance;
    }

    public void setTitleLeftImgLeftDistance(int titleLeftImgLeftDistance) {
        this.titleLeftImgLeftDistance = titleLeftImgLeftDistance;
    }

    public int getTitleLeftTextColor() {
        return titleLeftTextColor;
    }

    public void setTitleLeftTextColor(int titleLeftTextColor) {
        this.titleLeftTextColor = titleLeftTextColor;
    }

    public int getTitleLeftTextSize() {
        return titleLeftTextSize;
    }

    public void setTitleLeftTextSize(int titleLeftTextSize) {
        this.titleLeftTextSize = titleLeftTextSize;
    }

    public boolean isTitle() {
        return isTitle;
    }

    public void setTitle(boolean title) {
        isTitle = title;
    }

    public boolean isSplitLine() {
        return isSplitLine;
    }

    public void setSplitLine(boolean splitLine) {
        isSplitLine = splitLine;
    }

    public int getSplitLineColor() {
        return splitLineColor;
    }

    public void setSplitLineColor(int splitLineColor) {
        this.splitLineColor = splitLineColor;
    }

    public int getSplitLineWidth() {
        return splitLineWidth;
    }

    public void setSplitLineWidth(int splitLineWidth) {
        this.splitLineWidth = splitLineWidth;
    }

    public int getTitleBotCompanyHeight() {
        return titleBotCompanyHeight;
    }

    public void setTitleBotCompanyHeight(int titleBotCompanyHeight) {
        this.titleBotCompanyHeight = titleBotCompanyHeight;
    }
}
