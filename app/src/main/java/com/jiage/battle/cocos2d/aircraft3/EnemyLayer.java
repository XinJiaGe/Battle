package com.jiage.battle.cocos2d.aircraft3;

import com.jiage.battle.cocos2d.Cocos2dUtil;
import com.jiage.battle.cocos2d.CollisionUtil;
import com.jiage.battle.cocos2d.Constant;
import com.jiage.battle.cocos2d.aircraft3.model.PathsModel;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.instant.CCCallFuncN;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCColorLayer;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * 作者：忻佳
 * 日期：2019-11-01
 * 描述：敌人
 */
public class EnemyLayer {
    private List<PathsModel[]> pathList;
    private SickTo mSickTo;
    private Vector<Enemy> vcEnemys = new Vector<>();


    public EnemyLayer(SickTo sickTo) {
        this.mSickTo = sickTo;
    }

    public void init(List<PathsModel[]> pathList){
        this.pathList = pathList;
    }

    /**
     * 定时添加敌人
     */
    public void addEnemy(){
        add(0,Constant.EnemyType.ZhiZhu);
        add(1,Constant.EnemyType.XiaoChong);
    }

    /**
     * 添加敌人
     * @param pathIndex   添加到路径
     * @param type
     */
    private void add(int pathIndex,Constant.EnemyType type){
        PathsModel[] pathsModels = pathList.get(pathIndex);
        Enemy enemy = new Enemy(type, pathsModels,pathIndex);
        vcEnemys.add(enemy);
        enemy.updataOrientation(pathsModels[pathIndex].getOrientation());
        runAction(enemy,pathsModels[enemy.getPointIndex()].getPoint());
    }

    /**
     * 移动完成后向下一个点移动
     * @param sender
     */
    public void spriteMoveFinished(Object sender){
        CCSprite sprite = (CCSprite)sender;
        for (Enemy vcEnemy : vcEnemys) {
            if(vcEnemy.getCcSprite() == sprite){
                PathsModel[] pathsModels = pathList.get(vcEnemy.getPointI());
                int pointIndex = vcEnemy.getPointIndex();
                PathsModel pathsModel = pathsModels[pointIndex];
                vcEnemy.setX(pathsModel.getPoint().x);
                vcEnemy.setY(pathsModel.getPoint().y);
                pointIndex++;
                if(pointIndex>=pathsModels.length) pointIndex = 1;
                vcEnemy.setPointIndex(pointIndex);
                vcEnemy.updataOrientation(pathsModel.getOrientation());
                runAction(vcEnemy,pathsModels[vcEnemy.getPointIndex()].getPoint());
                break;
            }
        }
    }

    /**
     * 更新血条位置
     */
    public void updataBool(){
        for (Enemy vcEnemy : vcEnemys) {
            CGPoint position = vcEnemy.getCcSprite().getPosition();
            CGSize contentSize = vcEnemy.getCcSprite().getContentSize();
            vcEnemy.getColorLayer().setPosition(position.x-contentSize.width/2,position.y+contentSize.height/2);
        }
    }

    /**
     * 移动动画
     * @param enemy
     * @param point  移动终点坐标
     */
    private void runAction(Enemy enemy, CGPoint point){
        CCMoveTo action = CCMoveTo.action((float) CollisionUtil.geDistanceData(enemy.getX(), enemy.getY(), point.x, point.y, enemy.getSpeed()), point);
        CCSequence actions = CCSequence.actions(action, CCCallFuncN.action(this, "spriteMoveFinished"));
        enemy.getCcSprite().runAction(actions);
    }

    public class Enemy{
        private PathsModel[] points;//行走路线集合
        private ArrayList<CCSpriteFrame> frames;// 序列帧的播放
        private int pointIndex;//路线节点
        private int pointI;//那条路线
        private CCColorLayer colorLayer;//血条
        private CCSprite ccSprite;
        private float x;
        private float y;
        private Constant.EnemyType type;
        private int speed; //速度
        private int maxblood;//总血
        private int blood;//血
        private int gold;//值多少金币
        public Enemy(Constant.EnemyType type,PathsModel[] points,int pointI){
            this.points = points;
            this.pointI = pointI;
            this.type = type;
            CGPoint point = points[0].getPoint();
            this.x = point.x;
            this.y = point.y;
            this.speed = Config.enemy.getSpeed(type);
            this.gold = Config.enemy.getGold(type);
            this.maxblood = Config.enemy.getBlood(type);
            this.pointIndex ++;
            CCSprite ccSprite = null;
            switch (type) {
                case ZhiZhu:
                    ccSprite = CCSprite.sprite("player/bullet_skin_05.png");
                    break;
                case XiaoChong:
                    ccSprite = CCSprite.sprite("player/zombi_4.png");
                    frames = new ArrayList<>();
                    CGSize contentSize = ccSprite.getContentSize();
                    float oneWidth = contentSize.width / 15;
                    float oneHeight = contentSize.height / 3;
                    ccSprite.setTextureRect(oneWidth*1,0,oneWidth,oneHeight,false);
                    for (int i = 0; i < 12; i++) {
                        CCSprite sprite = CCSprite.sprite("player/zombi_4.png");
                        sprite.setTextureRect(oneWidth*(i+1),0,oneWidth,oneHeight,false);
                        CCSpriteFrame displayedFrame = sprite.displayedFrame();
                        frames.add(displayedFrame);
                    }
                    runAction(ccSprite,frames,"走路",0.05f);
                    break;
            }
            ccSprite.setAnchorPoint(0.5f, 0.5f);
            ccSprite.setPosition(x, y);
            mSickTo.addChild(ccSprite,Config.enemy.getZ(type), Config.enemy.tag);
            this.ccSprite = ccSprite;
            this.blood = maxblood;
            //设置血条
            this.colorLayer = Cocos2dUtil.setLayerBool(mSickTo, ccSprite, blood, maxblood);
        }

        /**
         * 根据方位改变图片
         * @return
         */
        public void updataOrientation(int orientation) {
            switch (orientation) {
                case Constant.Orientation.TOP:
                    ccSprite.setRotation(0);
                    break;
                case Constant.Orientation.RIGHT:
                    ccSprite.setRotation(90);
                    break;
                case Constant.Orientation.BOTTOM:
                    ccSprite.setRotation(180);
                    break;
                case Constant.Orientation.LEFT:
                    ccSprite.setRotation(270);
                    break;
            }
        }

        /**
         * 受伤
         * @param aggressivity  伤害值
         */
        public void Injured(int aggressivity){
            blood -= aggressivity;
            mSickTo.removeChild(colorLayer,true);
            if(blood<1){
                mSickTo.removeChild(ccSprite,true);
                vcEnemys.removeElement(this);
            }else this.colorLayer = Cocos2dUtil.setLayerBool(mSickTo, ccSprite,blood,maxblood);
        }

        /**
         * 执行帧动画
         * @param ccSprite
         * @param frames
         * @param name
         * @param delay
         */
        private void runAction(CCSprite ccSprite, ArrayList<CCSpriteFrame> frames, String name, float delay){
            // 配置序列帧的信息 参数1 动作的名字(给程序员看的) 参数2 每一帧播放的时间 单位秒 参数3 所有用到的帧
            CCAnimation anim = CCAnimation.animation(name, delay, frames);
            CCAnimate animate = CCAnimate.action(anim);
            // 序列帧动作默认是永不停止的循环
            CCRepeatForever forever = CCRepeatForever.action(animate);
            ccSprite.runAction(forever);
        }

        public Constant.EnemyType getType() {
            return type;
        }

        public void setType(Constant.EnemyType type) {
            this.type = type;
        }

        public int getMaxblood() {
            return maxblood;
        }

        public void setMaxblood(int maxblood) {
            this.maxblood = maxblood;
        }

        public int getBlood() {
            return blood;
        }

        public void setBlood(int blood) {
            this.blood = blood;
        }

        public int getGold() {
            return gold;
        }

        public void setGold(int gold) {
            this.gold = gold;
        }

        public CCColorLayer getColorLayer() {
            return colorLayer;
        }

        public void setColorLayer(CCColorLayer colorLayer) {
            this.colorLayer = colorLayer;
        }

        public ArrayList<CCSpriteFrame> getFrames() {
            return frames;
        }

        public void setFrames(ArrayList<CCSpriteFrame> frames) {
            this.frames = frames;
        }

        public int getPointI() {
            return pointI;
        }

        public void setPointI(int pointI) {
            this.pointI = pointI;
        }

        public int getPointIndex() {
            return pointIndex;
        }

        public void setPointIndex(int pointIndex) {
            this.pointIndex = pointIndex;
        }

        public PathsModel[] getPoints() {
            return points;
        }

        public void setPoints(PathsModel[] points) {
            this.points = points;
        }

        public CCSprite getCcSprite() {
            return ccSprite;
        }

        public void setCcSprite(CCSprite ccSprite) {
            this.ccSprite = ccSprite;
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
    }
    /**
     * 清除所有
     */
    public void initialization(){
        vcEnemys.removeAllElements();
        pathList.clear();
    }
    public Vector<Enemy> getVcEnemys() {
        return vcEnemys;
    }
}
