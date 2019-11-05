package com.jiage.battle.cocos2d.aircraft3;

import android.util.Log;

import com.jiage.battle.cocos2d.CollisionUtil;
import com.jiage.battle.cocos2d.Constant;
import com.jiage.battle.cocos2d.DrawUtil;

import org.cocos2d.actions.UpdateCallback;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.opengl.CCDrawingPrimitives;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;

import java.util.Vector;

/**
 * 作者：忻佳
 * 日期：2019-10-30
 * 描述：塔
 */
public class PlayerLayer {
    public static int tag = 2;
    private SickTo mSickTo;
    private int z = 9;
    private Vector<Player> vcPlayers = new Vector<>();


    /**
     * 构造方法
     * @param sickTo
     */
    public PlayerLayer(SickTo sickTo) {
        this.mSickTo = sickTo;
        //设置攻击频率
        sickTo.schedule("playerAttack",0.5f);
    }

    /**
     * 把modle添加成player
     * @param modelConfig
     */
    public void add(ModelLayer.Model modelConfig){
        vcPlayers.add(new Player(modelConfig.getSprite(),modelConfig.getAttacktype(),modelConfig.getX(),modelConfig.getY()));
    }

    /**
     * 攻击一次
     * @param bulletLayer
     * @param enemyLayer
     */
    public void Attack(BulletLayer bulletLayer, EnemyLayer enemyLayer){
        for (Player vcPlayer : vcPlayers) {
            if(vcPlayer.isIslocking()){
                bulletLayer.add(vcPlayer,enemyLayer);
            }
        }
    }

    /**
     * 搜寻范围内敌人,锁定敌人
     * @param enemyLayer
     */
    public void search(EnemyLayer enemyLayer){
        for (Player vcPlayer : vcPlayers) {
            float distance = vcPlayer.getDistance();
            CCSprite sprite = vcPlayer.getSprite();
            CGSize contentSize = sprite.getContentSize();
            if(!vcPlayer.isIslocking()) {//没有锁定敌人时
                for (EnemyLayer.Enemy vcEnemy : enemyLayer.getVcEnemys()) {
                    CGPoint position = vcEnemy.getCcSprite().getPosition();
                    //判断是否在攻击距离内
                    if (CollisionUtil.gePointDistance(vcPlayer.x + contentSize.width / 2, vcPlayer.y + contentSize.height / 4, position.x, position.y) <= distance) {
                        vcPlayer.setIslocking(true);
                        vcPlayer.setEnemy(vcEnemy);
                        Log.e("PlayerLayer","锁定敌人");
                        break;
                    }
                }
            }else{
                EnemyLayer.Enemy enemy = vcPlayer.getEnemy();
                if(enemy!=null) {
                    CGPoint position = enemy.getCcSprite().getPosition();
                    //判断是否在攻击距离内
                    if (CollisionUtil.gePointDistance(vcPlayer.x + contentSize.width / 2, vcPlayer.y + contentSize.height / 4, position.x, position.y) > distance) {
                        vcPlayer.setIslocking(false);
                        vcPlayer.setEnemy(null);
                        Log.e("PlayerLayer", "敌人超出攻击范围");
                    }
                }
            }
        }
    }

    public class Player{
        private float x;
        private float y;
        private EnemyLayer.Enemy enemy;//锁定攻击的敌人
        private boolean islocking = false;//是否锁定敌人中
        private float distance = 600;//攻击距离
        private int aggressivity = 1;//攻击力
        private Constant.ATTACKTYPE attacktype;//攻击方式
        private CCSprite sprite;
        public Player(CCSprite projectile,Constant.ATTACKTYPE attacktype, float x, float y) {
            this.sprite = projectile;
            this.attacktype = attacktype;
            this.x = x;
            this.y = y;
            sprite.setTag(tag);
            sprite.setVertexZ(z);
        }

        public Constant.ATTACKTYPE getAttacktype() {
            return attacktype;
        }

        public void setAttacktype(Constant.ATTACKTYPE attacktype) {
            this.attacktype = attacktype;
        }

        public EnemyLayer.Enemy getEnemy() {
            return enemy;
        }

        public void setEnemy(EnemyLayer.Enemy enemy) {
            this.enemy = enemy;
        }

        public boolean isIslocking() {
            return islocking;
        }

        public void setIslocking(boolean islocking) {
            this.islocking = islocking;
        }

        public CCSprite getSprite() {
            return sprite;
        }

        public float getDistance() {
            return distance;
        }

        public void setDistance(float distance) {
            this.distance = distance;
        }

        public void setSprite(CCSprite sprite) {
            this.sprite = sprite;
        }

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }

        public int getAggressivity() {
            return aggressivity;
        }

        public void setAggressivity(int aggressivity) {
            this.aggressivity = aggressivity;
        }
    }

    public Vector<Player> getVcPlayers() {
        return vcPlayers;
    }
}
