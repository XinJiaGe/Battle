package com.jiage.battle.cocos2d.stickto3;

import org.cocos2d.nodes.CCSprite;

import java.util.Vector;

/**
 * 作者：忻佳
 * 日期：2019-11-12
 * 描述：
 */
public class BulletLayer {
    private StickTo stickTo;
    private Vector<Bullet> vcBullets = new Vector<>();
    public BulletLayer(StickTo stickTo){
        this.stickTo = stickTo;
    }
    public void add(){
//        CCSprite ccSprite = CCSprite.sprite("bullet/icon_aircraftwars_bullet5.png");
//        ccSprite.setAnchorPoint(0f,0.5f);
//        ccSprite.setPosition(0,0);
//        stickTo.addChild(ccSprite, 3,3);
//        vcBullets.add(new Bullet(ccSprite));
    }

    public void logic(){
    }

    public class Bullet{
        private CCSprite ccSprite;

        public Bullet(CCSprite ccSprite) {
            this.ccSprite = ccSprite;
        }

        public CCSprite getCcSprite() {
            return ccSprite;
        }

        public void setCcSprite(CCSprite ccSprite) {
            this.ccSprite = ccSprite;
        }
    }

    public Vector<Bullet> getVcBullets() {
        return vcBullets;
    }
}
