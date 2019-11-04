package com.jiage.battle.cocos2d.aircraft3;

import org.cocos2d.nodes.CCSprite;

import java.util.Vector;

/**
 * 作者：忻佳
 * 日期：2019-11-01
 * 描述：子弹
 */
public class BulletLayer {
    private Vector<Bullet> vcEnemys = new Vector<>();


    public BulletLayer(SickTo sickTo) {
        sickTo.schedule("BulletLogic",0.1f);
    }

    /**
     * 添加一个子弹
     * @param player
     * @param type
     */
    public void add(PlayerLayer.Player player, TYPE type){
        vcEnemys.add(new Bullet(player,type));
    }

    public void logic(){

    }

    public static class Bullet{
        private CCSprite sprite;
        private float x;
        private float y;
        private TYPE type;
        private int speed; //速度
        private float angle = 0;//角度
        private int aggressivity = 1;//攻击力

        public Bullet(PlayerLayer.Player player,TYPE type) {
            player.setIslocking(true);
            this.x = player.getX();
            this.y = player.getY();
            this.type = type;
            this.speed = player.getSpeed();
            this.aggressivity = player.getAggressivity();
        }

        public TYPE getType() {
            return type;
        }

        public void setType(TYPE type) {
            this.type = type;
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

    public Vector<Bullet> getVcEnemys() {
        return vcEnemys;
    }

    public enum TYPE{
        GONGJIAN,//弓箭
    }
}
