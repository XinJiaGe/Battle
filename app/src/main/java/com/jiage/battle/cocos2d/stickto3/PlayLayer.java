package com.jiage.battle.cocos2d.stickto3;


import com.jiage.battle.cocos2d.CollisionUtil;
import com.jiage.battle.cocos2d.stickto2.model.CGPointModel;

import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;

import java.util.List;
import java.util.Vector;

/**
 * 作者：忻佳
 * 日期：2019-11-12
 * 描述：
 */
public class PlayLayer {
    private StickTo stickTo;
    private Vector<Player> vcPlayers = new Vector<>();
    private List<CGPointModel> placeList;

    public PlayLayer(StickTo stickTo, List<CGPointModel> placeList){
        this.placeList = placeList;
        this.stickTo = stickTo;
    }

    public void ccTouchesEnded(CGPoint cgPoint){
        for (int i = 0; i < placeList.size(); i++) {
            CGPointModel cgPointModel = placeList.get(i);
            CGPoint cgPoint1 = cgPointModel.getCgPoint();
            if(CollisionUtil.isClickRectangle(cgPoint.x,cgPoint.y,cgPoint1.x,cgPoint1.y,cgPointModel.getWidth(),cgPointModel.getHeight())){
                CCSprite ccSprite = CCSprite.sprite("player/gun/base.png");
                ccSprite.setAnchorPoint(0.5f,0.5f);
                ccSprite.setPosition(cgPoint1.x+cgPointModel.getWidth()/2,cgPoint1.y+cgPointModel.getHeight()/2);
                stickTo.addChild(ccSprite, 2,2);
                CCSprite ccSprite2 = CCSprite.sprite("player/gun/gun1.png");
                ccSprite2.setAnchorPoint(0.5f,0.3f);
                ccSprite2.setPosition(cgPoint1.x+cgPointModel.getWidth()/2,cgPoint1.y+cgPointModel.getHeight()/2);
                stickTo.addChild(ccSprite2, 3,3);
                vcPlayers.add(new Player(ccSprite,ccSprite2,cgPointModel));
                placeList.remove(i);
                break;
            }
        }
    }

    public class Player{
        private CCSprite ccSprite;
        private CCSprite ccSpriteBase;
        private CGPointModel cgPointModel;

        public Player(CCSprite ccSpriteBase, CCSprite ccSprite, CGPointModel cgPointModel) {
            this.cgPointModel = cgPointModel;
            this.ccSpriteBase = ccSpriteBase;
            this.ccSprite = ccSprite;
        }
        public CCSprite getCcSprite() {
            return ccSprite;
        }

        public void setCcSprite(CCSprite ccSprite) {
            this.ccSprite = ccSprite;
        }
    }

    public Vector<Player> getVcPlayers() {
        return vcPlayers;
    }

}
