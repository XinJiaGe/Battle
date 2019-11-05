package com.jiage.battle.cocos2d.aircraft3;

import com.jiage.battle.cocos2d.CollisionUtil;
import com.jiage.battle.cocos2d.Constant;

import org.cocos2d.actions.instant.CCCallFuncN;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;

import java.util.Vector;

/**
 * 作者：忻佳
 * 日期：2019-11-01
 * 描述：子弹
 */
public class BulletLayer {
    private SickTo mSickTo;
    private int z = 8;
    private Vector<Bullet> vcBullet = new Vector<>();


    public BulletLayer(SickTo sickTo) {
        this.mSickTo = sickTo;
    }

    /**
     * 添加一个子弹
     * @param player
     * @param enemyLayer
     */
    public void add(PlayerLayer.Player player, EnemyLayer enemyLayer){
        String name = "";
        switch (player.getAttacktype()) {
            case GONGJIAN:
                name = "849848961416416515.png";
                break;
        }
        CCSprite ccSprite = CCSprite.sprite(name);
        ccSprite.setAnchorPoint(0.5f,0.5f);
        ccSprite.setPosition(CCNode.ccp(player.getX(),player.getY()+player.getSprite().getContentSize().height-10));
        mSickTo.addChild(ccSprite,0,z);
        vcBullet.add(new Bullet(player,ccSprite,enemyLayer));
    }

    /**
     * 子弹逻辑
     */
    public void logic(){
        for (Bullet bullet : vcBullet) {
            EnemyLayer.Enemy enemy = bullet.getEnemy();
            CGPoint position = enemy.getCcSprite().getPosition();
            runAction(bullet,position);
        }
    }

    /**
     * 移动完成后
     * @param sender
     */
    public void bulletMoveFinished(Object sender){
        CCSprite sprite = (CCSprite)sender;
        mSickTo.removeChild(sprite,false);
        for (Bullet bullet : vcBullet) {
            if(bullet.getSprite() == sprite){
                EnemyLayer.Enemy enemy = bullet.getEnemy();
                int blood = enemy.getBlood() - bullet.getAggressivity();
                if(blood<=0){

                }
                vcBullet.removeElement(bullet);
                break;
            }
        }
    }

    /**
     * 移动动画
     * @param bullet
     * @param point  移动终点坐标
     */
    private void runAction(Bullet bullet, CGPoint point){
        CGPoint position = bullet.getSprite().getPosition();
        CCMoveTo action = CCMoveTo.action((float) CollisionUtil.geDistanceData(position.x, position.y, point.x, point.y, bullet.getSpeed()), point);
        CCSequence actions = CCSequence.actions(action, CCCallFuncN.action(this, "bulletMoveFinished"));
        bullet.getSprite().runAction(actions);
    }

    public static class Bullet{
        private CCSprite sprite;
        private Constant.ATTACKTYPE attacktype;//攻击方式
        private EnemyLayer.Enemy enemy;//锁定攻击的敌人
        private EnemyLayer enemyLayer;//敌人类
        private int speed; //速度
        private float angle = 0;//角度
        private int aggressivity = 1;//攻击力

        public Bullet(PlayerLayer.Player player, CCSprite ccSprite, EnemyLayer enemyLayer) {
            this.enemyLayer = enemyLayer;
            this.sprite = ccSprite;
            this.enemy = player.getEnemy();
            this.attacktype = player.getAttacktype();
            this.aggressivity = player.getAggressivity();
            switch (attacktype) {
                case GONGJIAN:
                    this.speed = 800;
                    break;
            }
        }

        public EnemyLayer getEnemyLayer() {
            return enemyLayer;
        }

        public void setEnemyLayer(EnemyLayer enemyLayer) {
            this.enemyLayer = enemyLayer;
        }

        public EnemyLayer.Enemy getEnemy() {
            return enemy;
        }

        public void setEnemy(EnemyLayer.Enemy enemy) {
            this.enemy = enemy;
        }

        public CCSprite getSprite() {
            return sprite;
        }

        public void setSprite(CCSprite sprite) {
            this.sprite = sprite;
        }

        public Constant.ATTACKTYPE getAttacktype() {
            return attacktype;
        }

        public void setAttacktype(Constant.ATTACKTYPE attacktype) {
            this.attacktype = attacktype;
        }
        public int getSpeed() {
            return speed;
        }

        public void setSpeed(int speed) {
            this.speed = speed;
        }

        public float getAngle() {
            return angle;
        }

        public void setAngle(float angle) {
            this.angle = angle;
        }

        public int getAggressivity() {
            return aggressivity;
        }

        public void setAggressivity(int aggressivity) {
            this.aggressivity = aggressivity;
        }
    }

    public Vector<Bullet> getVcBullet() {
        return vcBullet;
    }
}
