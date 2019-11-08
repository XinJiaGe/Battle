package com.jiage.battle.cocos2d.aircraft3;

import android.util.Log;

import com.jiage.battle.cocos2d.CollisionUtil;
import com.jiage.battle.cocos2d.Constant;

import org.cocos2d.actions.instant.CCCallFuncN;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;

import java.util.Vector;

/**
 * 作者：忻佳
 * 日期：2019-11-01
 * 描述：子弹
 */
public class BulletLayer {
    private SickTo mSickTo;
    private Vector<Bullet> vcBullet = new Vector<>();


    public BulletLayer(SickTo sickTo) {
        this.mSickTo = sickTo;
    }

    public void init(){

    }

    /**
     * 添加一个子弹
     * @param player
     * @param enemyLayer
     */
    public void add(PlayerLayer.Player player, EnemyLayer enemyLayer){
        String name = "";
        CGSize contentSize = null;
        CCSprite ccSprite = null;
        switch (player.getAttacktype()) {
            case GongJian:
                name = "bullet/849848961416416515.png";
                contentSize = player.getSprite().getContentSize();
                ccSprite = CCSprite.sprite(name);
                ccSprite.setAnchorPoint(0.5f,0.5f);
                ccSprite.setPosition(CCNode.ccp(player.getX()+contentSize.width/2,player.getY()+player.getSprite().getContentSize().height-player.getSprite().getContentSize().height/4));
                break;
            case DianQiu:
                name = "bullet/icon_aircraftwars_bullet2.png";
                contentSize = player.getSprite().getContentSize();
                ccSprite = CCSprite.sprite(name);
                ccSprite.setAnchorPoint(0,0.5f);
                ccSprite.setPosition(CCNode.ccp(player.getX()+contentSize.width/2,player.getY()+player.getSprite().getContentSize().height-player.getSprite().getContentSize().height/4));
                break;
        }
        if(ccSprite!=null) {
            mSickTo.addChild(ccSprite, Config.bullet.z, Config.bullet.tag);
            Bullet bullet = new Bullet(player, ccSprite, enemyLayer);
            vcBullet.add(bullet);
            runAction(bullet, player.getEnemy().getCcSprite().getPosition());
        }
    }

    /**
     * 子弹逻辑
     */
    public void logic(){
        for (Bullet bullet : vcBullet) {
            EnemyLayer.Enemy enemy = bullet.getEnemy();
            if(enemy!=null) {
                CGPoint position = enemy.getCcSprite().getPosition();
                runAction(bullet, position);
            }
        }
    }

    /**
     * 移动完成后
     * @param sender
     */
    public void bulletMoveFinished(Object sender){
        CCSprite sprite = (CCSprite)sender;
        for (Bullet bullet : vcBullet) {
            if(bullet.getSprite() == sprite){
                EnemyLayer.Enemy enemy = bullet.getEnemy();
                enemy.Injured(bullet.getAggressivity());
                bullet.getPlayer().setIslocking(false);
                vcBullet.removeElement(bullet);
                mSickTo.removeChild(sprite,true);
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
        bullet.updataRotation();
        CGPoint position = bullet.getSprite().getPosition();
        CCMoveTo action = CCMoveTo.action((float) CollisionUtil.geDistanceData(position.x, position.y, point.x, point.y, bullet.getSpeed()), point);
        CCSequence actions = CCSequence.actions(action, CCCallFuncN.action(this, "bulletMoveFinished"));
        bullet.getSprite().stopAllActions();
        bullet.getSprite().runAction(actions);
    }

    public static class Bullet{
        private CCSprite sprite;
        private Constant.AttackType attacktype;//攻击方式
        private EnemyLayer.Enemy enemy;//锁定攻击的敌人
        private EnemyLayer enemyLayer;//敌人类
        private PlayerLayer.Player player;
        private int speed; //移动速度
        private float angle = 0;//角度
        private int aggressivity;//攻击力

        public Bullet(PlayerLayer.Player player, CCSprite ccSprite, EnemyLayer enemyLayer) {
            this.enemyLayer = enemyLayer;
            this.player = player;
            this.sprite = ccSprite;
            this.enemy = player.getEnemy();
            this.attacktype = player.getAttacktype();
            this.speed = Config.bullet.getSpeed(attacktype);
            this.aggressivity = Config.bullet.getAggressivity(attacktype);
        }
        /**
         * 旋转图片
         * @return
         */
        public void updataRotation() {
            switch (attacktype) {
                case GongJian:
                    CGPoint playerPosition = enemy.getCcSprite().getPosition();
                    CGPoint position = sprite.getPosition();
                    angle = CollisionUtil.getRotationAngle(position.x, position.y, playerPosition.x, playerPosition.y);
                    sprite.setRotation(angle-90);
                    updataPlayerImage();
                    break;
            }
        }

        /**
         * 修改player图片
         */
        public void updataPlayerImage(){
            if(angle>22&&angle<=67){//右上
                player.updataImage(Constant.Orientation.TOPRIGHT);
            }else if(angle>67&&angle<=112){//右
                player.updataImage(Constant.Orientation.RIGHT);
            }else if(angle>112&&angle<=157){//右下
                player.updataImage(Constant.Orientation.BOTTOMRIGHT);
            }else if(angle>157&&angle<=202){//下
                player.updataImage(Constant.Orientation.BOTTOM);
            }else if(angle>202&&angle<=247){//左下
                player.updataImage(Constant.Orientation.BOTTOMLEFT);
            }else if(angle>247&&angle<=292){//左
                player.updataImage(Constant.Orientation.LEFT);
            }else if(angle>292&&angle<=337){//左上
                player.updataImage(Constant.Orientation.TOPLEFT);
            }else{//上
                player.updataImage(Constant.Orientation.TOP);
            }
        }


        public PlayerLayer.Player getPlayer() {
            return player;
        }

        public void setPlayer(PlayerLayer.Player player) {
            this.player = player;
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

        public Constant.AttackType getAttacktype() {
            return attacktype;
        }

        public void setAttacktype(Constant.AttackType attacktype) {
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

    /**
     * 清除所有
     */
    public void initialization(){
        vcBullet.removeAllElements();
    }
    public Vector<Bullet> getVcBullet() {
        return vcBullet;
    }
}
