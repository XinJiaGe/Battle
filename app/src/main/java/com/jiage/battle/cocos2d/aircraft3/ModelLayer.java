package com.jiage.battle.cocos2d.aircraft3;

import android.view.MotionEvent;

import com.jiage.battle.cocos2d.CollisionUtil;
import com.jiage.battle.cocos2d.Constant;
import com.jiage.battle.cocos2d.aircraft3.model.CGPointModel;

import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;

import java.util.ArrayList;
import java.util.Vector;

/**
 * 作者：忻佳
 * 日期：2019-10-30
 * 描述：塔model
 */
public class ModelLayer {
    private SickTo mSickTo;
    private String TAG = "ModelLayer";
    public static int tag = 1;
    private int z = 10;
    private String[] names = new String[]{"jianta.png"};
    private Vector<Model> vcModels = new Vector<>();
    private boolean isMobileModel = false;
    private boolean isLayDown = false;
    private Model mobileSprite;

    public ModelLayer(SickTo sickTo){
        this.mSickTo = sickTo;
        for (String name : names) {
            vcModels.add(new Model(name,0,0, Constant.Orientation.BOTTOM, Constant.ATTACKTYPE.GONGJIAN));
        }
    }

    /**
     * 点击model时
     * @param event
     */
    public void ccTouchesBegan(MotionEvent event){
        CGPoint point = new CGPoint();
        point.set(event.getX(), event.getY());
        //坐标转换
        CGPoint cgLocation = CCDirector.sharedDirector().convertToGL(point);
        for (Model vcModel : vcModels) {
            CCSprite sprite = vcModel.getSprite();
            //点击到了model
            if(sprite.getTag() == tag &&CGRect.containsPoint(sprite.getBoundingBox(), cgLocation)){
                isMobileModel = true;
                //new 一个移动对象
                mobileSprite = new Model(vcModel.getName(),vcModel.getX(),vcModel.getY(), vcModel.getOrientation(), vcModel.getAttacktype());
                break;
            }
        }
    }

    /**
     * 移动中  跟随手指移动
     * @param event
     * @param mapLayer
     * @param playerLayer
     */
    public void ccTouchesMoved(MotionEvent event, MapLayer mapLayer, PlayerLayer playerLayer){
        CGPoint point = new CGPoint();
        point.set(event.getX(), event.getY());
        CGPoint cgLocation = CCDirector.sharedDirector().convertToGL(point);
        if(isMobileModel){
            if(mobileSprite!=null){
                mobileSprite.setMoveX(cgLocation.x-mobileSprite.getSprite().getContentSize().width/2);
                mobileSprite.setMoveY(cgLocation.y);
                CCMoveTo ccMoveTo = CCMoveTo.action(0,CGPoint.ccp(cgLocation.x-mobileSprite.getSprite().getContentSize().width/2, cgLocation.y));
                mobileSprite.getSprite().runAction(ccMoveTo);
                //判断是否可以放置
                isLayDown = isLayDown(mapLayer)&&isObstale(mapLayer)&&isModle(playerLayer);
                if(isLayDown) mobileSprite.getSprite().setOpacity(255);//改变透明度
                else mobileSprite.getSprite().setOpacity(100);
            }
        }
    }

    /**
     * 判断是否在可放置区域
     * @param mapLayer
     * @return
     */
    private boolean isLayDown(MapLayer mapLayer){
        for (CGPointModel cgPointModel : mapLayer.getPlaceList()) {
            CGPoint cgPoint = cgPointModel.getCgPoint();
            float moveX = mobileSprite.getMoveX();
            float moveY = mobileSprite.getMoveY();
            float width = mobileSprite.getSprite().getContentSize().width;
            float height = mobileSprite.getSprite().getContentSize().height;
            if(moveX>=cgPoint.x&&moveY<=cgPoint.y+cgPointModel.getHeight()-height/2&&moveX+width<=cgPoint.x+cgPointModel.getWidth()&&moveY>=cgPoint.y){
                return true;
            }
        }
        return false;
    }

    /**
     * 判断可放置区域是否有障碍物
     * @param mapLayer
     * @return
     */
    private boolean isObstale(MapLayer mapLayer){
        for (CGPointModel cgPointModel : mapLayer.getObstacleRectList()) {
            CGPoint cgPoint = cgPointModel.getCgPoint();
            float moveX = mobileSprite.getMoveX();
            float moveY = mobileSprite.getMoveY();
            float width = mobileSprite.getSprite().getContentSize().width;
            float height = mobileSprite.getSprite().getContentSize().height;
            if(CollisionUtil.isRectangleAndRectangle((int)(moveX),(int)(moveY),(int)(width),(int)(height/2),(int)(cgPoint.x),(int)(cgPoint.y),cgPointModel.getWidth(),cgPointModel.getHeight())){
                return false;
            }
        }
        return true;
    }
    /**
     * 判断modle是否重叠
     * @param playerLayer
     * @return
     */
    private boolean isModle(PlayerLayer playerLayer){
        for (PlayerLayer.Player vcPlayer : playerLayer.getVcPlayers()) {
            CGSize contentSize = vcPlayer.getSprite().getContentSize();
            float moveX = mobileSprite.getMoveX();
            float moveY = mobileSprite.getMoveY();
            float width = mobileSprite.getSprite().getContentSize().width;
            float height = mobileSprite.getSprite().getContentSize().height;
            if(CollisionUtil.isRectangleAndRectangle((int)(moveX),(int)(moveY),(int)(width),(int)(height/2),(int)(vcPlayer.getX()),(int)(vcPlayer.getY()),(int)contentSize.width,(int)contentSize.height/2)){
                return false;
            }
        }
        return true;
    }

    /**
     * 抬起手指
     * @param event
     * @param playerLayer
     */
    public void ccTouchesEnded(MotionEvent event, PlayerLayer playerLayer){
        if(isMobileModel){
            isMobileModel = false;
            if(mobileSprite!=null){
                if(isLayDown){//可放置
                    CGPoint point = new CGPoint();
                    point.set(event.getX(), event.getY());
                    CGPoint cgLocation = CCDirector.sharedDirector().convertToGL(point);
                    mobileSprite.setX(cgLocation.x-mobileSprite.getSprite().getContentSize().width/2);
                    mobileSprite.setY(cgLocation.y);
                    playerLayer.add(mobileSprite);
                }else {//不可放置，删除
                    mSickTo.removeChild(mobileSprite.sprite,false);
                }
            }
        }
    }

    public class Model{
        private float x;
        private float y;
        private float moveX;
        private float moveY;
        private String name;
        private int orientation;//方位
        private CCSprite sprite;//默认显示图片
        private Constant.ATTACKTYPE attacktype;//攻击方式
        private ArrayList<CCSpriteFrame> frames;//帧动图集

        /**
         * 创建一个
         * @param x
         * @param y
         */
        public Model(String name, float x, float y,int orientation,Constant.ATTACKTYPE attacktype) {
            this.orientation = orientation;
            this.attacktype = attacktype;
            this.name = name;
            this.x = x;
            this.y = y;
            updata(orientation);
        }

        /**
         * 获取图片，根据方位改变
         * @return
         */
        public CCSprite updata(int orientation) {
            mSickTo.removeChild(sprite,false);
            CCSprite ccSprite = CCSprite.sprite(name);
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
            ccSprite.setAnchorPoint(0,0);
            ccSprite.setPosition(CGPoint.ccp(0,0));
            mSickTo.addChild(ccSprite,z,tag);
            this.sprite = ccSprite;
            return sprite;
        }

        public Constant.ATTACKTYPE getAttacktype() {
            return attacktype;
        }

        public void setAttacktype(Constant.ATTACKTYPE attacktype) {
            this.attacktype = attacktype;
        }

        public CCSprite getSprite() {
            return sprite;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getOrientation() {
            return orientation;
        }

        public void setOrientation(int orientation) {
            this.orientation = orientation;
        }

        public ArrayList<CCSpriteFrame> getFrames() {
            return frames;
        }

        public void setFrames(ArrayList<CCSpriteFrame> frames) {
            this.frames = frames;
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
