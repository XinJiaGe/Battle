package com.jiage.battle.surface.defense;

import android.content.Context;
import android.util.AttributeSet;

import com.jiage.battle.constant.Constant;
import com.jiage.battle.surface.BaseSurfaceView;
import com.jiage.battle.util.OtherUtil;
import com.jiage.battle.util.SDTimerUtil;

import java.util.ArrayList;
import java.util.Vector;

/**
 * 作者：李忻佳
 * 日期：2018/12/18
 * 说明：
 */

public class TowerDefenseSurfaceView extends BaseSurfaceView {
    private int[][] towerDefenses;
    private int topHeight = 200;
    private int mapHeight = 0;
    private Vector<Map> vcMap = new Vector<>();//地图容器
    private Vector<Enemy> vcEnemy = new Vector<>();//敌人容器
    private Vector<Route> vcRoute = new Vector<>();//路线集合
    private int checkpoint;
    private int startEnemyX, startEnemyY;
    private int startMapIndex, column, row;
    private boolean isEnd = false;

    @Override
    public void created() {
        towerDefenses = Constant.TowerDefenseCheckpoint.getCheckpoint(checkpoint);
        int postion = 0;
        int x = 0, y = topHeight;
        for (int i = 0; i < towerDefenses.length; i++) {
            column = towerDefenses[i].length;
            row = towerDefenses.length;
            y += mScreenW / towerDefenses[i].length;
            if (mapHeight == 0)
                mapHeight = mScreenW / towerDefenses[i].length;
            for (int j = 0; j < towerDefenses[i].length; j++) {
                //生成地图
                vcMap.add(new Map(mScreenW / towerDefenses[i].length * x, y, mScreenW / towerDefenses[i].length, i, j,
                        towerDefenses[i].length, towerDefenses.length, postion, towerDefenses[i][j]));
                if (towerDefenses[i][j] == 3) {
                    startEnemyX = x + mapHeight / 2;
                    startEnemyY = y + mapHeight / 2;
                    startMapIndex = vcMap.size() - 1;
                }
                x = j + 1;
            }
            x = 0;
        }
        SDTimerUtil util = new SDTimerUtil();
        util.startWork(0, 1000, new SDTimerUtil.SDTimerListener() {
            @Override
            public void onWork() {
            }

            @Override
            public void onWorkMain() {
                vcEnemy.add(new Enemy(startEnemyX, startEnemyY));
            }
        });
    }

    @Override
    public void myDraw() {

        for (int i = 0; i < vcMap.size(); i++) {//绘制地图
            vcMap.elementAt(i).draw(mCanvas, mPaint);
        }
        for (int i = 0; i < vcEnemy.size(); i++) {//绘制敌人
            vcEnemy.elementAt(i).draw(mCanvas, mPaint);
        }
    }

    @Override
    public void logic() {
        for (int i = 0; i < vcEnemy.size(); i++) {//敌人逻辑
            Enemy enemy = vcEnemy.elementAt(i);
            if (enemy.isDead()) {
                vcEnemy.removeElement(enemy);
            } else {
                enemy.logic(vcMap);
            }
        }
    }

    public TowerDefenseSurfaceView(Context context) {
        super(context);
    }

    public TowerDefenseSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TowerDefenseSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setCheckpoint(int checkpoint) {
        this.checkpoint = checkpoint;
    }
}
