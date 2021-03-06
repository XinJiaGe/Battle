package com.jiage.battle.cocos2d.stickto2;

import android.util.Log;

import com.jiage.battle.cocos2d.CollisionUtil;

import org.cocos2d.actions.instant.CCCallFuncN;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;

import java.util.ArrayList;
import java.util.Vector;

/**
 * 作者：忻佳
 * 日期：2019-10-30
 * 描述：塔
 */
public class PlayerLayer {
    private SickTo mSickTo;
    private Vector<Player> vcPlayers = new Vector<>();
    private int i;
    private BulletLayer mBulletLayer;
    private EnemyLayer mEnemyLayer;


    public PlayerLayer(SickTo sickTo) {
        this.mSickTo = sickTo;
    }

    public void init(){

    }

    /**
     * 把modle添加成player
     * @param modelConfig
     */
    public void add(ModelLayer.Model modelConfig){
        vcPlayers.add(new Player(modelConfig));
    }

    /**
     * 攻击一次
     * @param bulletLayer
     * @param enemyLayer
     */
    public void Attack(BulletLayer bulletLayer, EnemyLayer enemyLayer){
        i++;
        for (Player vcPlayer : vcPlayers) {
            if(vcPlayer.isIslocking()){
                if(i%Config.player.getSpeed(vcPlayer.getAttacktype())==0) {
                    switch (vcPlayer.getAttacktype()) {
                        case DianQiu:
                            if (vcPlayer.getAttackFrames() != null) {
                                this.mBulletLayer = bulletLayer;
                                this.mEnemyLayer = enemyLayer;
                                // 配置序列帧的信息 参数1 动作的名字(给程序员看的) 参数2 每一帧播放的时间 单位秒 参数3 所有用到的帧
                                CCAnimation anim = CCAnimation.animation("电塔攻击", 0.05f, vcPlayer.getAttackFrames());
                                CCAnimate animate = CCAnimate.action(anim,false);
                                CCSequence sequence = CCSequence.actions(animate, CCCallFuncN.action(this, "attacktypeFinish"));
                                vcPlayer.getSprite().runAction(sequence);
                            }
                            break;
                        default:
                            bulletLayer.add(vcPlayer, enemyLayer);
                            break;
                    }
                }
            }
        }
        if(i>=999999999) i=1;
    }

    public void attacktypeFinish(Object sender){
        CCSprite sprite = (CCSprite)sender;
        for (Player vcPlayer : vcPlayers) {
            if(vcPlayer.getSprite() == sprite){
                if(mBulletLayer!=null&&mEnemyLayer!=null) mBulletLayer.add(vcPlayer, mEnemyLayer);
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
                EnemyLayer.Enemy recentEnemy=null;//最近的敌人
                float recentDistance = 999999;//最近距离
                for (EnemyLayer.Enemy vcEnemy : enemyLayer.getVcEnemys()) {
                    CGPoint position = vcEnemy.getCcSprite().getPosition();
                    //获取离敌人距离
                    float d = CollisionUtil.gePointDistance(vcPlayer.getX() + contentSize.width / 2, vcPlayer.getY() + contentSize.height - contentSize.height / 4, position.x, position.y);
                    if(d<recentDistance){
                        recentDistance = d;
                        recentEnemy = vcEnemy;
                    }
                }
                //判断最近的敌人是否在攻击距离内
                if (recentEnemy!=null && recentDistance <= distance) {
                    vcPlayer.setIslocking(true);
                    vcPlayer.setEnemy(recentEnemy);
                    Log.e("PlayerLayer","锁定敌人");
                }
            }else{
                EnemyLayer.Enemy enemy = vcPlayer.getEnemy();
                if(enemy!=null) {
                    CGPoint position = enemy.getCcSprite().getPosition();
                    //判断的人是否还在攻击距离内
                    if (CollisionUtil.gePointDistance(vcPlayer.getX()+contentSize.width/2,vcPlayer.getY()+contentSize.height-contentSize.height/4, position.x, position.y) > distance) {
                        vcPlayer.setIslocking(false);
                        vcPlayer.setEnemy(null);
                        Log.e("PlayerLayer", "敌人超出攻击范围");
                    }
                }
            }
        }
    }

    public class Player{
        private String name;
        private float x;
        private float y;
        private EnemyLayer.Enemy enemy;//锁定攻击的敌人
        private boolean islocking = false;//是否锁定敌人中
        private float distance;//攻击距离
        private Constant.ModelType type;//塔类型
        private Constant.AttackType attacktype;//攻击方式
        private ArrayList<CCSpriteFrame> attackFrames;// 攻击动画
        private CCSprite sprite;
        public Player(ModelLayer.Model modelConfig) {
            this.distance = modelConfig.getDistance();
            this.sprite = modelConfig.getSprite();
            this.attacktype = modelConfig.getAttacktype();
            this.x = modelConfig.getX();
            this.y = modelConfig.getY();
            this.name = modelConfig.getName();
            this.type = modelConfig.getType();
            this.attackFrames = modelConfig.getAttackFrames();
            sprite.setTag(Config.player.tag);
            sprite.setVertexZ(Config.player.z);
        }



        /**
         * 获取图片，根据方位改变
         * @return
         */
        public void updataImage(int orientation) {
            CCSprite ccSprite = CCSprite.sprite(name);
            switch (type) {
                case JianTa:
                    float oneWidth = ccSprite.getContentSize().width/15;
                    float oneHeight = ccSprite.getContentSize().height/4;
                    switch (orientation) {
                        case Constant.Orientation.TOP:
                            ccSprite.setTextureRect(oneWidth*11,0,oneWidth,oneHeight,false);
                            break;
                        case Constant.Orientation.LEFT:
                        case Constant.Orientation.BOTTOM:
                        case Constant.Orientation.BOTTOMLEFT:
                            ccSprite.setTextureRect(0,0,oneWidth,oneHeight,false);
                            break;
                        case Constant.Orientation.RIGHT:
                        case Constant.Orientation.BOTTOMRIGHT:
                            ccSprite.setTextureRect(oneWidth*5,0,oneWidth,oneHeight,false);
                            break;
                        case Constant.Orientation.TOPLEFT:
                            ccSprite.setTextureRect(oneWidth*8,0,oneWidth,oneHeight,false);
                            break;
                        case Constant.Orientation.TOPRIGHT:
                            ccSprite.setTextureRect(oneWidth*12,0,oneWidth,oneHeight,false);
                            break;
                    }
                    break;
            }
            mSickTo.removeChild(sprite,true);
            ccSprite.setAnchorPoint(sprite.getAnchorPoint());
            ccSprite.setPosition(sprite.getPosition());
            mSickTo.addChild(ccSprite,Config.player.z,Config.player.tag);
            this.sprite = ccSprite;
        }

        public ArrayList<CCSpriteFrame> getAttackFrames() {
            return attackFrames;
        }

        public void setAttackFrames(ArrayList<CCSpriteFrame> attackFrames) {
            this.attackFrames = attackFrames;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Constant.ModelType getType() {
            return type;
        }

        public void setType(Constant.ModelType type) {
            this.type = type;
        }

        public Constant.AttackType getAttacktype() {
            return attacktype;
        }

        public void setAttacktype(Constant.AttackType attacktype) {
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
    }
    /**
     * 清除所有
     */
    public void initialization(){
        vcPlayers.removeAllElements();
    }
    public Vector<Player> getVcPlayers() {
        return vcPlayers;
    }
}
