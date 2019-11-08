package com.jiage.battle.cocos2d.aircraft3;

import android.util.Log;
import android.view.MotionEvent;

import com.jiage.battle.cocos2d.Constant;

import org.cocos2d.layers.CCColorLayer;
import org.cocos2d.types.ccColor4B;

/**
 * 作者：忻佳
 * 日期：2019-10-29
 * 描述：
 */
public class SickTo extends CCColorLayer {
    private String TAG = "SickTo";
    private Constant.Process process = Constant.Process.Game;//游戏进程
    private ModelLayer modelLayer;
    private PlayerLayer playerLayer;
    private MapLayer mapLayer;
    private BulletLayer bulletLayer;
    private EnemyLayer enemyLayer;
    private int gold;//金币

    public SickTo(ccColor4B color) {
        super(color);
        setIsTouchEnabled(true);
        mapLayer = new MapLayer(this);
        modelLayer = new ModelLayer(this);
        playerLayer = new PlayerLayer(this);
        bulletLayer = new BulletLayer(this);
        enemyLayer = new EnemyLayer(this);
        init();
    }

    private void init(){
        mapLayer.init(process);
        switch (process) {
            case Start:

                break;
            case Level:

                break;
            case Game:
                modelLayer.init();
                playerLayer.init();
                bulletLayer.init();
                enemyLayer.init(mapLayer.getPathList());
                schedule("update");
                //定时添加敌人
                schedule("addEnemy",Config.enemy.addEnemyInterval);
                //设置攻击频率
                schedule("playerAttack",Config.player.playerAttackInterval);
                break;
            case Barracks:

                break;
        }
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
        //发射子弹
        playerLayer.Attack(bulletLayer,enemyLayer);

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

    /**
     * 初始化清除所有，停止所有
     * @param tag
     */
    public void initialization(int tag){
        unschedule("update");
        unschedule("bulletLogic");
        unschedule("addEnemy");
        unschedule("playerAttack");
        stopAllActions();
        mapLayer.initialization();
        modelLayer.initialization();
        playerLayer.initialization();
        bulletLayer.initialization();
        enemyLayer.initialization();
        switch (tag) {
            case Constant.StartTag.Start:
                process = Constant.Process.Level;
                break;
            case Constant.StartTag.Barracks:
                process = Constant.Process.Barracks;
                break;
            case Constant.StartTag.Out:
                process = Constant.Process.Start;
                break;
        }
        init();
    }
}
