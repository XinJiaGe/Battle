package com.jiage.battle.cocos2d.stickto3;

import android.view.MotionEvent;

import com.jiage.battle.cocos2d.Cocos2dUtil;

import org.cocos2d.layers.CCColorLayer;
import org.cocos2d.types.ccColor4B;

/**
 * 作者：忻佳
 * 日期：2019-11-12
 * 描述：
 */
public class StickTo extends CCColorLayer {
    private MapLayer mapLayer;
    private PlayLayer playLayer;
    private BulletLayer bulletLayer;
    private EnemyLayer enemyLayer;

    public StickTo(ccColor4B color) {
        super(color);
        setIsTouchEnabled(true);
        init();
    }

    private void init() {
        mapLayer = new MapLayer(this);
        playLayer = new PlayLayer(this,mapLayer.getPlaceList());
        enemyLayer = new EnemyLayer(this,mapLayer.getPathList());
        bulletLayer = new BulletLayer(this);
//
        schedule("addEnemy", 1f);
//        schedule("enemyLogic", 0.1f);
    }

    public void addEnemy(float dt){
        enemyLayer.add();
    }
    public void enemyLogic(float dt){
        bulletLayer.logic();
    }


    @Override
    public boolean ccTouchesBegan(MotionEvent event) {
        return super.ccTouchesBegan(event);
    }

    @Override
    public boolean ccTouchesMoved(MotionEvent event) {
        return super.ccTouchesMoved(event);
    }

    @Override
    public boolean ccTouchesEnded(MotionEvent event) {
        playLayer.ccTouchesEnded(Cocos2dUtil.convertToGL(event));
        return super.ccTouchesEnded(event);
    }
}
