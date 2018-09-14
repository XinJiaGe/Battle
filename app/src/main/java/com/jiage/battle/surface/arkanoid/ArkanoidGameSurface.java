package com.jiage.battle.surface.arkanoid;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;

import com.jiage.battle.constant.Constant;
import com.jiage.battle.entity.CheckpointItemEntity;
import com.jiage.battle.sharedPreference.SharedPreference;
import com.jiage.battle.surface.BaseSurfaceView;
import com.jiage.battle.surface.arkanoid.Square;
import com.jiage.battle.surface.arkanoid.Bullet;
import com.jiage.battle.surface.arkanoid.Functions;
import com.jiage.battle.surface.arkanoid.Player;
import com.jiage.battle.util.SurfaceViewUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * 作者：李忻佳
 * 日期：2018/8/15
 * 说明：打砖块游戏
 */

public class ArkanoidGameSurface extends BaseSurfaceView {
    private Player player;
    private int playerInitX, playerInitY, bulletInitX, bulletInitY;
    private float downPlayerX;
    private boolean playerIsMove = false;
    private Vector<Square> vcSquare;//方块容器实例
    private Vector<Bullet> vcBullet;//子弹容器实例
    private List<Constant.FunctionType> functionList;//功能几率数组
    private Vector<Functions> vcFunctions;//功能容器实例
    private onLenter mOnLenter;//回调
    private boolean initSuspend = true; //是否暂停
    private boolean suspend = true; //是否暂停
    private CheckpointItemEntity mCheckpoint;

    public ArkanoidGameSurface(Context context) {
        super(context);
    }

    public ArkanoidGameSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ArkanoidGameSurface(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void created() {
        functionList = getFunctionProbability();
        playerInitX = mScreenW / 2 - Constant.Player.width / 2;//主角初始位置
        playerInitY = mScreenH - Constant.Player.height - 200;
        bulletInitX = playerInitX + Constant.Player.width / 2;//子弹初始位置
        bulletInitY = playerInitY - Constant.Bullet.radius;
        player = new Player(playerInitX, playerInitY, mScreenH);//添加主角
        vcSquare = new Vector<>();
        vcBullet = new Vector<>();
        vcFunctions = new Vector<>();
        vcBullet.add(new Bullet(bulletInitX, bulletInitY, mScreenW, mScreenH, 135, player));//添加一个子弹
        setCheckpoint();
    }

    @Override
    public void myDraw() {
        //刷屏，画布白色
        mCanvas.drawColor(Color.WHITE);
        player.draw(mCanvas, mPaint);//绘制主角
        for (int i = 0; i < vcSquare.size(); i++) {//绘制方块
            vcSquare.elementAt(i).draw(mCanvas, mPaint);
        }
        for (int i = 0; i < vcFunctions.size(); i++) {//绘制功能
            vcFunctions.elementAt(i).draw(mCanvas, mPaint);
        }
        for (int i = 0; i < vcBullet.size(); i++) {//绘制子弹
            vcBullet.elementAt(i).draw(mCanvas, mPaint);
        }
    }

    @Override
    public void logic() {
        boolean iswan = true;
        for (int i = 0; i < vcSquare.size(); i++) {//方块逻辑
            for (int i1 = 0; i1 < vcBullet.size(); i1++) {
                vcSquare.elementAt(i).logic(vcBullet.elementAt(i1));
            }
            if (vcSquare.elementAt(i).isDead) {//方块销毁生成功能
                int random = SurfaceViewUtil.getRandomEnd(functionList.size() - 1);
                if (functionList.get(random) != Constant.FunctionType.nulls)
                    vcFunctions.add(new Functions(vcSquare.elementAt(i), mScreenW, mScreenH, functionList.get(random)));
                vcSquare.removeElement(vcSquare.elementAt(i));
            }
        }
        for (int i1 = 0; i1 < vcBullet.size(); i1++) {//子弹逻辑
            vcBullet.elementAt(i1).logic(initSuspend,suspend, player, soundPool);
            if (vcBullet.elementAt(i1).isDead)
                vcBullet.removeElement(vcBullet.elementAt(i1));
        }
        for (int i = 0; i < vcFunctions.size(); i++) {//功能逻辑
            vcFunctions.elementAt(i).logic(initSuspend,suspend,player, vcBullet);
            if (vcFunctions.elementAt(i).isDead)
                vcFunctions.removeElement(vcFunctions.elementAt(i));
        }
        if (!suspend&&vcBullet.size() == 0&&mCheckpoint != null) {//游戏结束
            suspend = true;
            if (mOnLenter != null) {
                mOnLenter.gameOver();
            }
        }
        for (int i = 0; i < vcSquare.size(); i++) {
            if (vcSquare.elementAt(i).getmType() != -1) {//判断可击碎的方块是否还存在
                iswan = false;
                break;
            }
        }
        if (!suspend&&iswan&&mCheckpoint != null) { //游戏胜利
            suspend = true;
            if (mOnLenter != null) {
                mOnLenter.victory();
            }
        }
    }

    @Override
    protected void onTouchDown(int id, float rawX, float rawY) {
        if (((initSuspend&&suspend)||(!initSuspend&&!suspend)) && SurfaceViewUtil.isCollsionClick(player.getRegion(), rawX, rawY)) {
            playerIsMove = true;
            downPlayerX = player.getmInitX();
        } else {
            playerIsMove = false;
        }
    }

    @Override
    protected void onTouchMove(int id, float downRawX, float downRawY, float rawX, float rawY) {
        if (playerIsMove) {
            float moveX = (rawX - downRawX) + downPlayerX;
            if (moveX > 0 && (moveX + Constant.Player.width) < mScreenW)
                player.setmInitX(moveX);
        }
    }

    @Override
    protected void onTouchUp(int id, float rawX, float rawY) {
        playerIsMove = false;
    }

    /**
     * 绘制方块
     */
    public void setCheckpoint() {
        if (mCheckpoint != null) {
            int[][] checkpoints = mCheckpoint.getCheckpoint();
            for (int i = 0; i < checkpoints.length; i++) {
                for (int i1 = 0; i1 < checkpoints[i].length; i1++) {
                    if (checkpoints[i][i1] != 0) {
                        if(vcSquare !=null)
                            vcSquare.add(new Square(soundPool, checkpoints[i][i1], i, i1, mScreenW, mScreenH, checkpoints[i].length,
                                i1 * (mScreenW / checkpoints[i].length), i * mCheckpoint.getHeght(), mCheckpoint.getHeght()));
                    }
                }
            }
        }
    }

    public void setData(CheckpointItemEntity checkpoints) {
        this.mCheckpoint = checkpoints;
    }

    /**
     * 重新游戏
     */
    public void renew() {
        created();
    }

    /**
     * 下一关
     */
    public void nextLevel() {
        created();
    }

    /**
     * 获取功能几率数组
     * @return
     */
    private List<Constant.FunctionType> getFunctionProbability(){
        List<Constant.FunctionType> functionList = new ArrayList<>();
        for (int i = 0; i < SharedPreference.getSharedPreference().getArkanoidFunctions2(); i++) {
            functionList.add(Constant.FunctionType.doubleTimes);
        }
        for (int i = 0; i < SharedPreference.getSharedPreference().getArkanoidFunctions3(); i++) {
            functionList.add(Constant.FunctionType.threeTimes);
        }
        for (int i = 0; i < SharedPreference.getSharedPreference().getArkanoidFunctions4(); i++) {
            functionList.add(Constant.FunctionType.fourTimes);
        }
        for (int i = 0; i < 100-functionList.size(); i++) {
            functionList.add(Constant.FunctionType.nulls);
        }
        return functionList;
    }

    public interface onLenter {
        void gameOver();//结束游戏
        void victory();//胜利
    }

    public void onDestroy() {
        onDestroy();
    }

    public void setOnLenter(onLenter lenter) {
        this.mOnLenter = lenter;
    }

    public boolean isSuspend() {
        return suspend;
    }

    public void setSuspend(boolean suspend) {
        this.suspend = suspend;
    }

    public boolean isInitSuspend() {
        return initSuspend;
    }

    public void setInitSuspend(boolean initSuspend) {
        this.initSuspend = initSuspend;
    }
}
