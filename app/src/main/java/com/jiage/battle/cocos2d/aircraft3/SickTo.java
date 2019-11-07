package com.jiage.battle.cocos2d.aircraft3;

import android.util.Log;
import android.view.MotionEvent;

import org.cocos2d.layers.CCColorLayer;
import org.cocos2d.types.ccColor4B;

/**
 * 作者：忻佳
 * 日期：2019-10-29
 * 描述：
 */
public class SickTo extends CCColorLayer {
    private String TAG = "SickTo";
    private ModelLayer modelLayer;
    private PlayerLayer playerLayer;
    private MapLayer mapLayer;
    private BulletLayer bulletLayer;
    private EnemyLayer enemyLayer;
    private int gold;//金币

    public SickTo(ccColor4B color) {
        super(color);
        init();
    }

    private void init(){
        setIsTouchEnabled(true);
        mapLayer = new MapLayer(this);
        modelLayer = new ModelLayer(this);
        playerLayer = new PlayerLayer(this);
        bulletLayer = new BulletLayer(this);
        enemyLayer = new EnemyLayer(this,mapLayer.getPathList());

        schedule("update");
    }

    /**
     * 子弹逻辑
     * @param dt
     */
    public void bulletLogic(float dt){
        bulletLayer.logic();
    }

    public void update(float dt){
        //循环搜寻范围内敌人,锁定
        playerLayer.search(enemyLayer);
        //更新血条位置
        enemyLayer.updataBool();

        Log.e("数量","子弹："+bulletLayer.getVcBullet().size());
        Log.e("数量","敌人："+enemyLayer.getVcEnemys().size());
    }

    /**
     * 定时添加敌人
     * @param dt
     */
    public void addEnemy(float dt){
        enemyLayer.addEnemy();
    }

    /**
     * player攻击发射子弹
     * @param dt
     */
    public void playerAttack(float dt){
        playerLayer.Attack(bulletLayer,enemyLayer);
    }

    @Override
    public boolean ccTouchesBegan(MotionEvent event) {
        modelLayer.ccTouchesBegan(event);
        mapLayer.ccTouchesBegan(event);
        return super.ccTouchesBegan(event);
    }

    @Override
    public boolean ccTouchesMoved(MotionEvent event) {
        modelLayer.ccTouchesMoved(event, mapLayer,playerLayer);
        return super.ccTouchesMoved(event);
    }

    @Override
    public boolean ccTouchesEnded(MotionEvent event) {
        modelLayer.ccTouchesEnded(event,playerLayer);
        mapLayer.ccTouchesEnded(event);
        return super.ccTouchesEnded(event);
    }
}
