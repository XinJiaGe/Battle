package com.jiage.battle.surface.aircraft;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.jiage.battle.R;
import com.jiage.battle.util.BitmapUtils;
import com.jiage.battle.util.SDViewUtil;

/**
 * 作者：李忻佳
 * 日期：2018/11/26
 * 说明：
 */

public class DrawBackground {
    private int mScreenHeight;
    private Bitmap mBackgroundBottom;
    private int mBottomY1 = 0;
    private int mBottomY2 = 0;
    private int seep = 10;

    public DrawBackground(Context mContext){
        //加载背景图片
        mBackgroundBottom = BitmapUtils.ReadBitMap(mContext, R.drawable.icon_background);
        //把背景图大小设置为手机屏幕大小
        mBackgroundBottom = BitmapUtils.getBitmap(mContext, mBackgroundBottom,0);
        mScreenHeight = SDViewUtil.getScreenHeight();
        mBottomY2 = mBottomY1+mScreenHeight;
    }

    public void myDraw(Canvas mCanvas, Paint mPaint) {
        mCanvas.drawBitmap(mBackgroundBottom,0, mBottomY1,mPaint);
        mCanvas.drawBitmap(mBackgroundBottom,0, mBottomY2,mPaint);
    }

    public void logic() {
        if(mBottomY1>=mScreenHeight){
            mBottomY1 = -mScreenHeight;
        }
        if(mBottomY2>=mScreenHeight){
            mBottomY2 = -mScreenHeight;
        }
        if(mBottomY1>mBottomY2){
            mBottomY1 += seep;
            mBottomY2 = mBottomY1-mScreenHeight;
        }else{
            mBottomY2 += seep;
            mBottomY1 = mBottomY2-mScreenHeight;
        }
    }
}
