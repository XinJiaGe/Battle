package com.jiage.battle.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.jiage.battle.constant.Constant;
import com.jiage.battle.entity.CheckpointItemEntity;

/**
 * 作者：李忻佳
 * 日期：2018/8/24
 * 说明：
 */

public class CheckpointView extends View {
    private int mWidth;
    private int mHeight;
    private CheckpointItemEntity mCheckpoint;

    public CheckpointView(Context context) {
        super(context);
    }

    public CheckpointView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CheckpointView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mWidth = w;
        this.mHeight = h;
    }

    public void setData(CheckpointItemEntity checkpoint){
        this.mCheckpoint = checkpoint;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        if(mCheckpoint!=null){
            int[][] checkpoint = mCheckpoint.getCheckpoint();
            int screenH = mCheckpoint.getScreenH();
            int gao = mHeight*mCheckpoint.getHeght()/screenH;
            for (int i = 0; i < checkpoint.length; i++) {
                for (int i1 = 0; i1 < checkpoint[i].length; i1++) {
                    if(checkpoint[i][i1]!=0)
                        initBg(canvas,paint,checkpoint[i][i1],checkpoint[i].length,i1 * (mWidth / checkpoint[i].length), i * gao,gao);
                }
            }
        }
    }

    private void initBg(Canvas canvas, Paint paint, int i, int size, int initX, int initY, int gao){
        paint.setColor(Constant.Block.getColor(i));
        Rect rect = new Rect(initX, initY, initX + Constant.Block.getWidth(mWidth, size), initY + gao);
        canvas.drawRect(rect,paint);
        if(i == 0)
            paint.setColor(Color.BLACK);
        else
            paint.setColor(Color.WHITE);
        canvas.drawLine(rect.left,rect.top,rect.right,rect.top,paint);
        canvas.drawLine(rect.right,rect.top,rect.right,rect.bottom,paint);
        canvas.drawLine(rect.right,rect.bottom,rect.left,rect.bottom,paint);
        canvas.drawLine(rect.left,rect.bottom,rect.left,rect.top,paint);
    }
}
