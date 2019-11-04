package com.jiage.battle.cocos2d.aircraft3;

import android.util.Log;

import com.jiage.battle.cocos2d.CollisionUtil;

import org.cocos2d.actions.UpdateCallback;
import org.cocos2d.nodes.CCSprite;
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
     * 把modle添加成player
     * @param modelConfig
     */
    public void add(ModelLayer.Model modelConfig){
        modelConfig.getSprite().setTag(tag);
        vcPlayers.add(new Player(modelConfig.getSprite(),modelConfig.getX(),modelConfig.getY()));
    }

    /**
     * 构造方法
     * @param sickTo
     */
    public PlayerLayer(SickTo sickTo) {
        this.mSickTo = sickTo;
    }

    /**
     * 搜寻范围内敌人,锁定敌人
     * @param enemyLayer
     * @param bulletLayer
     */
    public void search(EnemyLayer enemyLayer, BulletLayer bulletLayer){
        for (Player vcPlayer : vcPlayers) {
            if(!vcPlayer.isIslocking()) {//没有锁定敌人时
                float distance = vcPlayer.getDistance();
                CCSprite sprite = vcPlayer.getSprite();
                CGSize contentSize = sprite.getContentSize();
                //判断是否在攻击距离内
                if (CollisionUtil.gePointDistance(vcPlayer.x + contentSize.width / 2, vcPlayer.y + contentSize.height / 4, 200, 200) <= distance) {
                    bulletLayer.add(vcPlayer, BulletLayer.TYPE.GONGJIAN);
                }
            }
        }
    }

    public class Player{
        private float x;
        private float y;
        private boolean islocking = false;//是否锁定敌人中
        private float distance = 200;//攻击距离
        private int speed; //速度
        private int angle = 0;//角度
        private int aggressivity = 1;//攻击力
        private CCSprite sprite;
        public Player(CCSprite projectile, float x, float y) {
            this.sprite = projectile;
            this.x = x;
            this.y = y;
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

        public int getSpeed() {
            return speed;
        }

        public void setSpeed(int speed) {
            this.speed = speed;
        }

        public int getAngle() {
            return angle;
        }

        public void setAngle(int angle) {
            this.angle = angle;
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
