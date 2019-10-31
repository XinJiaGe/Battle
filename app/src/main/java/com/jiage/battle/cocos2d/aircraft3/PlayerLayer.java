package com.jiage.battle.cocos2d.aircraft3;

import org.cocos2d.nodes.CCSprite;

import java.util.Vector;

/**
 * 作者：忻佳
 * 日期：2019-10-30
 * 描述：
 */
public class PlayerLayer {
    public static int tag = 2;
    private int z = 9;
    private Vector<Player> vcPlayers = new Vector<>();


    public PlayerLayer(SickTo sickTo) {

    }

    public void add(ModelLayer.Model modelConfig){
        modelConfig.getSprite().setTag(tag);
        vcPlayers.add(new Player(modelConfig.getSprite(),modelConfig.getX(),modelConfig.getY()));
    }

    public class Player{
        private float x;
        private float y;
        private CCSprite sprite;
        public Player(CCSprite projectile, float x, float y) {
            this.sprite = projectile;
            this.x = x;
            this.y = y;
        }

        public CCSprite getSprite() {
            return sprite;
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
}
