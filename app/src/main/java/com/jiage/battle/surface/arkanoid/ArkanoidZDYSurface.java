package com.jiage.battle.surface.arkanoid;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import com.jiage.battle.surface.BaseSurfaceView;
import com.jiage.battle.surface.arkanoid.CustomizeBlock;

import java.util.Vector;

/**
 * 作者：李忻佳
 * 日期：2018/8/21
 * 说明：
 */

public class ArkanoidZDYSurface extends BaseSurfaceView {
    private Vector<CustomizeBlock> vcBlock;
    private int Gao = 20;
    private float touchX = -1,touchY = - 1;
    private int color;
    private int code;


    @Override
    public void created() {
        frame = 8;
        vcBlock = new Vector<>();//方块容器实例
    }

    @Override
    public void myDraw() {
        //刷屏，画布白色
        mCanvas.drawColor(Color.WHITE);
        for (int i = 0; i < vcBlock.size(); i++) {
            vcBlock.elementAt(i).draw(mCanvas,mPaint);
        }
    }

    @Override
    public void logic() {
        for (int i = 0; i < vcBlock.size(); i++) {
            vcBlock.elementAt(i).logic(touchX,touchY,code);
        }
    }

    @Override
    protected void onTouchDown(int id,float rawX, float rawY) {
        touchX = rawX;
        touchY = rawY;
    }

    @Override
    protected void onTouchMove(int id,float downRawX, float downRawY, float rawX, float rawY) {
        touchX = rawX;
        touchY = rawY;
    }

    @Override
    protected void onTouchUp(int id,float rawX, float rawY) {
        touchX = -1;
        touchY = -1;
    }

    public ArkanoidZDYSurface(Context context) {
        super(context);
    }

    public ArkanoidZDYSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ArkanoidZDYSurface(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * @param checkpoints
     * @param gao
     */
    public void addCheckpoint(int[][] checkpoints, int gao){
        vcBlock.clear();
        this.Gao = gao;
        for (int i = 0; i < checkpoints.length; i++) {
            for (int i1 = 0; i1 < checkpoints[i].length; i1++) {
                vcBlock.add(new CustomizeBlock(checkpoints[i][i1], mScreenW, mScreenH, checkpoints[i].length,
                        i1 * (mScreenW / checkpoints[i].length), i * gao,gao));
            }
        }
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Vector<CustomizeBlock> getVcBlock() {
        return vcBlock;
    }

    public int getScreenH(){
        return mScreenH;
    }
}
