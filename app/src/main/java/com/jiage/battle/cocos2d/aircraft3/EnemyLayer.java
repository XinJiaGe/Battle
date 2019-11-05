package com.jiage.battle.cocos2d.aircraft3;

import android.graphics.Point;
import android.util.Log;

import com.jiage.battle.cocos2d.CollisionUtil;
import com.jiage.battle.cocos2d.Constant;
import com.jiage.battle.cocos2d.aircraft3.model.PathsModel;

import org.cocos2d.actions.instant.CCCallFuncN;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCRotateBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;

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


    public EnemyLayer(SickTo sickTo, List<PathsModel[]> pathList) {
        this.mSickTo = sickTo;
        this.pathList = pathList;
        sickTo.schedule("addEnemy",0.2f);
//        add();
    }

    /**
     * 定时添加敌人
     */
    public void add(){
        for (PathsModel[] points : pathList) {
            Log.e("EnemyLayer","添加敌人");
            Enemy enemy = new Enemy(TYPE.ZHIZHU, points,0);
            vcEnemys.add(enemy);
            enemy.updataOrientation(points[0].getOrientation());
            runAction(enemy,points[enemy.getPointIndex()].getPoint());
        }
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
        private int pointIndex;//路线节点
        private int pointI;//那条路线
        private CCSprite ccSprite;
        private float x;
        private float y;
        private TYPE type;
        private int speed; //速度
        private int blood;//血
        public Enemy(TYPE type,PathsModel[] points,int pointI){
            this.points = points;
            this.pointI = pointI;
            this.type = type;
            CGPoint point = points[0].getPoint();
            this.x = point.x;
            this.y = point.y;
            this.pointIndex ++;
            String name= "bullet_skin_05.png";
            switch (type) {
                case ZHIZHU:
                    name = "bullet_skin_05.png";
                    speed = 200;
                    blood = 1;
                    break;
            }
            CCSprite ccSprite = CCSprite.sprite(name);
            ccSprite.setAnchorPoint(0.5f,0.5f);
            ccSprite.setPosition(x,y);
            mSickTo.addChild(ccSprite);
            this.ccSprite = ccSprite;
        }

        /**
         * 获取图片，根据方位改变
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

        public int getBlood() {
            return blood;
        }

        public void setBlood(int blood) {
            this.blood = blood;
        }
    }

    public Vector<Enemy> getVcEnemys() {
        return vcEnemys;
    }

    public enum TYPE{
        ZHIZHU,//蜘蛛
    }
}
