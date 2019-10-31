package com.jiage.battle.cocos2d.aircraft3;

import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

import java.util.Vector;

/**
 * 作者：忻佳
 * 日期：2019-10-30
 * 描述：
 */
public class ModelLayer {
    private String TAG = "ModelLayer";
    public static int tag = 1;
    private int z = 10;
    private String[] models = new String[]{"icon_aircraftwars_play.png"};
    private Vector<Model> vcModels = new Vector<>();
    private boolean isMobileModel = false;
    private boolean isLayDown = false;
    private Model mobileSprite;

    public ModelLayer(SickTo sickTo){
        for (int i = 0; i < models.length; i++) {
            String model = models[i];
            CCSprite projectile = CCSprite.sprite(model);
            float x = projectile.getContentSize().width/2+(projectile.getContentSize().width+20)*i;
            float y = projectile.getContentSize().height/2;
            projectile.setPosition(CGPoint.ccp(x,y));
            sickTo.addChild(projectile,z,tag);
            vcModels.add(new Model(projectile ,x,y));
        }
    }

    public void ccTouchesBegan(MotionEvent event){
        CGPoint point = new CGPoint();
        point.set(event.getX(), event.getY());
        CGPoint cgLocation = CCDirector.sharedDirector().convertToGL(point);
        for (Model vcModel : vcModels) {
            CCSprite sprite = vcModel.getSprite();
            if(sprite.getTag() == tag &&CGRect.containsPoint(sprite.getBoundingBox(), cgLocation)){//判断是否点击到了
                Log.e(TAG,"点击到了");
                isMobileModel = true;
                mobileSprite = vcModel;
                break;
            }
            if(sprite.getTag() == PlayerLayer.tag &&CGRect.containsPoint(sprite.getBoundingBox(), cgLocation)){
                CCMoveTo ccMoveTo = CCMoveTo.action(0.05f, CGPoint.ccp(vcModel.getX(), vcModel.getY()+20));
                sprite.runAction(ccMoveTo);
            }
        }
    }

    public void ccTouchesMoved(MotionEvent event, MapLayer mapLayer){
        CGPoint point = new CGPoint();
        point.set(event.getX(), event.getY());
        CGPoint cgLocation = CCDirector.sharedDirector().convertToGL(point);
        if(isMobileModel){
            if(mobileSprite!=null){
                mobileSprite.setMoveX(cgLocation.x);
                mobileSprite.setMoveY(cgLocation.y);
                CCMoveTo ccMoveTo = CCMoveTo.action(0,CGPoint.ccp(cgLocation.x, cgLocation.y));
                mobileSprite.getSprite().runAction(ccMoveTo);
                isLayDown = isLayDown(mapLayer);
            }
        }
    }

    /**
     * 判断是否可以放置
     * @param mapLayer
     * @return
     */
    private boolean isLayDown(MapLayer mapLayer){
        for (Rect rect : mapLayer.getPlaceList()) {
            if(mobileSprite.getMoveX()>=rect.left&&mobileSprite.getMoveY()>=rect.top&&
                    (mobileSprite.getMoveX()+mobileSprite.getSprite().getContentSize().width)<=rect.right&&
                    (mobileSprite.getMoveY()+mobileSprite.getSprite().getContentSize().height)<=rect.bottom){
                return true;
            }
        }
        return false;
    }

    public void ccTouchesEnded(MotionEvent event, PlayerLayer playerLayer){
        if(isMobileModel){
            isMobileModel = false;
            if(mobileSprite!=null){
                if(isLayDown){
                    CGPoint point = new CGPoint();
                    point.set(event.getX(), event.getY());
                    CGPoint cgLocation = CCDirector.sharedDirector().convertToGL(point);
                    mobileSprite.setX(cgLocation.x);
                    mobileSprite.setY(cgLocation.y);
                    playerLayer.add(mobileSprite);
                }else {
                    CCMoveTo ccMoveTo = CCMoveTo.action(0.05f, CGPoint.ccp(mobileSprite.getX(), mobileSprite.getY()));
                    mobileSprite.getSprite().runAction(ccMoveTo);
                }
            }
        }
    }

    public class Model{
        private float x;
        private float y;
        private float moveX;
        private float moveY;
        private CCSprite sprite;

        public Model(CCSprite projectile, float x, float y) {
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

        public float getMoveX() {
            return moveX;
        }

        public void setMoveX(float moveX) {
            this.moveX = moveX;
        }

        public float getMoveY() {
            return moveY;
        }

        public void setMoveY(float moveY) {
            this.moveY = moveY;
        }
    }
}
